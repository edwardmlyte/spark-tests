package blog.hashmade.spark;

import java.io.IOException;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RestAdapter;
import scala.Tuple2;
import blog.hashmade.spark.retrofit.WorldCupService;
import blog.hashmade.spark.retrofit.bean.Match;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.collect.Lists;
import com.stratio.deep.config.DeepJobConfigFactory;
import com.stratio.deep.config.ICassandraDeepJobConfig;
import com.stratio.deep.context.DeepSparkContext;
import com.stratio.deep.entity.Cells;
import com.stratio.deep.rdd.CassandraJavaRDD;

public class StandaloneGroupByTest {

	private static final int EMBEDDED_CASSANDRA_SERVER_WAITING_TIME = 10000;
	private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneGroupByTest.class);
	
	public static void main(String[] args) throws IOException {
		try {
			initCassandra(retrieveMatchs());
			initSpark();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			System.exit(1);
		}
	}

	private static List<Match> retrieveMatchs() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				"http://worldcup.sfg.io").build();
		WorldCupService service = restAdapter.create(WorldCupService.class);
		return service.getMatchs();
	}

	private static void initCassandra(List<Match> matchs) throws Exception {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		Thread.sleep(EMBEDDED_CASSANDRA_SERVER_WAITING_TIME);
		Cluster cluster = new Cluster.Builder().addContactPoints("localhost")
				.withPort(9142).build();
		Session session = cluster.connect();
		CQLDataLoader dataLoader = new CQLDataLoader(session);
		dataLoader.load(new ClassPathCQLDataSet("worldcup.cql"));
		
		Insert insertStatement = QueryBuilder.insertInto("Match");
		insertStatement.value("number", QueryBuilder.bindMarker())
		 .value("status", QueryBuilder.bindMarker())
		 .value("location", QueryBuilder.bindMarker())
		 .value("datetime", QueryBuilder.bindMarker())
		 .value("winner", QueryBuilder.bindMarker())
		 .value("home_team_code", QueryBuilder.bindMarker())
		 .value("home_team_country", QueryBuilder.bindMarker())
		 .value("home_team_goals", QueryBuilder.bindMarker())
		 .value("away_team_code", QueryBuilder.bindMarker())
		 .value("away_team_country", QueryBuilder.bindMarker())
		 .value("away_team_goals", QueryBuilder.bindMarker());
		PreparedStatement ps = session.prepare(insertStatement.toString());
		BatchStatement batch = new BatchStatement();
		for(Match match : matchs){
			batch.add(ps.bind(match.getMatch_number(), 
					match.getStatus(), 
					match.getLocation(), 
					match.getDatetime(),
					match.getWinner(),
					match.getHome_team().getCode(),
					match.getHome_team().getCountry(),
					match.getHome_team().getGoals(),
					match.getAway_team().getCode(),
					match.getAway_team().getCountry(),
					match.getAway_team().getGoals()
			));
		}
		session.execute(batch);
		session.close();
		
	}

	private static void initSpark() {
		String cluster = "local";
		String job = "myJobName";
		String sparkHome = "";

		DeepSparkContext deepContext = new DeepSparkContext(cluster, job,
				sparkHome, new String[]{});
		ICassandraDeepJobConfig<Cells> config = DeepJobConfigFactory
				.create().host("localhost").cqlPort(9142)
				.keyspace("worldCup").table("match")
				.inputColumns("winner")
				.initialize();
		CassandraJavaRDD rdd = deepContext.cassandraJavaRDD(config);
		
		JavaPairRDD<String, Iterable<Cells>> groups = rdd.groupBy(new Function<Cells, String>() {
		    @Override
		    public String call(Cells cells) throws Exception {
		    	Object cellValue = cells.getCellByName("winner").getCellValue();
		        return cellValue!=null ? cellValue.toString() : null;
		    }
		});
		JavaPairRDD<String,Integer> counts = groups.mapToPair(new PairFunction<Tuple2<String, Iterable<Cells>>, String, Integer>() {
		    @Override
		    public Tuple2<String, Integer> call(Tuple2<String, Iterable<Cells>> t) throws Exception {
		        return new Tuple2<String,Integer>(t._1(), Lists.newArrayList(t._2()).size());
		    }
		});

		List<Tuple2<String,Integer>> results = counts.collect();
		LOGGER.info("GroupBy Results:");
		for(Tuple2<String,Integer> tuple : results){
			LOGGER.info(tuple.toString());
		}
	}

}
