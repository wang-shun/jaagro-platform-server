#基础镜像也可以用 java:8
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD platform-gateway-2.0.0-SNAPSHOT.jar app.jar
RUN echo "Asia/shanghai" > /etc/timezone
ENTRYPOINT ["java","-Xmx1024m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "-Dspring.cloud.inetutils.preferred-networks: 172.16.31."]
EXPOSE 9030