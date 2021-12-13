FROM openjdk:11-jdk-slim

VOLUME /tmp
#ARG JAR_FILE
COPY target/iot-consumer-simulator-api.jar app.jar
ENTRYPOINT ["java","-Xms1024m","-jar","/app.jar"]
EXPOSE 8082