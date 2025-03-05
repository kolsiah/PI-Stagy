FROM openjdk:17
EXPOSE 8084
ADD target/API-GATEWAY-0.0.1-SNAPSHOT.jar apigateway.jar
ENTRYPOINT ["java", "-jar","apigateway.jar"]