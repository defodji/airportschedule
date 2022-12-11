# Airport schedule backend application

This is a REST SpringBoot microservice that uses a PostgresSQL database and exposes 4 operations.
Every incoming flight has 5 properties: 
* flight number
* scheduled time
* city from
* airline
* aircraft
Every outgoing flight has 5 properties:
* flight number
* scheduled time
* city from
* airline
* aircraft
To simulate the status of each flight a random actual arrival/departure time is generated for each flight and uses to determine its status.

To build the microservice run:
`gradlew clean build`

To run the microservice, first start the Postgres Docker container with:
`./gradlew composeUp`
Then launch the microservice with:
`./gradlew bootRun`

To get the incoming flights, send a GET message:
`curl --location --request GET 'http://localhost:8080/airportschedule/arrivals'`

To get the outgoing flights, send a GET message:
`curl --location --request GET 'http://localhost:8080/airportschedule/departures'`

To create a departure, send a POST message :
`curl --location --request POST 'http://localhost:8080/airportschedule/departures' \
--header 'Content-Type: application/json' \
--data-raw '{
"flightNumber":"DL2176",
"scheduledTime":"09:30:00",
"to":"Chicago, USA",
"airline": "Delta Airlines",
"aircraft": "B626"
}'`

To create an arrival, send a POST message:
`curl --location --request POST 'http://localhost:8080/airportschedule/arrivals' \
--header 'Content-Type: application/json' \
--data-raw '{
"flightNumber":"JP2176",
"scheduledTime":"02:30:00",
"from":"Tokyo, Japan",
"airline": "Japan Airlines",
"aircraft": "B720"
}'`