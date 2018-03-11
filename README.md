# Almundo CallCenter API
This git repository contains the source code of a CallCenter API simulator to apply for Almundo senior backend developer job.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine. See deployment for notes on how to deploy the project on a live system.

### Prerrequisites
Technologies you need to install the software and where to download them:

Technology | Version | Download URL
------------ | ------------- | -------------
Java SE & JDK | 1.8.0_151 | http://www.oracle.com/technetwork/java/javase/downloads

Maven is optional because the project has an embedded version installed on it.

### Installing
A step by step series of examples that tell you have to get a local or production environment running.

#### Local Environment

##### Run liquibase
This command will allows you to create an H2 local database with the tables needed by the application:

```
sh mvnw liquibase:update -P local
```

The tests are connected to a separated database. In order to run them you need to create a test database and it can be achieved running the following command:

```
sh mvnw liquibase:update -P local-test
```

For any case you will get an output message like this:

```
INFO 3/11/18 1:30 AM: liquibase: Successfully released change log lock
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.859 s
[INFO] Finished at: 2018-03-11T01:30:41-05:00
[INFO] Final Memory: 24M/403M
[INFO] ------------------------------------------------------------------------
```

#### Production Environment

##### Run liquibase
This command will allows you to create an H2 local database with the tables needed by the application:

```
sh mvnw liquibase:update -P prod
```

The tests are connected to a separated database. In order to run them you need to create a test database and it can be achieved running the following command:

```
sh mvnw liquibase:update -P prod-test
```

For any case you will get an output message like this:

```
INFO 3/11/18 1:30 AM: liquibase: Successfully released change log lock
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.859 s
[INFO] Finished at: 2018-03-11T01:30:41-05:00
[INFO] Final Memory: 24M/403M
[INFO] ------------------------------------------------------------------------
```

## Running the tests

You can run unit and integration tests using Maven.

### Local Environment

Local tests are disabled by default.

To run only unit tests you can execute the command as follows:

```
sh mvnw test -DskipUnitTests=false -Dspring.profiles.active=local -P local
```

For integration tests use the command below:

```
sh mvnw verify -DskipIntegrationTests=false -Dspring.profiles.active=local -P local
```

For full tests execute the command as next:

```
sh mvnw verify -DskipUnitTests=false -DskipIntegrationTests=false -Dspring.profiles.active=local -P local
```

### Production Environment

Production tests are enabled by default.

To run only unit tests you can execute the command as follows:

```
sh mvnw test -Dspring.profiles.active=prod -P prod
```

For integration tests use the command below:

```
sh mvnw verify -DskipUnitTests=true -Dspring.profiles.active=prod -P prod
```

For full tests execute the command as next:

```
sh mvnw verify -Dspring.profiles.active=prod -P prod
```

## Deployment

This section explains how to deploy the project in local and production environments using Maven.

### Local Environment

To generate the project sources for a local environment you need to execute the following command:

```
sh mvnw clean install -Dspring.profiles.active=local -P local
```

Then you can run your application:

```
java -jar target/myapplication-0.0.1-SNAPSHOT.jar
```

Now you can consume the REST API provided from port 15000.

### Production Environment

To generate the project sources for a production environment you need to execute the following command:

```
sh mvnw clean install -Dspring.profiles.active=prod -P prod
```

Then you can run your application:

```
java -jar target/myapplication-0.0.1-SNAPSHOT.jar
```

Now you can consume the REST API provided from port 15000.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management


## API Documentation

### Employee Module

#### Create

```
POST /call-center/api/employee
```

Creates a new employee in the system.

##### Request Data

Field | Description | Optional
------------ | ------------- | -------------
names | The employee names. | No
surnames | The employee surnames. | No
email | The employee email. | No
type | The employee type (OPERATOR, SUPERVISOR or DIRECTOR). | No

###### Example

```
{
	"names": "Andrés Felipe",
	"surnames": "Pedraza Infante",
	"email": "elpatopedraza@gmail.com",
	"type": "OPERATOR"
}
```

##### Response Data

Field | Description
------------ | -------------
id | The employee identifier.
names | The employee names.
surnames | The employee surnames.
email | The employee email.
type | The employee type (OPERATOR, SUPERVISOR, DIRECTOR).
available | Indicates if employee is available to answer calls or not.

###### Example

```
{
    "id": "97f2806b-3093-46da-9679-e46e9ca0fee9",
    "names": "Andrés Felipe",
    "surnames": "Pedraza Infante",
    "email": "elpatopedraza@gmail.com",
    "type": "OPERATOR",
    "available": true
}
```

### Dispatcher Module

#### Dispatch

```
POST /call-center/api/dispatcher/dispatch
```

Creates a new call and tries to assign an employee to attend it.

##### Request Data

No request data to send.

##### Response Data

Field | Description
------------ | -------------
id | The call identifier.
employee | The employee that attends the call.
durationInSecs | The total call duration in seconds.
state | The current call state (CREATED, QUEUED, IN PROGRESS, FINISHED).
type | The employee type (OPERATOR, SUPERVISOR or DIRECTOR).
available | Indicates if employee is available to answer calls or not.

###### Example

```
{
    "id": "6651336e-e2f3-4151-8fa0-4128916849fe",
    "employee": {
        "id": "97f2806b-3093-46da-9679-e46e9ca0fee9",
        "names": "Andrés Felipe",
        "surnames": "Pedraza Infante",
        "email": "elpatopedraza@gmail.com",
        "type": "OPERATOR",
        "available": false
    },
    "durationInSecs": 5,
    "state": "IN_PROGRESS"
}
```

### Call Module

#### List All

```
GET /call-center/api/call
```

Lists all calls registered in the system.

##### Request Data

No request data to send.

##### Response Data

Field | Description
------------ | -------------
id | The call identifier.
employee | The employee that attends the call.
durationInSecs | The total call duration in seconds.
state | The current call state (CREATED, QUEUED, IN PROGRESS, FINISHED).
type | The employee type (OPERATOR, SUPERVISOR or DIRECTOR).
available | Indicates if employee is available to answer calls or not.

###### Example

```
[
    {
        "id": "6651336e-e2f3-4151-8fa0-4128916849fe",
        "employee": {
            "id": "97f2806b-3093-46da-9679-e46e9ca0fee9",
            "names": "Andrés Felipe",
            "surnames": "Pedraza Infante",
            "email": "elpatopedraza@gmail.com",
            "type": "OPERATOR",
            "available": false
        },
        "durationInSecs": 5,
        "state": "FINISHED"
    },
    {
        "id": "82567b9b-7d90-4b31-8492-3dd6d79a05f3",
        "employee": {
            "id": "97f2806b-3093-46da-9679-e46e9ca0fee9",
            "names": "Andrés Felipe",
            "surnames": "Pedraza Infante",
            "email": "elpatopedraza@gmail.com",
            "type": "OPERATOR",
            "available": false
        },
        "durationInSecs": 7,
        "state": "IN_PROGRESS"
    },
    {
        "id": "5eef5510-9b96-4e63-80b1-730b68923c06",
        "employee": null,
        "durationInSecs": null,
        "state": "QUEUED"
    }
]
```

## Authors

* **Andrés Felipe Pedraza Infante** - *Java Spring / PHP Laravel Backend Developer* - [GitHub](https://github.com/elpatopedraza)