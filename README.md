# key-value

# Building

Java 8, Docker and Maven are required.
For the IDE, make sure you have [Lombok plugin](https://projectlombok.org/setup/overview) installed.

`mvn clean install`

This will create an image named kv-app:1.0 at local repository. 


# Running

mongodb and rabbitmq are runtime external dependencies.

user docker compose to start key-value microservice cluster, docker compose file is available at src/main/resources/docker-compose.yml

Default profile is set as `SPRING_PROFILES_ACTIVE=cache` which stores key values in memory which is synched across nodes in cluster.
You can remove this from docker compose to run the key-value service with mongo-db 

Please follow start procedure to have clean startup as rabbitmq takes more time to boot

1. `docker compose -f docker-compose.yml up -d kv-mongo`
2. `docker compose -f docker-compose.yml up -d rabbitmq`
3. `docker compose -f docker-compose.yml up -d eureka`
4. `docker compose -f docker-compose.yml up -d ribbon`

5. instance 1 for key-value service running on 8080
    `docker compose -f docker-compose.yml up kv-app-1`
    
    instance 2 for key-value service running on 8082
    `docker compose -f docker-compose.yml up kv-app-2`
    
This cluster will have two instances of key-value service. At the start of service, nodes will have status as STARTING in registry server

Add node to cluster 

GET http://localhost:8080/up
GET http://localhost:8082/up

Remove node from cluster

GET http://localhost:8080/down
GET http://localhost:8082/down


# API

load balancer Ribbon is running on port 80, following are the REST api enddpoints for key-value service 

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

remove by key
GET http://localhost/rm?k=some-key

clear all
GET http://localhost/clear

# Load Test using Jmeter

Load test can be run using maven command 
    
    `mvn jmeter:jmeter`

results in csv can be seen at target/jmeter/results/<date>-kv-load-test.csv   


