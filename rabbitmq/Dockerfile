# Docker image for springboot application
# VERSION 0.0.1
# Author: bolingcavalry

### 基础镜像
#FROM centos:7
#
#RUN yum install -y java-1.8.0-openjdk java-1.8.0-openjdk-devel vorbis-tools
#
#COPY target/service2-1.0-SNAPSHOT.jar /service2-1.0.jar
#
#EXPOSE 8080
#
#CMD ["/bin/bash","-c","java -jar /service2-1.0.jar"]

## 基础镜像，使用alpine操作系统，openjkd使用8u201
FROM openjdk:8u201-jdk-alpine3.9

#作者
MAINTAINER mxd

#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#应用构建成功后的jar文件被复制到镜像内，名字也改成了app.jar
ADD target/rabbitmq-1.0-SNAPSHOT.jar rabbitmq.jar

#启动容器时的进程
ENTRYPOINT ["java","-jar","/rabbitmq.jar"]

#暴露8080端口
EXPOSE 8082