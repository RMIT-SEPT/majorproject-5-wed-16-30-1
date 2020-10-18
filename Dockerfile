FROM openjdk:8-jdk-alpine
ARG JAR_FILE=BackEnd/target/backend-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar
COPY database.sh database.sh
COPY run.sh run.sh

ADD hsqldb hsqldb

ENTRYPOINT ["./run.sh"]
