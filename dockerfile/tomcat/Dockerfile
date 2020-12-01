FROM tomcat:8-jdk11-corretto
MAINTAINER hrinkovsergey@yandex.ru

COPY controller/target/socialnetwork.war /usr/local/tomcat/webapps/

COPY dockerfile/tomcat/tomcat-users.xml /usr/local/tomcat/conf/