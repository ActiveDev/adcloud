# ACL Service
Provides CRUD functionality for the access control list of the system.

* Using GET, GET{id}, POST, PUT{id}, DELETE{id}
* Common Http Status codes
* Exception handling

## Run
* Ensure MySql Server is running.
* Run the mysql-schema.sql script against the db.
* mvn spring-boot:run

## Action Items
Still a work in progress with lots to do:
* Simplify API - example: add children accepts a list of objects, which implies the object is saved when only the ids are actually saved
* Add unit testing
* Shore up exception handling
* Create swagger definition
* Look at moving the abstract classes into a common library
* Clean up code
* Address TODOs

## JMeter Tests
aclservice_tests.jmx will call all of the available endpoints. These test cases will call the acl service directly and bypass the gateway. 

## Url
http://localhost:9001/acl/v1.0/<resource>

Reousrces:
* users
* groups
* roles
* permissions

## Docker
* mvn install dockerfile:build
* docker run -d -p 9001:9001 activedev/adcloud_aclservice

### Database
docker run -e MYSQL_ROOT_PASSWORD=secret -d -p 3306:3306 mysql:5.7.13

If using Virtualbox, make sure port forwarding is enabled for 3306
