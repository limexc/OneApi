FROM openjdk:19
MAINTAINER Limexc
EXPOSE 8082
ARG JAR_FILE=*.jar

ADD target/${JAR_FILE} /one_api.jar

ENTRYPOINT ["java", "-jar","/one_api.jar"]
