FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD partybuilding-service-1.0.RELEASE.jar app.jar
ENTRYPOINT ["sh","-c","java $PARAMS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]