FROM openjdk
MAINTAINER hrinkovsergey@yandex.ru

RUN mkdir /usr/local/webapps

RUN ls

RUN pwd

COPY app/target/socialnetwork-spring-boot.jar /usr/local/webapps/

CMD java -jar /usr/local/webapps/socialnetwork-spring-boot.jar