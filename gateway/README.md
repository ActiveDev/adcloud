# API Gateway
This application uses Zuul to proxy requests which provides a central place to apply request and response filters. This is useful for enforcing security and other policies such as logging and auditing.

## Features
### Discovery
Uses Eureka to discover the available services. The services are found using the serviceId that is registered with Eureka (the spring application name).

### Load Balancing
Uses Ribbon to load balance between multiple services

### Security
Uses Spring Security and Zuul filters to apply RBAC to the api endpoints. More info to come.

## Run
1) Ensure the monitor application is running
2) mvn spring-boot:run

## Example
When using the gateway, the url is constructed as:
<protocol>://<hostname>:<port>/gateway/<service-id>/remaining url of the service being called

### Add
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X POST -d 'param1=aaa&param2=bbb&param3=ccc' http://localhost:8080/gateway/template-service/template/v1.0/items

### Get (list)
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X GET http://localhost:8080/gateway/template-service/template/v1.0/items

### Get Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X GET http://localhost:8080/gateway/template-service/template/v1.0/items/{use_an_id_from_the_list_call}

### Partial Update Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X PATCH -d 'param3=ddd' http://localhost:8080/gateway/template-service/template/v1.0/items/{use_an_id_from_the_list_call}

### Delete Item
curl -v -H "Content-Type:application/x-www-form-urlencoded" -X DELETE http://localhost:8080/gateway/template-service/template/v1.0/items/{use_an_id_from_the_list_call}
