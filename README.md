Bloffee
=======

![bloffee_small](https://cloud.githubusercontent.com/assets/6644095/11587898/179a7386-9a7d-11e5-93db-1ae89fa5aae9.png)

Bloffee is a token-based authentication service accessible via a rest API and developped in Java using the Spring framework

Installation
------------

Clone this project, go into the project root folder, then type:

```mvn clean install && mvn -pl services-rest tomcat7:run```

The server should be running now, you can now call the urls defined in the following section "Documentation"

or you can execute the example client by typing:

```java -jar client/target/ame-client-*-jar-with-dependencies.jar```

Documentation
----

You can find the REST documentation here: https://github.com/masters-info-nantes/bloffee/tree/master/doc/rest-documentation.yaml

Authors
-------

Created by Adrien Garandel & Nicolas Brondin
