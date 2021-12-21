FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD /target/organicos-0.0.1-SNAPSHOT.jar organicos.jar
EXPOSE 8080
RUN bash -c 'touch /organicos.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:dev/./urandom","-jar", "/organicos.jar"]
