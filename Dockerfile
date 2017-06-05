FROM maven:3.5.0-alpine
RUN mkdir /code
WORKDIR /code
COPY . /code
RUN mvn compile
EXPOSE 8081
CMD mvn spring-boot:run

#FROM openjdk:8u111-jdk-alpine
#MAINTAINER   906877230@qq.com
#COPY  target/xing-web-1.0-SNAPSHOT.jar   app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8081