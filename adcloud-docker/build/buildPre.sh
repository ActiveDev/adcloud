#!/bin/sh

docker stop $(docker ps -aq)
docker rm gateway
docker rm aclservice
docker rm mockidm
docker rm monitor
docker rm config
docker rmi -f $(docker images | grep adcloud_ | tr -s ' ' | cut -d ' ' -f 3)