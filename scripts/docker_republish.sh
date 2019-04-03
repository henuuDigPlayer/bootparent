#!/bin/sh

application_name=$1
port=$2
service_name="${application_name}-server"
#服务所在地址，即在注册中心中的展示地址
eureka_host=183.131.202.13
#eureka主节点地址
eureka_url=183.131.202.13
#eureka 从节点地址
eureka_slave_url=183.131.202.13
#eureka主节点端口
eureka_port=8000
#eureka从节点端口
eureka_slave_port=8001



echo "container is stoping and removing"

containerId=$(docker ps -a | grep -E "${service_name}" | awk '{print $1}')

if [ ! -z $containerId ]
  then docker stop $containerId && docker rm $containerId
fi

imageId=$(docker images | grep -E "${service_name}" | awk '{print $3}')
if [ ! -z $imageId ]
  then docker rmi $imageId
fi

echo "image and container ware removed and image is building"
cd ..

cd ${service_name}
mvn clean compile
mvn package -Dmaven.test.skip=true docker:build

echo "build success and container is starting"

docker run -p ${port}:${port} \
       --env SERVER_PORT=${port} \
       --env EUREKA_URL=${eureka_url} \
       --env EUREKA_HOST=${eureka_host} \
       --env EUREKA_PORT=${eureka_port} \
       --env EUREKA_SLAVE_PORT=${eureka_slave_port} \
       --env EUREKA_SLAVE_URL=${eureka_slave_url} \
       -v /data/servers/logs/${service_name}/:/data/servers/logs/${service_name} \
       -t ${service_name}:1.0-SNAPSHOT