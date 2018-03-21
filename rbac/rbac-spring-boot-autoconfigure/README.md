# RBAC AutoConfigure
This project will handle the configuration of a role based system. It is meant to be included in a web application through the rbac-spring-boot-starter project.

## Features
Configures an OAuth2 Resource server which will enforce a valid JWT token. Depending on the rbac method specified, it will also validate the endpoint against the granted authorities within the token.

## Configuration
security.enabled=true/false - the default is false which means that the application will not enforce any security
security.oauth2.resource.jwt.key-value=123 - the JWT implementation currently uses a Signing Key to verify the token
rbac.authorization.method=filter - Can be none, filter, or granted-auth
