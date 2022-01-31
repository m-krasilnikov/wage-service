# Wage service 

## Description
The project contains two services:
* **producer-service** - contains one REST controller that handles incoming messages and sends them in the Kafka MQ
* **consumer-service** - gets messages from the queue and save them in the db

## RUN
The easiest way to run the whole system(service, kafka, db etc) is using docker-compose at the root of the project.  
The application is dockerized, but, anyway, you need to build images  

It's possible to build images via command: 
> ./gradlew :consumer-service:jibDockerBuild :producer-service:jibDockerBuild

And, after then run compose:

> docker-compose up 

**NB:** The Tax_rate changing via ENV variable: **TAX_RATE** in the consumer-service!

## Swagger
After application has successfully started you can check swagger UI console by the following URL  
`http://{service_host}:{service_port}/swagger-ui.html`

Path to the API: `/v3/api-docs`

## Healthcheck 
Information about services health is available by the following URL:  
  `http://{service_host}:{service_port}/actuator/health`

## Suggestions for the improvement 
* Change composite key in the DB on unique Id
* Add compensatory action to the consumer. For example in case of any exception resend the message in the another queue or save it in the DB as is.
* Add controller in the consumer-service, that allows polling DB to check if message saved or not. Async. request/replay pattern. 