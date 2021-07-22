#!/bin/sh
host_name=18.217.38.215

mvn clean install

echo 'Create folders...'

ssh -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem ubuntu@$host_name <<EOF

mkdir -p service

cd service || exit

mkdir -p jar

mkdir -p dockerfile

EOF

echo 'Copy files...'

scp -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem \
    app/target/socialnetwork-spring-boot.jar \
    ubuntu@$host_name:/home/ubuntu/service/jar/

scp -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem \
    dockerfile/ubuntu/Dockerfile \
    ubuntu@$host_name:/home/ubuntu/service/dockerfile

scp -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem \
    prod.yml \
    ubuntu@$host_name:/home/ubuntu/service/

scp -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem \
    dependencies.sh \
    ubuntu@$host_name:/home/ubuntu/service/

echo 'Install dependencies...'

ssh -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem ubuntu@$host_name <<EOF

chmod +x service/dependencies.sh

service/dependencies.sh

sudo chmod 666 /var/run/docker.sock

sudo service docker start

rm service/dependencies.sh

EOF

echo 'Start server....'

ssh -i /Users/siarhei.hrynkou/Projects/amazone/siarhei-key-Ohio.pem ubuntu@$host_name <<EOF

cd service

docker-compose -f prod.yml up --build -d

EOF
