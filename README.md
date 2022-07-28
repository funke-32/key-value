# key-value

# Building

Java 8, Docker and Maven are required.
For the IDE, make sure you have [Lombok plugin](https://projectlombok.org/setup/overview) installed.

`mvn clean install`

This will create an image named kv-app:1.0 at local repository. 


# Running

ribbon, eureka, mongodb and rabbitmq are runtime dependencies.

user docker compose to start key-value microservice cluster, docker compose file is available at src/main/resources/docker-compose.yml

This cluster will have two instances of key-value service. At the start of service, nodes will have status as STARTING in registry server

Add node to cluster 

GET http://localhost:8080/up
GET http://localhost:8082/up

Remove node from cluster

GET http://localhost:8080/down
GET http://localhost:8082/down


# API

set key-vlaue
GET http://localhost/set?k=some-key&v=some-value

get key-vlaue
GET http://localhost/get?k=some-key

check if key exists
GET http://localhost/is?k=some-key

get all keys
GET http://localhost/getKeys?page=0&size=10

get all values
GET http://localhost/getValues?page=0&size=10

