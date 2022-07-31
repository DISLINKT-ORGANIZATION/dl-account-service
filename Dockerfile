FROM adoptopenjdk:11-jre-hotspot
COPY "target/account-service.jar" account-service.jar
ENTRYPOINT ["java", "-jar", "account-service.jar"]