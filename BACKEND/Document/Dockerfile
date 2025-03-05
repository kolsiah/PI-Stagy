FROM openjdk:17
EXPOSE 8087
ADD target/Document-0.0.1-SNAPSHOT.jar document.jar
ENTRYPOINT ["java", "-jar","document.jar"]