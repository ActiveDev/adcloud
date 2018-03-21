# adcloud
Contains projects to get up and running using Spring Cloud

This is still a work in progress but the projects should help you get an understanding of how the various components fit together. The projects should be started in the following order:

## Monitor
Provides the Eureka Discovery service and the Hystrix Dashboard

## Config
Provides a centralized configuration for all of the components using the Spring Cloud Config Server.

## TemplateService
This is just a sample microservice to show how it interacts with the gateway

## Gateway
Uses Zuul to proxy requests through and also to provide access. The plumbing is being put in place for security and RBAC and should be coming shortly.

### Build Order
There was a conscious decision to not use maven parent projects as it hides an understanding of just how all of these projects fit together. As such, the build must be done in the correct order which is:

1) rbac-autoconfigure
2) rbac-starter
3) gateway

## Mock IDM
When using the security features in the gateway, this mock identity management server can be used to generate JWTs. This is just a placeholder for demonstrations.

