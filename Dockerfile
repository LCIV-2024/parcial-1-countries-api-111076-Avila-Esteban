FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} lciii-scaffolding-0.0.1-SNAPSHOT.jar.jar
ENTRYPOINT ["java","-jar","/lciii-scaffolding-0.0.1-SNAPSHOT.jar"]
