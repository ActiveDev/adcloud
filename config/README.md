# Config Server
This application keeps a repository of configuration files and exposes them through REST calls. Used in conjunction with Spring applications that enable cloud config, it will centralize the configuration of the entire system.

## Repository
The current repository is located in the repo folder. This folder can be located anywhere on the classpath. The files within it must follow the naming convention of:
<spring.application.name>-<profile>.properties

## Future Considerations
To use a git repository which will allow for proper tracking of changes to the configuration files 

## Docker
* mvn install dockerfile:build
* docker run -d -p 8888:8888 activedev/adcloud_config