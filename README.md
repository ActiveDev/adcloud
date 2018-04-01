# adcloud
Contains projects to get up and running using Spring Cloud

* NOTE: This branch is not stable. There is a lot of restructuring happening. If you are looking for something stable, please refer to the master branch.

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

## adcloud_Docker
General project that provides information on getting up and running using docker. This includes several docker-compose yml files that will setup the entire cloud. It's a great way to see just how everything fits together.

