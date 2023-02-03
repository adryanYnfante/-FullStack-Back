# RetoSofka - FullStack-Back

Reactive api Using Springboot webflux and from BD mongo reactive

##Project Content

Database in MongoDB

 ###Java Backend

Project Created with SpringBoot webflux in java11.
[https://www.oracle.com/co/java/technologies/javase/jdk11-archive-downloads.html)

```
Port used: 8080 (can be modified in application.properties)

Configure the connection path to the Mongo DB with your username and password. (Also in application.properties)
```

Class QuestionRouter

```
Class where all the endpoints are to access the consumption of the api
```

useCase package
```
all business logic
```

collections package

```
You will find the classes of the data models to use
```
models package

```
(DTOs)Data object transfer for the management collections
```


### Swagger 3.0

API consumption and documentation

```
http://localhost:8080/swagger-doc/swagger-ui.html
(can be modified in application.properties)

```

reposioties package
```
It is in charge of establishing the methods for related queries
with data management
```

## Author

* **Mike Arango Nieto** - * Coding and Documentation *

