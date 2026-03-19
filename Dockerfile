FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
 # Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]