FROM openjdk:8-jdk-slim
ARG JAR_FILE=build/libs/clean-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
ENV PROJECT_ID=architect-poc
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Djava.security.edg=file:/dev/./urandom","-jar","/app.jar"]
