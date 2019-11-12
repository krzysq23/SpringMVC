FROM java:8-jdk-alpine
EXPOSE 8081
ADD /target/springmvc-0.0.1-SNAPSHOT.jar springmvc-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","springmvc-0.0.1-SNAPSHOT.jar"]