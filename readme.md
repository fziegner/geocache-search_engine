# Geocache Search Engine
### Installation

GeocacheSearchEngine needs to following requirements to run:
* [Java JDK 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi) to be deployed on 

The following dependencies will be downloaded via maven:
* [Apache Lucene]()
* [Jersey JAX-RS]()

Navigate into the project folder and execute the following command:
```sh
$ mvn install
```
or 
```sh
$ mvn package
```

Now navigate into the /target folder. 
You should see a file named geocache-search-engine.war
Deploy this file into your Tomcat-Server and start it. Per default, visit:
```sh
http://localhost:8080/geocache-search-engine/
```
in your preferred browser. If everything is working correctly, you should see the index.jsp
which currently display a "Hello World!"
