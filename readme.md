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
Next install Maven by executing:
```sh
sudo apt-get install maven
```
You now have everything ready for compilation of the search engine.
Next up you need to download and unpack the tomcat server. Please use [this guide](https://tecadmin.net/install-tomcat-9-on-ubuntu/).

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

### Logging

If you want to enable logging, you need to download and set up a PostgreSQL Database.

To download and install it:

```sh
sudo apt-get install postgresql
```
This will install the latest version of Postgresql. Please do not change the default port (it should be 5432).

Next you need to reset the password of the postgres superuser.
Switch to the postgres user:

```sh
su -u postgres
```

and open the PostgreSQL shell with
```sh
psql
```

You should be inside the PostgreSQL shell. Now set the password to "geocache" by executing the following command:
```sh
ALTER USER postgres WITH PASSWORD 'geocache';
```

You can now leave the psql shell.

As a last step you need to create the Table. Simply execute the create.sql script by:
```sh
psql -U postgres -d postgres -a -f path/to/file/create.sql
```
or if you are inside the psql shell:

```sh
\i path/to/file/create.sql
```
If you get a Permission denied error, you may want to do
```sh
chmod 777 create.sql
```
to allow read/write permission.

As a last resort you can still copy and paste it into a psql shell.

If everything worked fine, you should see a table "Logs" in the list when executing
```sh
\d
```
from inside a psql shell.

From now on Logging should be working fine. Every search will now be logged into this table.
You can view its content by typing 
```sh
SELECT * FROM Logs;
``` 
in a psql shell.
