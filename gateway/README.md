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
### gateway_template_tests.jmx 
Will call the template service through the gateway. It assumes that security is turned off and the rbac authorization is set to none.

### gateway_aclservice_withsecurity_tests.jmx
Will call the acl service through the gateway. It assumes security is turned on and rbac authorization is set to filter.
In addition, the mockidm needs to be running in order to do the login. The default user is root, but you can switch it to ben to see how it restricts the write calls.

## Url
localhost:8080/gateway/template-service/template/v1.0/items
