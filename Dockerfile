FROM adoptopenjdk/openjdk11:jre-11.0.2.9-alpine
RUN mkdir courier-tracking-system
ADD build/libs/shopping-service-all-0.0.1-SNAPSHOT.jar shopping-service/shopping-service-all-0.0.1-SNAPSHOT.jar
WORKDIR shopping-service
RUN "pwd"
RUN "ls"
EXPOSE 4567
ENTRYPOINT ["java","-jar", "shopping-service-all-0.0.1-SNAPSHOT.jar"]
