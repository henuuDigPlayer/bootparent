#!/bin/sh

application_name=$1
port=$2
service_name="${application_name}-server"
eureka_url=127.0.0.1
eureka_port=8000

echo "container is stoping and removing"

containerId=$(docker ps -a | grep -E "${service_name}" | awk '{print $1}')

if [ ! -z $containerId ]
  then docker stop $containerId && docker rm $containerId
fi


echo "image and container ware removed and image is building"


echo "build success and container is starting"

docker run -p ${port}:${port} \
       --env SERVER_PORT=${port} \
       --env EUREKA_URL=${eureka_url} \
       --env EUREKA_PORT=${eureka_port} \
       -v /data/servers/logs/${service_name}/:/data/servers/logs/${service_name} \
       -t ${service_name}:1.0-SNAPSHOT