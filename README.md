# nielsen-scheduler

## Project Overview

Appointment scheduling application for a car repair service, built in `Spring Boot` with `Spring Data JPA` as the persistence API and `H2` embedded database layer.

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

### API Documentation

#### Retrieve Appointments By Filter


##### Request

```
GET /appointments
```


| Query Param | Type          | Required | Example             |
|-------------|---------------|----------|---------------------|
| startDate   | LocalDateTime | Yes      | 2021-11-08T14:00:00 |
| endDate     | LocalDateTime | Yes      | 2021-11-09T14:00:00 |
| sortBy      | String        | No       | totalPrice          |
| orderBy     | String        | No       | ASC                 |

```
curl --location --request GET 'http://localhost:8080/appointments?startDate=2021-11-08T14:00:00&endDate=2021-12-18T14:00:00&sortBy=totalPrice&orderBy=ASC'
```

##### Response

```
[
    {
        "id": 59,
        "status": "SCHEDULED",
        "dateTime": "2021-11-21T13:00:00",
        "customer": {
            "id": 19,
            "name": "Customer 18",
            "phoneNumber": null,
            "address1": "123 Main Street",
            "address2": null,
            "state": "NY",
            "city": "Nowhere",
            "zipCode": "12345",
            "vehicleLicensePlate": "1E11A",
            "vehicleModelYear": 2016,
            "vehicleMakeModel": "Ford Focus"
        },
        "vehicleServices": [
            {
                "price": 127.0,
                "serviceId": "b1dc",
                "serviceName": "Brakes"
            }
        ],
        "totalPrice": 127.0
    }
 ]

```


#### Retrieve Specific Appointment

##### Request

```
GET /appointments/{uid}
```

| Path Param  | Type          | Required | Example             |
|-------------|---------------|----------|---------------------|
| uid         | Long          | Yes      | 123                 |

```
curl --location --request GET 'http://localhost:8080/appointment/51'
```

##### Response

```
{
    "id": 51,
    "status": "SCHEDULED",
    "dateTime": "2021-11-26T10:00:00",
    "customer": {
        "id": 26,
        "name": "Customer 25",
        "phoneNumber": null,
        "address1": "123 Main Street",
        "address2": null,
        "state": "NY",
        "city": "Nowhere",
        "zipCode": "12345",
        "vehicleLicensePlate": "C18A8",
        "vehicleModelYear": 2019,
        "vehicleMakeModel": "Honda Civic"
    },
    "vehicleServices": [
        {
            "price": 137.0,
            "serviceId": "3d87",
            "serviceName": "Oil Change"
        }
    ],
    "totalPrice": 137.0
}
```


#### Create Appointment

##### Request

```
POST /appointment
```

```
curl --location --request POST 'http://localhost:8080/appointment' \
--header 'Content-Type: application/json' \
--data-raw '{
    "status": "SCHEDULED",
    "dateTime": "2021-12-02T12:00:00",
    "customer": {
        "id": 45,
        "name": "Customer 44",
        "phoneNumber": null,
        "address1": "123 Main Street",
        "address2": null,
        "state": "NY",
        "city": "Nowhere",
        "zipCode": "12345",
        "vehicleLicensePlate": "D47DA",
        "vehicleModelYear": 2012,
        "vehicleMakeModel": "Nissan Altima"
    },
    "vehicleServices": [
        {
            "price": 206.0,
            "serviceId": "a457",
            "serviceName": "Oil Change"
        },
        {
            "price": 239.0,
            "serviceId": "fb2a",
            "serviceName": "Brakes"
        },
        {
            "price": 162.0,
            "serviceId": "1487",
            "serviceName": "Inspection"
        }
    ]
}'
```


#### Update Appointment

##### Request

```
PUT /appointment
```

```
curl --location --request POST 'http://localhost:8080/appointment' \
--header 'Content-Type: application/json' \
--data-raw '{
    "status": "SCHEDULED",
    "id": 123
}'
```



#### Delete Appointment

##### Request

```
DELETE /appointment/{uid}
```

| Path Param  | Type          | Required | Example             |
|-------------|---------------|----------|---------------------|
| uid         | Long          | Yes      | 123                 |

```
curl --location --request DELETE 'http://localhost:8080/appointment/51'
```


