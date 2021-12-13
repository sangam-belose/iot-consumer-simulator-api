FROM openjdk:11-jdk-slim

VOLUME /tmp
#ARG JAR_FILE
COPY target/liveplan.jar app.jar
ENTRYPOINT ["java","-Xms1024m","-jar","/app.jar"]
EXPOSE 8080