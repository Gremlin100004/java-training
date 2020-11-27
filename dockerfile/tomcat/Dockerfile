FROM tomcat:8-jdk11-corretto
MAINTAINER hrinkovsergey@yandex.ru

RUN mvn clean install

COPY controller/target/socialnetwork.war /usr/local/tomcat/webapps/

COPY dockerfile/dev/tomcat/tomcat-users.xml /usr/local/tomcat/conf/
