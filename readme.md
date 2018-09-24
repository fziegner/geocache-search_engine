# Geocache Search Engine
### Installation

GeocacheSearchEngine needs to following requirements to run:
* [Java JDK 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html) for compilation
* [Maven](https://maven.apache.org/) to run the build 
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi) to be deployed on

The following dependencies will be downloaded via maven:
* [Apache Lucene](https://lucene.apache.org/)
* [Jersey JAX-RS](https://jersey.github.io/)
* [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc )
* [Java Servlet API](https://javaee.github.io/servlet-spec/)
* [JSON In Java](https://github.com/douglascrockford/JSON-java )

Navigate into the project folder and execute the following command:
```sh
$ mvn install
```
or 
```sh
$ mvn package
```

Now navigate into the /target folder. You should see a file named geocache-search-engine.war. Deploy this file into your Tomcat-Server and start it.
<br><br>
IMPORTANT: You need to start your tomcat from the directory where the data folder is located.
e.g.: If you want to deploy your webapp through the tomcat application manager, you need to copy the data folder from our root repository (there should be 19.985 JSON's in there) to /tomcat/bin/
<br><br>
To create an index , visit:
```sh
http://localhost:8080/geocache-search-engine/webapi/index/create
```
You can follow the indexing process in your Tomcat console.

To visit the web interface open the index.html in the /Code/webinterface - localhost folder.

To view a description of our REST API, visit:
```sh
http://localhost:8080/geocache-search-engine/
```
in your preferred browser.