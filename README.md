# adcloud
Contains projects to get up and running using Spring Cloud

## 0.2-preview Release Notes
* Restructured some of the projects to reduce code duplication
* adservice starter project contains dependencies and configurations for all microservices.
* adservice has added a adservice. prefix to all of its properties
* This release does not contain much in the way of additional functionality. That should change with 0.3-preview

## Monitor
Provides the Eureka Discovery service and the Hystrix Dashboard

## Config
Provides a centralized configuration for all of the components using the Spring Cloud Config Server.

## Services/ADService
Is a multi-module project that contains:
* adservice-core - used for common code used in all microservices such as abstractcontroller, abstractdao, etc.
* adservice-springboot-autoconfigure - used to configure discovery and security.
* adservice-springboot-starter - used to tie all of the dependencies together.

## Services/Template Service
This is just a sample microservice to show how it interacts with the gateway.

## Services/ACL Service
This provides the access control list to the RBAC filter in the gateway. This service is required when security is enabled and the authorization method is set to filter.

## Gateway
Uses Zuul to proxy requests through and also to provide access. The plumbing is being put in place for security and RBAC and should be coming shortly.

## Mock IDM
When using the security features in the gateway, this mock identity management server can be used to generate JWTs. This is just a placeholder for demonstrations.

## adcloud_Docker
General project that provides information on getting up and running using docker. This includes several docker-compose yml files that will setup the entire cloud. It's a great way to see just how everything fits together.

