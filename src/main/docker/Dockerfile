FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD partybuilding-service-1.0.RELEASE.jar partybuilding-service.jar
EXPOSE 9500
ENTRYPOINT ["java","-jar","/partybuilding-service.jar"]