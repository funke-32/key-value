# key-value

# Building

Java 8, Docker and Maven are required.
For the IDE, make sure you have [Lombok plugin](https://projectlombok.org/setup/overview) installed.

`mvn clean install`

This will create an image named kv-app:1.0 at local repository. 


# Running

ribbon, eureka, mongodb and rabbitmq are runtime dependencies.

user docker compose to start key-value microservice cluster, docker compose file is available at src/main/resources/docker-compose.yml

This cluster will have two instances of key-value service
