FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/api-gateway-0.0.1.jar
COPY ${JAR_FILE} app_apigateway.jar
ENTRYPOINT ["java", "-jar", "apigateway.jar"]