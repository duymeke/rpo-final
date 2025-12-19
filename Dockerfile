FROM eclipse-temurin:21-jdk
MAINTAINER duymeke
COPY build/libs/final-0.0.1-SNAPSHOT.jar task.jar
ENTRYPOINT ["java", "-jar", "task.jar"]