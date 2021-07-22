#!/bin/sh

yes|sudo apt-get update

yes|sudo apt-get install openjdk-11-jdk

yes|sudo apt-get update

yes|sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo apt-key fingerprint 0EBFCD88

sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

yes|sudo apt-get update
yes|sudo apt-get install docker-ce docker-ce-cli containerd.io

yes|sudo apt-get update

yes|sudo apt-get install docker-compose