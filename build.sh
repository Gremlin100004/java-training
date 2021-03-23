#!/bin/bash

mvn clean install
sudo docker-compose -f prod.yml up --build