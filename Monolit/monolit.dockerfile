FROM adoptopenjdk/openjdk13:alpine-jre
MAINTAINER Efremov Michael <efremov20081@gmail.com>
EXPOSE 8090
WORKDIR /opt/server/
VOLUME /opt/server/logs/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD java -Dspring.profiles.active=prod -jar app.jar


