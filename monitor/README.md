# Monitor
This application enables a eureka server and hystrix dashboard in order to provide discovery and metrics.

## Running
mvn spring-boot:run

## Eureka Dashboard
http://localhost:8761/

## Hystrix Dashboard
http://localhost:8761/hystrix

## Monitor Streams
To monitor the streams from the gateway:
Start the Gateway
From the Hystrix Dashboard, enter: http://localhost:8080/gateway/hystrix.stream

## Docker
* mvn install dockerfile:build
* docker run -d -p 8761:8761 activedev/adcloud_monitor