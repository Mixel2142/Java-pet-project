FROM adoptopenjdk/openjdk13:alpine-jre
MAINTAINER Efremov Michael <efremov20081@gmail.com>
EXPOSE 8080
WORKDIR /opt/server/
VOLUME /opt/server/logs/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
CMD java -Dspring.profiles.active=dev -jar app.jar
