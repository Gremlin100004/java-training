#!/bin/bash

mvn clean install
docker-compose -f prod.yml up --build