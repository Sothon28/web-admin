FROM openjdk:17-alpine3.14

ARG JAR_FILE=target/*.jar

ENV PORT 8282

#EXPOSE $PORT

COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
