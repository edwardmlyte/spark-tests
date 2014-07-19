package blog.hashmade.spark;

import java.io.IOException;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RestAdapter;
import scala.Tuple2;
import blog.hashmade.spark.retrofit.WorldCupService;
import blog.hashmade.spark.retrofit.bean.Match;
import blog.hashmade.spark.util.MatchUtil;

import com.google.common.collect.Lists;
import com.stratio.deep.config.DeepJobConfigFactory;
import com.stratio.deep.config.ICassandraDeepJobConfig;
import com.stratio.deep.context.DeepSparkContext;
import com.stratio.deep.entity.Cells;
import com.stratio.deep.rdd.CassandraJavaRDD;

public class StratioGroupByTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StratioGroupByTest.class);
	
	public static void main(String[] args) throws IOException {
		try {
			MatchUtil.initCassandraWithMatchs(retrieveMatchs());
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
				//.filterByField(filterColumnName, filterValue)
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
