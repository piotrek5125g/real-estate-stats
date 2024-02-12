
# Real estate stats project

Created with: **Spring Boot, Java 17, maven**.
***

The project consists of 2 main features:
1. A scheduler that reads real estate data from external api and puts it in DB
2. A Rest endpoint that can be used to query the DB for real estate prices

Project assumes an external API exists under location defined in application.properties
***
Can be built by using command: `mvn install`

and started by: `mvn spring-boot:run` 

before starting, update relevant properties in application.properties file such as database user and password, email server properties etc.
***
SQL file for DB creation in folder : **sql**
