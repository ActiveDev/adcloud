# ACL Service
Provides CRUD functionality for the access control list of the system.

* Using GET, GET{id}, POST, PUT{id}, DELETE{id}
* Common Http Status codes
* Exception handling

## Run
mvn spring-boot:run

## Action Items
Still a work in progress with lots to do:
* Simplify API - example: add children accepts a list of objects, which implies the object is saved when only the ids are actually saved
* Add unit testing
* Shore up exception handling
* Create swagger definition
* Re-enable eureka and config server
* Look at moving the abstract classes into a common library
* Plug into gateway
* Clean up code
* Address TODOs

## JMeter Tests
There are JMeter tests that can be run to see how the service works with the various calls.

## Url
http://localhost:9001/acl/v1.0/<resource>

Reousrces:
* users
* groups
* roles
* permissions
