FROM openjdk:8
ADD target/kv-app.jar kv-app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n","-jar","kv-app.jar"]