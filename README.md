# Hrinkov social network

#### [Hrinkov Sergey](https://www.linkedin.com/in/sergey-hrynkou-1646aa19a/)

This repository includes a basic REST API built with Spring framework and for demonstration purposes.

### Installation

#### Java
Install java 8 use following command in terminal
```sh
    $ sudo apt update
$ sudo apt-get install openjdk-8-jdk
```
You will be prompted to accept the license.
Verify the installation by running the following command which will print the version:

```sh
$ java -version
```
```sh
openjdk version "1.8.0_275"
OpenJDK Runtime Environment (build 1.8.0_275-8u275-b01-0ubuntu1~18.04-b01)
OpenJDK 64-Bit Server VM (build 25.275-b01, mixed mode)
```

#### Maven
Install maven
```sh
$ sudo apt update
$ sudo apt install maven
```
Verify the installation by running the following command which will print the version:
```sh
$ mvn -version
```
```sh
Apache Maven 3.6.0
Maven home: /usr/share/maven
Java version: 1.8.0_275, vendor: Private Build, runtime: /usr/lib/jvm/java-8-openjdk-amd64/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-53-generic", arch: "amd64", family: "unix"
```
#### Docker
Update the apt package index and install packages to allow apt to use a repository over HTTPS:
```sh
$ sudo apt-get update
$ sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
```
Add Docker’s official GPG key:
```sh
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```
Verify that you now have the key with the fingerprint 9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88, by searching for the last 8 characters of the fingerprint.
```sh
$ sudo apt-key fingerprint 0EBFCD88
```
```sh
pub   rsa4096 2017-02-22 [SCEA]
      9DC8 5822 9FC7 DD38 854A  E2D8 8D81 803C 0EBF CD88
uid           [ unknown] Docker Release (CE deb) <docker@docker.com>
sub   rsa4096 2017-02-22 [S]
```
Use the following command to set up the stable repository.
```sh
$ sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
```
Update the apt package index, and install the latest version of Docker Engine and containerd, or go to the next step to install a specific version:
```sh
$ sudo apt-get update
$ sudo apt-get install docker-ce docker-ce-cli containerd.io
```

Verify that Docker Engine is installed correctly by running the hello-world image.
```sh
$ sudo docker run hello-world
```
This command downloads a test image and runs it in a container. When the container runs, it prints an informational message and exits.

#### Docker compose
Start by updating the software repositories and software packages.
```sh
$ sudo apt-get update
```
Install docker compose from the official Ubuntu repository using the following command
```sh
$ sudo apt-get install docker-compose
``` 
To test for a successful installation, check the version using:
```sh
$ docker-compose -version
``` 
```sh
docker-compose version 1.17.1
```
### Configuration
Go to the folder with the project.
Open prod.yml (in any text editor):
```sh
services:
  web:
    build:
      context: .
      dockerfile: dockerfile/ubuntu/Dockerfile
    restart: on-failure
    depends_on:
      - db
    ports:
      - 2308:8080
    volumes:
      - ./logs:/usr/local/tomcat/logs
  db:
    image: postgres:13.1
    environment:
      - POSTGRES_DB=test_database
      - POSTGRES_USER=TestUser
      - POSTGRES_PASSWORD=TestPassword
    ports:
      - 5432:5432
    volumes:
      - ./database/postgres:/var/lib/postgresql/data
    cap_add:
      - SYS_NICE
```
Change the following fields:
```sh
" - POSTGRES_USER= " - POSTGRESSQL username;
" - POSTGRES_PASSWORD= " - POSTGRESSQL password;
```
Save file.
Open controller/src/main/resources/hibernate.properties (in any text editor).
```sh
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://db:5432/test_database
spring.datasource.driver_class=org.postgresql.Driver
spring.datasource.username=TestUser
spring.datasource.password=TestPassword
spring.liquibase.change-log=classpath:db/changelog/changeLog-master.xml
spring.banner.location=classpath:banner.txt
com.senla.socialnetwork.service.WeatherConditionServiceImpl.weatherKey=b68b4778fdca71f0acfc8b78bb3bb162
com.senla.socialnetwork.service.WeatherConditionServiceImpl.updateTime=1800
com.senla.socialnetwork.service.UserServiceImpl.expiration=20000000
```
Change the following fields:
```sh
"spring.datasource.username= " - POSTGRESSQL username;
"spring.datasource.password= " - POSTGRESSQL password;
```
Save file.

Open app/src/main/resources/db/changelog/v-1.0/dml_changeLog.sql (in any text editor).
```sh
ALTER TABLE users AUTO_INCREMENT = 1;

INSERT INTO users VALUES
(NULL, 'admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');

COMMIT;
```
You need to come up with a password and get a hash function from it, for this enter the following command in the terminal:
```sh
$ ./password YourPassword
```
```sh
$2a$10$OToP0F8iLtqiiLmYlwMmnuVOQPGFs772u73jQRcJhChL1rjPZx2kq
```
Сhange email and hash function from password of admin:
```sh
        "username"                   "hash function from password"
            |                                    |
(NULL, 'admin@test.com', '$2a$10$ehU.2fP2fFtcFB9Fti8u2unzBrCIzQRvUei8r/ppUzxlBxSP86eH2', 'ROLE_ADMIN');
```
Save file.
### Building the project and starting the Server
The project is built using the maven tool, the application is launched using the docker compose tool, 
for this enter the following command in the root directory of the project:
```sh
$ ./build.sh
```
Upon successful build you will see the following message:
```sh
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for hrinkov-social-network 1.0.0-SNAPSHOT:
[INFO] 
[INFO] hrinkov-social-network ............................. SUCCESS [  2.982 s]
[INFO] domain ............................................. SUCCESS [  5.830 s]
[INFO] dao ................................................ SUCCESS [  4.093 s]
[INFO] dto ................................................ SUCCESS [  1.841 s]
[INFO] service ............................................ SUCCESS [ 13.774 s]
[INFO] controller ......................................... SUCCESS [  6.563 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  35.387 s
[INFO] Finished at: 2020-11-30T15:18:25+03:00
[INFO] ------------------------------------------------------------------------
```
The following messages indicate a successful server start:
```sh
web_1                   | 01-Dec-2020 11:37:11.435 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-nio-8080"]
web_1                   | 01-Dec-2020 11:37:11.526 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 21059 ms
social-network-mysql_1  | 2020-12-01T11:37:11.586746Z 0 [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections. Version: '8.0.22'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.
```
To see the capabilities of a RESTful application, visit the following link:
http://localhost:2308/swagger-ui.html
