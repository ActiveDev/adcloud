# Template
Acts as a template for any microservice that needs to be created. It provides the following:

* Using GET, GET{id}, POST, PUT{id}, DELETE{id}
* Common Http Status codes
* Exception handling

## Run
mvn spring-boot:run

## Action Items
Just finished making it similar to the acl service. The common code needs to be moved into a library to be used by all microservices. Just like the acl service, still lots to do here.

## JMeter Tests
Added some jmeter tests to see how the service runs on its own (no gateway or config server).

## Url
localhost:9000/template/v1.0/items
