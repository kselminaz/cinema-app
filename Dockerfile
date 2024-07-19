FROM openjdk:21-jdk-slim
COPY build/libs/cinema-app-0.0.1-SNAPSHOT.jar  /cinema-app/
ENTRYPOINT ["java", "-jar", "/cinema-app/cinema-app-0.0.1-SNAPSHOT.jar"]