# nielsen-scheduler

## Project Overview

Appointment scheduling application built in `Spring Boot` with `Spring Data JPA` as the persistence API and `H2` embedded database layer.

### Libraries and frameworks used

* Spring Boot Web

* Spring Security

* Spring Data JPA

* Netty

* H2 Embedded Database

* Lombok

* JUnit

* Gradle

* Docker

## Local Setup

### Pre-requisites

* JDK8

* Git CLI 

#### Steps

1. Open `Terminal` if macOS/Unix or `Command Prompt` if Windows.

2. Clone this project: `https://github.com/samwhile/nielsen-scheduler.git`

4. In the project terminal, run the following command:

```
macOS: ./gradlew clean build

Windows: gradlew clean build
```


### Running Locally

#### Command Line

```
macOS: ./gradlew bootRun
Windows: gradlew bootRun
```

#### Docker

##### Build The Image

```
docker build -t nielsen-scheduler .
```

##### Run The Image

```
docker run -p 8080:8080 nielsen-scheduler
```

#### Docker Compose

##### Build The Image

```
docker-compose up
```

