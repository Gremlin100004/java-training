#!/bin/bash

mvn clean install;
docker-compose -f dev.yml up