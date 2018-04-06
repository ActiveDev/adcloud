# adcloud
Contains projects to get up and running using Spring Cloud

## 0.3-preview Release Notes
* In-progress

Full documentation can be found at https://activedevsolutions.com

AD Cloud aims to provide a series of projects that will provide common configurations and functions simplifying implementation. Each project uses SpringBoot that can be run as a stand-alone jar or through a docker container.

IMPORTANT: ADCloud is still in the development phase. The code is currently for preview versions to be used for demonstration purposes.

## Monitor
This project enables a eureka server for discovery and a hystrix dashboard for circuit breaker metrics. It should be the first project run and is part of the base docker compose yml file.

### Running
From source: mvn spring-boot:run
From jar: java -jar monitor-<version>.jar
Once the service is running, the Eureka and Hystrix dashboards are available for viewing.

### Eureka Dashboard
http://localhost:8761/

### Hystrix Dashboard
http://localhost:8761/hystrix

To monitor the streams, the gateway needs to be started as it exposes the hystrix stream to monitor. This will become more clear as we explain the other projects. For now, you can just make a note that this is available. From the Hystrix Dashboard, enter:
With Security: http://localhost:8080/gateway/actuator/hystrix.stream?access_token=<token-from-mockidm>
Without Security: http://localhost:8080/gateway/actuator/hystrix.stream

## Config
This application keeps a repository of configuration files and exposes them through REST calls. Used in conjunction with Spring applications that enable cloud config, it will centralize the configuration of the entire system.

### Repository
The current repository is located in the repo folder. This folder can be located anywhere on the classpath unless the native search locations property is changed. The files within it must follow the naming convention of:
<spring.application.name>-<profile>.properties

### Future Considerations
To use a git repository which will allow for proper tracking of changes to the configuration files

## Configuration
spring.cloud.config.server.native.search-locations - location of the repository
eureka.client.serviceUrl.defaultZone - location of the discovery server

### Running
From source: mvn spring-boot:run
From jar: java -jar monitor-<version>.jar
Once the service is running, check the Eureka dashboard to ensure the config server has registered. The config server is now available for config clients to access the properties. The properties can also be accessed manually:
http://localhost:8888/<app-name>/<profile>/master
Example:
http://localhost:8888/gateway/default/master

## ADService
Is a multi-module project that contains:
adservice-core - used for common code used in all microservices such as abstractcontroller, abstractdao, etc.
adservice-springboot-autoconfigure - used to configure discovery and security.
adservice-springboot-starter - used to tie all of the dependencies together.

### Build
This project must be built before being able to build the remaining projects. From the project root folder, run:
mvn clean install

### Configuration
Whenever an application uses adservice, it will have the following configuration options available to it:
adservice.security.enabled - true or false indicating that the application enforces a valid JWT based on the key-value
security.oauth2.resource.jwt.key-value - is the JWT key value
adservice.rbac.authorization.method- none/filter/granted (currently, only none and filter are supported)
adservice.rbac.authorization.url - this is temporary until issues are resolved with using a discovery aware rest template

## ACLService
Provides CRUD functionality for the access control list of the system using GET, GET{id}, POST, PUT, DELETE{id}

Resources:
users
groups
roles
permissions

### Configuration
bootstrap.properties contains information for config server and discovery server
application.properties can be found in the config repository. It contains database connection information.

### Running
Ensure MySql Server is running.
Run the mysql-schema.sql script against the db.
From source: mvn spring-boot:run
From jar: java -jar aclservice-<version>.jar
Accessible from: http://localhost:9001/acl/v1.0/<resource>

### JMeter Tests
aclservice_tests.jmx will call all of the available endpoints. These test cases will call the acl service directly and bypass the gateway.

## Gateway
This application uses Zuul to proxy requests which provides a central place to apply request and response filters. This is useful for enforcing security and other policies such as logging and auditing.

### Features
1. Routing
Uses Eureka to discover the available services. The services are found using the service-id that is registered with Eureka (the spring application name).

2. Load Balancing
Uses Ribbon to load balance between multiple services

3. Security
Uses ADService library to apply Security and RBAC to the api endpoints. It is assumed that an IDM already exists that will authenticate users and create a JWT. For demonstration purposes, a mock IDM is provided. Please see Mock IDM for details.

www.owasp.org/index.php/JSON_Web_Token_(JWT)_Cheat_Sheet_for_Java should be taken into consideration when using JWTs.

### Configuration
bootstrap.properties contains information for config server and discovery server
application.properties can be found in the config repository. Refer to the ADService Configuration for details.

### Running
Ensure the monitor application is running
From source: mvn spring-boot:run
From jar: java -jar gateway-<version>.jar
Available from: localhost:8080/gateway/<service-id>/<uri>
Example: localhost:8080/gateway/template-service/template/v1.0/items

### JMeter Tests
gateway_template_tests.jmx - will call the template service through the gateway. It assumes that security is turned off and the rbac authorization is set to none.
gateway_aclservice_withsecurity_tests.jmx - will call the acl service through the gateway. It assumes security is turned on and rbac authorization is set to filter. In addition, the mockidm needs to be running in order to do the login. The default user is root, but you can switch it to ben to see how it restricts the write calls.

## Mock IDM
This project mimics an identity management system in that it will authenticate a user against an embedded ldap server and create a JWT that can then be used by the gateway.

NOTE: This is just a mock project and should NOT be used in production. It does not adhere to any security standards whatsoever.

### Running
Ensure the monitor application is running
From source: mvn spring-boot:run
From jar: java -jar mockidm-<version>.jar

### Create a Token
POST
http://localhost:8090/mockauth/oauth/token?grant_type=password&username=ben&password=benspassword Basic Auth: mock/secret

Example with Curl that will create a JWT with a signing key of "123":
curl mock:secret@localhost:8080/mockauth/oauth/token -d grant_type=password -d username=ben -d password=benspassword
