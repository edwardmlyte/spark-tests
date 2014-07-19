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

mvn  clean package exec:java

Datastax - Use cases
-------

- From a specific roadtrip sets, compute the average roadtrip distance, grouped by origin city.
Here are the steps to perform this:
  + Insert roadtrips information (from src/main/resources/roadtrips.zip) into an embedded [Cassandra](http://cassandra.apache.org/) server (using [cassandra unit](https://github.com/jsevellec/cassandra-unit))
  + First, count the number of trips, grouped by origin city, using [Datastax Cassandra/Spark Connector](https://github.com/datastax/spark-cassandra-connector/), allowing using [Cassandra](http://cassandra.apache.org/) Database as a resilient distributed dataset (RDD) for [Spark](http://spark.apache.org/).
  + Finally, compute the average roadtrip distance, still grouped by origin city:<br/><br/>


Nb RoadTrips by origin
Albuquerque : 61
Raleigh/Durham : 62
Memphis : 24
Seattle : 31
Orlando : 154
Salt Lake City : 31
Newark : 61
Hartford : 31
Miami : 773
San Antonio : 176
New York : 978
Omaha : 57
Portland : 9
San Jose : 57
Austin : 194
Charlotte : 31
Kansas City : 93
Chicago : 1108
Fort Lauderdale : 31
Dayton : 31
San Francisco : 362
Tulsa : 62
Los Angeles : 957
Atlanta : 31
Indianapolis : 1
Fayetteville : 31
Wichita : 62
Columbus : 31
Washington : 358
St. Louis : 204
Kahului : 93
El Paso : 31
Oklahoma City : 31
Ontario : 36
Phoenix : 124
Santa Ana : 33
Baltimore : 27
Burbank : 8
Kona : 31
Las Vegas : 93
Norfolk : 50
Philadelphia : 8
Minneapolis : 30
Houston : 58
Lihue : 42
Palm Springs : 31
Honolulu : 164
San Juan : 62
Louisville : 1
Tampa : 124
Fort Myers : 31
Colorado Springs : 31
San Diego : 159
Boston : 212
Mission/McAllen/Edinburg : 30
West Palm Beach/Palm Beach : 62
Dallas/Fort Worth : 2275
Charlotte Amalie : 31

Average distance by origin
Albuquerque : 569.0
Raleigh/Durham : 880.5
Memphis : 432.0
Seattle : 2428.8387096774195
Orlando : 1313.7662337662337
Salt Lake City : 989.0
Newark : 1904.1311475409836
Hartford : 1471.0
Miami : 1404.1875808538164
San Antonio : 247.0
New York : 1639.402862985685
Omaha : 583.0
Portland : 1616.0
San Jose : 1643.7894736842106
Austin : 520.7835051546392
Charlotte : 936.0
Kansas City : 441.0
Chicago : 906.5361010830325
Fort Lauderdale : 1182.0
Dayton : 861.0
San Francisco : 2099.5552486187844
Tulsa : 448.61290322580646
Los Angeles : 2424.0010449320794
Atlanta : 731.0
Indianapolis : 761.0
Fayetteville : 280.0
Wichita : 328.0
Columbus : 926.0
Washington : 1322.2067039106146
St. Louis : 752.1764705882352
Kahului : 2881.043010752688
El Paso : 551.0
Oklahoma City : 175.0
Ontario : 1188.0
Phoenix : 1154.0
Santa Ana : 1315.5151515151515
Baltimore : 1217.0
Burbank : 1231.0
Kona : 2504.0
Las Vegas : 1605.6666666666667
Norfolk : 1212.0
Philadelphia : 1303.0
Minneapolis : 852.0
Houston : 619.5172413793103
Lihue : 2615.0
Palm Springs : 1126.0
Honolulu : 3112.8231707317073
San Juan : 1045.0
Louisville : 733.0
Tampa : 789.25
Fort Myers : 1120.0
Colorado Springs : 592.0
San Diego : 1558.4528301886792
Boston : 1871.1462264150944
Mission/McAllen/Edinburg : 469.0
West Palm Beach/Palm Beach : 1123.0
Dallas/Fort Worth : 1040.072087912088
Charlotte Amalie : 1623.0

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
