spark-tests
===============

This repo contains tests involving [Spark](http://spark.apache.org/) using a [Cassandra](http://cassandra.apache.org/) Data source, through the [Stratio API](http://www.openstratio.org/).

Installation prerequisites
-------

To run these tests, you need:
- JDK 8 or greater (you can use ealier versions, just adapt pom.xml accordingly)
- Git
- Maven

Download the project
-------

git clone https://github.com/jsebrien/spark-tests.git

Run tests
-------

mvn exec:java

Use cases
-------

- Perform a "group By" winner country and display, for each team, how many times they win during World Cup (until now).
Here are the steps to perform this:
  + Retrieve world cup matchs statistics from [worldcup.sfg.io](http://worldcup.sfg.io/) json API, using [retrofit](http://square.github.io/retrofit/)
  + Store retrieved statistics into an embedded [Cassandra](http://cassandra.apache.org/) server (using [cassandra unit](https://github.com/jsevellec/cassandra-unit))
  + Perform a "group By" winner country accross all matches, using [Stratio](http://www.openstratio.org/), allowing using [Cassandra](http://cassandra.apache.org/) Database as a spark resilient distributed dataset (RDD) for [Spark](http://spark.apache.org/).
  + The following results will appear (on 29 June 2014):<br/><br/>
GroupBy Results:<br/>
(null,14)<br/>
(Portugal,1)<br/>
(Brazil,3)<br/>
(Netherlands,3)<br/>
(Draw,9)<br/>
(Uruguay,2)<br/>
(Colombia,4)<br/>
(Argentina,3)<br/>
(Chile,2)<br/>
(Croatia,1)<br/>
(Belgium,3)<br/>
(Mexico,2)<br/>
(Ecuador,1)<br/>
(Greece,1)<br/>
(France,2)<br/>
(Italy,1)<br/>
(Ivory Coast,1)<br/>
(Algeria,1)<br/>
(Switzerland,2)<br/>
(Spain,1)<br/>
(Germany,2)<br/>
(USA,1)<br/>
(Costa Rica,2)<br/>
(Bosnia and Herzegovina,1)<br/>
(Nigeria,1)<br/>
