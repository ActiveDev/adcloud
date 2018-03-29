## Docker
Each project has the ability to build a docker image:
* mvn install dockerfile:build

### Build Folder
Just contains some helper scripts until we get Jenkins up and running

### Host Network
#### Docker Compose
NOTE: Right now the timing is a bit off. Starting the base and waiting for the config server to complete its discovery registration will provide the best results. This is why we have separated out the base from the services. Once we create the proper waitfor script, this won't be necessary.

##### Base
This includes the database, monitor, and config servers.
Prerequisites: Setup a volume for the config server and copy the properties files to it. The default is /tmp/config-repo.

* docker-compose -f docker-compose-host-base.yml
or
* docker-compose -f docker-compose-bridge-base.yml

##### Services
This includes the gateway, mockidm, and aclservice.
Prerequisites:
Run the schema-mysql.sql file against the database

* docker-compose -f docker-compose-host-services.yml
or
* docker-compose -f docker-compose-host-services.yml

NOTE: For bridge mode, the datasource has to be updated to point to the ip address instead of localhost.
WORKAROUND: Right now, the gateway calls the aclservice directly instead of through the discovery service. This requires that the acl url be updated to point to the correct ip address for now.

#### Manual Host
* docker run -d --name monitor --net=host -p 8761:8761 activedev/adcloud_monitor:{tag}
* docker run -d --name config --net=host -p 8888:8888 activedev/adcloud_config:{tag}
* docker run -d --name gateway --net=host -p 8080:8080 activedev/adcloud_gateway:{tag}
* docker run --name acldb --net=host -e MYSQL_ROOT_PASSWORD=secret -d -p 3306:3306 mysql:5.7.13
* docker run -d --name aclservice --net=host -p 9001:9001 activedev/adcloud_aclservice:{tag}
* docker run -d -p 8090:8090 activedev/adcloud_mockidm:{tag}

### Manual Bridge
NOTE: Still a work in progress.

* docker network create adcloud
* docker run -d --name monitor --net=adcloud -p 8761:8761 activedev/adcloud_monitor:{tag}
* docker run -d --name config --net=adcloud -p 8888:8888 activedev/adcloud_config:{tag}
* docker run -d --name gateway --net=adcloud -p 8080:8080 activedev/adcloud_gateway:{tag}
* docker run -d --name acldb --net=adcloud -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret mysql:5.7.13
* docker run -d --name aclservice --net=adcloud -p 9001:9001 activedev/adcloud_aclservice:{tag}
* docker run -d -p --name mockidm --net=adcloud 8090:8090 activedev/adcloud_mockidm:{tag}
