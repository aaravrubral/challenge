# docker build -t blk-hacking-ind-name-lastname .
FROM eclipse-temurin:17-jdk-alpine
# Alpine chosen for minimal footprint and fast startup

WORKDIR /app
COPY target/*.jar app.jar

EXPOSE 5477
ENTRYPOINT ["java","-jar","app.jar"]