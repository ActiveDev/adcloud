# adcloud
Contains projects to get up and running using Spring Cloud

This is still a work in progress but the projects should help you get an understanding of how the various components fit together. The projects should be started in the following order:

## Monitor
Provides the Eureka Discovery service and the Hystrix Dashboard

## Config
Provides a centralized configuration for all of the components using the Spring Cloud Config Server.

## services/Template Service
This is just a sample microservice to show how it interacts with the gateway

## services/ACL Service
This provides the access control list to the RBAC filter in the gateway. This service is required when security is enabled and the authorization method is set to filter.

## Gateway
Uses Zuul to proxy requests through and also to provide access. The plumbing is being put in place for security and RBAC and should be coming shortly.

### Build Order
There was a conscious decision to not use maven parent projects as it hides an understanding of just how all of these projects fit together. As such, the build must be done in the correct order which is:

1) rbac-autoconfigure
2) rbac-starter
3) gateway

## Mock IDM
When using the security features in the gateway, this mock identity management server can be used to generate JWTs. This is just a placeholder for demonstrations.


## Docker
Each project has the ability to build a docker image:
* mvn install dockerfile:build

### Host Mode
* docker run -d --name monitor --net=host -p 8761:8761 activedev/adcloud_monitor:{tag}
* docker run -d --name config --net=host -p 8888:8888 activedev/adcloud_config:{tag}
* docker run -d --name gateway --net=host -p 8080:8080 activedev/adcloud_gateway:{tag}
* docker run --name acldb --net=host -e MYSQL_ROOT_PASSWORD=secret -d -p 3306:3306 mysql:5.7.13
* docker run -d --name aclservice --net=host -p 9001:9001 activedev/adcloud_aclservice:{tag}
* docker run -d -p 8090:8090 activedev/adcloud_mockidm:{tag}

### Bridge Mode
NOTE: Still a work in progress

* docker network create adcloud
* docker run -d --name monitor --net=adcloud -p 8761:8761 activedev/adcloud_monitor:{tag}
* docker run -d --name config --net=adcloud -p 8888:8888 activedev/adcloud_config:{tag}
* docker run -d --name gateway --net=adcloud -p 8080:8080 activedev/adcloud_gateway:{tag}
* docker run -d --name acldb --net=adcloud -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret mysql:5.7.13
* docker run -d --name aclservice --net=adcloud -p 9001:9001 activedev/adcloud_aclservice:{tag}
* docker run -d -p --name mockidm --net=adcloud 8090:8090 activedev/adcloud_mockidm:{tag}

