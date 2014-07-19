spark-tests
===============

This repo contains tests involving [Spark](http://spark.apache.org/) using a [Cassandra](http://cassandra.apache.org/) Data source, through the [Datastax Cassandra/Spark Connector](https://github.com/datastax/spark-cassandra-connector/) and [Stratio API](http://www.openstratio.org/).

Installation prerequisites
-------

To run these tests, you need:
- JDK 7 or greater
- Git
- Maven

Download the project
-------

git clone https://github.com/jsebrien/spark-tests.git

Datastax - Run tests
-------

First you need to import spark-cassandra-connector_2.10-1.0.0-beta1.jar in your maven repository:<br/><br/>
mvn install:install-file -Dfile=lib/spark-cassandra-connector_2.10-1.0.0-beta1.jar -DgroupId=com.datastax -DartifactId=spark-cassandra-connector_2.10 -Dversion=1.0.0-beta1 -Dpackaging=jar<br/><br/>

Then start the test:<br/><br/>
mvn  clean package exec:java

Datastax - Use cases
-------

- From a specific roadtrip sets, compute the average roadtrip distance, grouped by origin city.
Here are the steps to perform this:
  + Insert roadtrips information (from src/main/resources/roadtrips.zip) into an embedded [Cassandra](http://cassandra.apache.org/) server (using [cassandra unit](https://github.com/jsevellec/cassandra-unit))
  + First, count the number of trips, grouped by origin city, using [Datastax Cassandra/Spark Connector](https://github.com/datastax/spark-cassandra-connector/), allowing using [Cassandra](http://cassandra.apache.org/) Database as a resilient distributed dataset (RDD) for [Spark](http://spark.apache.org/).
  + Finally, compute the average roadtrip distance, still grouped by origin city:<br/><br/>


Nb RoadTrips by origin<br/>
Albuquerque : 61<br/>
Raleigh/Durham : 62<br/>
Memphis : 24<br/>
Seattle : 31<br/>
Orlando : 154<br/>
Salt Lake City : 31<br/>
Newark : 61<br/>
Hartford : 31<br/>
Miami : 773<br/>
San Antonio : 176<br/>
New York : 978<br/>
Omaha : 57<br/>
Portland : 9<br/>
San Jose : 57<br/>
Austin : 194<br/>
Charlotte : 31<br/>
Kansas City : 93<br/>
Chicago : 1108<br/>
Fort Lauderdale : 31<br/>
Dayton : 31<br/>
San Francisco : 362<br/>
Tulsa : 62<br/>
Los Angeles : 957<br/>
Atlanta : 31<br/>
Indianapolis : 1<br/>
Fayetteville : 31<br/>
Wichita : 62<br/>
Columbus : 31<br/>
Washington : 358<br/>
St. Louis : 204<br/>
Kahului : 93<br/>
El Paso : 31<br/>
Oklahoma City : 31<br/>
Ontario : 36<br/>
Phoenix : 124<br/>
Santa Ana : 33<br/>
Baltimore : 27<br/>
Burbank : 8<br/>
Kona : 31<br/>
Las Vegas : 93<br/>
Norfolk : 50<br/>
Philadelphia : 8<br/>
Minneapolis : 30<br/>
Houston : 58<br/>
Lihue : 42<br/>
Palm Springs : 31<br/>
Honolulu : 164<br/>
San Juan : 62<br/>
Louisville : 1<br/>
Tampa : 124<br/>
Fort Myers : 31<br/>
Colorado Springs : 31<br/>
San Diego : 159<br/>
Boston : 212<br/>
Mission/McAllen/Edinburg : 30<br/>
West Palm Beach/Palm Beach : 62<br/>
Dallas/Fort Worth : 2275<br/>
Charlotte Amalie : 31<br/><br/>

Average distance by origin<br/>
Albuquerque : 569.0<br/>
Raleigh/Durham : 880.5<br/>
Memphis : 432.0<br/>
Seattle : 2428.8387096774195<br/>
Orlando : 1313.7662337662337<br/>
Salt Lake City : 989.0<br/>
Newark : 1904.1311475409836<br/>
Hartford : 1471.0<br/>
Miami : 1404.1875808538164<br/>
San Antonio : 247.0<br/>
New York : 1639.402862985685<br/>
Omaha : 583.0<br/>
Portland : 1616.0<br/>
San Jose : 1643.7894736842106<br/>
Austin : 520.7835051546392<br/>
Charlotte : 936.0<br/>
Kansas City : 441.0<br/>
Chicago : 906.5361010830325<br/>
Fort Lauderdale : 1182.0<br/>
Dayton : 861.0<br/>
San Francisco : 2099.5552486187844<br/>
Tulsa : 448.61290322580646<br/>
Los Angeles : 2424.0010449320794<br/>
Atlanta : 731.0<br/>
Indianapolis : 761.0<br/>
Fayetteville : 280.0<br/>
Wichita : 328.0<br/>
Columbus : 926.0<br/>
Washington : 1322.2067039106146<br/>
St. Louis : 752.1764705882352<br/>
Kahului : 2881.043010752688<br/>
El Paso : 551.0<br/>
Oklahoma City : 175.0<br/>
Ontario : 1188.0<br/>
Phoenix : 1154.0<br/>
Santa Ana : 1315.5151515151515<br/>
Baltimore : 1217.0<br/>
Burbank : 1231.0<br/>
Kona : 2504.0<br/>
Las Vegas : 1605.6666666666667<br/>
Norfolk : 1212.0<br/>
Philadelphia : 1303.0<br/>
Minneapolis : 852.0<br/>
Houston : 619.5172413793103<br/>
Lihue : 2615.0<br/>
Palm Springs : 1126.0<br/>
Honolulu : 3112.8231707317073<br/>
San Juan : 1045.0<br/>
Louisville : 733.0<br/>
Tampa : 789.25<br/>
Fort Myers : 1120.0<br/>
Colorado Springs : 592.0<br/>
San Diego : 1558.4528301886792<br/>
Boston : 1871.1462264150944<br/>
Mission/McAllen/Edinburg : 469.0<br/>
West Palm Beach/Palm Beach : 1123.0<br/>
Dallas/Fort Worth : 1040.072087912088<br/>
Charlotte Amalie : 1623.0<br/><br/>

Stratio - Run tests
-------

mvn -Pstratio clean package exec:java

Stratio - Use cases
-------

- Perform a "group By" winner country and display, for each team, how many times they win during World Cup (until now).
Here are the steps to perform this:
  + Retrieve world cup matchs statistics from [worldcup.sfg.io](http://worldcup.sfg.io/) json API, using [retrofit](http://square.github.io/retrofit/)
  + Store retrieved statistics into an embedded [Cassandra](http://cassandra.apache.org/) server (using [cassandra unit](https://github.com/jsevellec/cassandra-unit))
  + Perform a "group By" winner country accross all matches, using [Stratio](http://www.openstratio.org/), allowing using [Cassandra](http://cassandra.apache.org/) Database as a resilient distributed dataset (RDD) for [Spark](http://spark.apache.org/).
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
