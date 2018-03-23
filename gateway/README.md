# API Gateway
This application uses Zuul to proxy requests which provides a central place to apply request and response filters. This is useful for enforcing security and other policies such as logging and auditing.

## Features
### Discovery
Uses Eureka to discover the available services. The services are found using the serviceId that is registered with Eureka (the spring application name).

### Load Balancing
Uses Ribbon to load balance between multiple services

### Security
Uses Spring Security and Zuul filters to apply RBAC to the api endpoints. More info to come.

This should be taken into consideration when using JWTs.
https://www.owasp.org/index.php/JSON_Web_Token_(JWT)_Cheat_Sheet_for_Java

#### Using Security
To use security, modify the gateway-default.properties and set security.enabled to true. The gateway will now enforce a valid JWT token before allowing for a request to go through.

## Run
1) Ensure the monitor application is running
2) mvn spring-boot:run

## JMeter Tests
Added JMeter tests that call the template service through the gateway.

## Url
localhost:8080/gateway/template-service/template/v1.0/items
