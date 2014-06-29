spark-tests
===============

Spark use cases
====================
This repo contains tests involving spark using a Cassandra Data source, through the Stratio API (openstratio.org).

Installation prerequisites
-------

To run these tests, you need:
- JDK 8 or greater (you can use ealier versions, just adapt pom.xml accordingly)
- Git
- Maven

Download the project
-------

git clone https://github.com/jsebrien/spark-tests.git

-------

Run tests
-------

mvn exec:java

Use cases
-------

- Perform a "group By" winner country and display, for each team, how many times they win during World Cup (until now).
Here are the steps to perform this:
* Retrieve world cup matchs using worldcup.sfg.io json API (using retrofit)
* Store retrieved statistics into an embedded cassandra server (using cassandra unit)
* Perform a "group By" winner country accross all matches, using statio API (using stratio), allowing using Cassandra Database as a spark resilient distributed dataset (RDD).
The following results will appear (on 29 June 2014):
GroupBy Results:
(null,14)
(Portugal,1)
(Brazil,3)
(Netherlands,3)
(Draw,9)
(Uruguay,2)
(Colombia,4)
(Argentina,3)
(Chile,2)
(Croatia,1)
(Belgium,3)
(Mexico,2)
(Ecuador,1)
(Greece,1)
(France,2)
(Italy,1)
(Ivory Coast,1)
(Algeria,1)
(Switzerland,2)
(Spain,1)
(Germany,2)
(USA,1)
(Costa Rica,2)
(Bosnia and Herzegovina,1)
(Nigeria,1)
