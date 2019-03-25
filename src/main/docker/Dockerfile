FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD partybuilding-service-1.0.RELEASE.jar partybuilding-service.jar
EXPOSE 9500
ENTRYPOINT ["sh","-c","java $PARAMS -Djava.security.egd=file:/dev/./urandom -jar /partybuilding-service.jar"]