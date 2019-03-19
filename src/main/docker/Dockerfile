FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD partybuilding-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh","-c","java $PARAMS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]