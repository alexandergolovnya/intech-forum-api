# Intech Forum API
Project provides REST API for Intech forum. It includes role based OAuth authorization and authentication, CRUD operations for topic and topic messages.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [PostgreSQL](https://www.postgresql.org/)

## Heroku instance

There is an instance of application deployed to Heroku available at https://intech-forum-api.herokuapp.com/forum/api

## Swagger UI

There is an Swagger UI with info about available endpoints at https://intech-forum-api.herokuapp.com/forum/api/swagger-ui.html

## Running the application

### Building

``` bash
mvn clean install
```

### Running with default params

``` bash
mvn spring-boot:run
```

### Running with configuration for database

``` bash
mvn spring-boot:run -Dspring-boot.run.arguments=--DB_HOST=localhost,--DB_PORT=5432,--DB_NAME=intech-forum-api,--DB_USER=alexandergolovnya,--DB_USER_PASS=root
```
- DB_HOST - host of the Postgres server
- DB_PORT - port of the Postgres server
- DB_NAME - name of the Postgres database for this project
- DB_USER - username of the Postgres user
- DB_USER_PASS - password of the Postgres user

### Default users for the project

User with admin authority and access rights:
- email: admin@admin
- password: pass

User with default authority and access rights:
- email: user@user
- password: pass
