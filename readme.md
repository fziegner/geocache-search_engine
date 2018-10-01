# Geocache Search Engine
### Installation

GeocacheSearchEngine needs the following requirements to run:
* [Java JDK 10](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html) for compilation
* [Maven](https://maven.apache.org/) to run the build
* [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi) to be deployed on
* [PostgreSQL](https://www.postgresql.org/) if you want to enable logging

The following dependencies will be downloaded via maven:
* [Apache Lucene](https://lucene.apache.org/)
* [Jersey JAX-RS](https://jersey.github.io/)
* [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc )
* [Java Servlet API](https://javaee.github.io/servlet-spec/)
* [JSON In Java](https://github.com/douglascrockford/JSON-java )

First install JDK 10 by following [this guide](https://websiteforstudents.com/install-oracle-java-jdk-10-on-ubuntu-16-04-17-10-18-04-via-ppa/).
Previous JDK version (including openjdk) might work, but it is not guaranteed.
Next install Maven by executing:
```sh
sudo apt-get install maven
```
You now have everything ready for compilation of the search engine.
Next up you need to download and unpack the tomcat server. Please use [this guide](https://tecadmin.net/install-tomcat-9-on-ubuntu/).

To compile, navigate into the project folder and execute the following command:
```sh
$ mvn install
```
or
```sh
$ mvn package
```

Now navigate into the /target folder. You should see a file named geocache-search-engine.war. This file holds the compiled and ready-to-run project. You don't need to execute any java-classes beforehand, relevant functions such as creating an index will be accessed via REST.

Bevor you start your Tomcat Server, navigate into its bin directory (tomcat/bin). Copy the data folder from our repository into this directory (it should contain 19.985 JSON's).
This step can be omitted, basically the data folder can be anywhere you want, but the tomcat server needs to be started from the directory where the data folder is located. For simplification we recommend putting the data folder inside the /bin directory of your tomcat server.

You can now deploy the search engine by copying the geocache-search-engine.war (from the /target folder of the project) into the /webapps directory of the tomcat server (or use the tomcat application manager by starting the tomcat server and visiting localhost:8080/manager/html in your browser).

As a last step, it is important to start the tomcat server from the location where the data folder is present. If you previously followed our recommendation, navigate into the /tomcat/bin folder and run the start script:
```sh
$ cd /yourPath/toTomcat/bin
$ ./startup.sh
```

If the deployment was successfull, you should see a description of our REST-API when visiting:
```sh
$ http://localhost:8080/geocache-search-engine/
```

To create an index , visit:
```sh
$ http://localhost:8080/geocache-search-engine/webapi/index/create
```
You can follow the indexing process in your Tomcat console.

To visit the web interface, open the index.html in the /Code/webinterface - localhost folder. This one will use your server on localhost.
You can also simply use the /code/webinterface directory. This one will use a public server that we set up (online until 15.10.2018).

Please NOTE, that every browser except Firefox had problems with CORS-Filters regarding the communication between backend and frontend when just using the index.html file.
If you want to use another browser, or happen to have those problems even on Firefox, use the following workaround with Python 3.x :

Navigate into the /webinterface directory. Open a terminal and execute:
```sh
$ python -m http.server 10001
```
and then type the following address into your browser to display the frontend:
```sh
$ http://localhost:10001
```
It will start a second http server on localhost, port 10001. This works, because now communication happens between two servers, which should be fine for CORS-Filters.

This guide covers the whole installation process (except for Logging, please see following section) including some edge-cases.
If you happen to have any problems and/or errors trying it yourself, please contact us (cs85xyqa@studserv.uni-leipzig.de). If possible, include your error log located in /tomcat/logs/catalina.out

### Logging

If you want to enable logging, you need to download and set up a PostgreSQL Database.

To download and install it:

```sh
$ sudo apt-get install postgresql
```
This will install the latest version of Postgresql. Please do not change the default port (it should be 5432).

Next you need to reset the password of the postgres superuser.
Switch to the postgres user:

```sh
$ su -u postgres
```

and open the PostgreSQL shell with
```sh
$ psql
```

You should be inside the PostgreSQL shell. Now set the password to "geocache" by executing the following command:
```sh
$ ALTER USER postgres WITH PASSWORD 'geocache';
```

You can now leave the psql shell.

As a next step, you need to create the Table which is given by create.sql in our repository. Simply execute the create.sql script by:
```sh
$ psql -U postgres -d postgres -a -f path/to/file/create.sql
```
or if you are inside the psql shell:

```sh
$ \i path/to/file/create.sql
```
If you get a Permission denied error, you may want to do
```sh
$ chmod 777 create.sql
```
to allow read/write permission.

As a last resort you could still copy and paste it into a psql shell.

If everything worked fine, you should see a table "Logs" in the list when executing
```sh
$ \d
```
from inside a psql shell.

From now on Logging should be working fine. Every search will now be logged into this table.
You can view its content by typing
```sh
$ SELECT * FROM Logs;
```
in a psql shell.
