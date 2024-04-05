# Dianping

JDK-21  
MYSQL+Redis+Pulsar+Docker

## MYSQL and redis

docker-compose.yml

```yml
version: '3'

services:
  mysql:
    image: mysql:latest
    container_name: mysql
    environment:
      MYSQL_ROOT_PASSWORD: "123456"
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - backend
  
  redis-stack:
    image: redis/redis-stack:latest
    container_name: redis-stack
    ports:
      - "6379:6379"
      - "8001:8001" # RedisInsight web interface
    volumes:
      - redis_data:/data
    networks:
      - backend
  
  
  pulsar:
    image: apachepulsar/pulsar:3.2.2
    container_name: pulsar
    networks:
      - pulsar-net
    ports:
      - "6650:6650"
      - "8080:8080"
    volumes:
      - pulsar_data:/pulsar/data
      - pulsar_conf:/pulsar/conf
    command: bin/pulsar standalone
  
  pulsar-manager:
    image: apachepulsar/pulsar-manager:v0.3.0
    container_name: pulsar-manager
    depends_on:
      - pulsar
    networks:
      - pulsar-net
    ports:
      - "9527:9527"
      - "7750:7750"
    environment:
      - SPRING_CONFIGURATION_FILE=/pulsar-manager/pulsar-manager/springs/application.properties

networks:
  pulsar-net:
    driver: bridge
  backend:
    driver: bridge

volumes:
  mysql_data:
    driver: local
  redis_data:
    driver: local
  pulsar_data:
    driver: local
  pulsar_conf:
    driver: local
```

```shell
mkdir mysql_data redis_data pulsar_data pulsar_conf

```