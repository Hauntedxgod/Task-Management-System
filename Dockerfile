FROM openjdk:19
COPY target/*.jar test-effective-mobile.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "test-effective-mobile.jar"]





