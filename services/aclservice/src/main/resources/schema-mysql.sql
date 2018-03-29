CREATE DATABASE IF NOT EXISTS rbac;

use rbac;

drop table if exists sec_role_permission;
drop table if exists sec_group_role;
drop table if exists sec_user_group;
drop table if exists sec_group;
drop table if exists sec_user;
drop table if exists sec_role;
drop table if exists sec_permission;

CREATE TABLE IF NOT EXISTS sec_user
(
    id INT NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(255) NOT NULL,
 	first_name VARCHAR(255) NOT NULL,
 	last_name VARCHAR(255) NOT NULL,	
	PRIMARY KEY (id),
	UNIQUE UQ_USER_user_id(user_id)
);

CREATE TABLE IF NOT EXISTS sec_group
(
	id INT NOT NULL AUTO_INCREMENT,
 	name VARCHAR(255) NOT NULL,
	description VARCHAR(2000),
	PRIMARY KEY (id),
	UNIQUE UQ_GROUP_name(name)
);

CREATE TABLE IF NOT EXISTS sec_role
(
	id INT NOT NULL AUTO_INCREMENT,
 	name VARCHAR(255) NOT NULL,
	description VARCHAR(2000),
	PRIMARY KEY (id),
	UNIQUE UQ_ROLE_name(name)
);

CREATE TABLE IF NOT EXISTS sec_permission
(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	endpoint VARCHAR(2000) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE UQ_PERMISSION_name(name)
);

CREATE TABLE IF NOT EXISTS sec_role_permission
(
	role_id INT NOT NULL,
	permission_id INT NOT NULL,
	PRIMARY KEY (role_id, permission_id),
	KEY (role_id),
    KEY (permission_id)
);

ALTER TABLE sec_role_permission ADD CONSTRAINT FK_roleperm_role_id 
	FOREIGN KEY (role_id) REFERENCES sec_role (id);
ALTER TABLE sec_role_permission ADD CONSTRAINT FK_roleperm_permission_id 
	FOREIGN KEY (permission_id) REFERENCES sec_permission (id);


CREATE TABLE IF NOT EXISTS sec_group_role
(
    role_id INT NOT NULL,
    group_id INT NOT NULL,
	PRIMARY KEY (role_id, group_id),
	KEY (role_id),
	KEY (group_id)
);

ALTER TABLE sec_group_role ADD CONSTRAINT FK_rolegrp_role_id 
	FOREIGN KEY (role_id) REFERENCES sec_role (id);
ALTER TABLE sec_group_role ADD CONSTRAINT FK_rolegrp_group_id 
	FOREIGN KEY (group_id) REFERENCES sec_group (id);


CREATE TABLE IF NOT EXISTS sec_user_group
(
    user_id INT NOT NULL,
    group_id INT NOT NULL,
	PRIMARY KEY (user_id, group_id),
	KEY (user_id),
	KEY (group_id)
);

ALTER TABLE sec_user_group ADD CONSTRAINT FK_usergrp_user_id 
	FOREIGN KEY (user_id) REFERENCES sec_user (id);
ALTER TABLE sec_user_group ADD CONSTRAINT FK_usergrp_group_id 
	FOREIGN KEY (group_id) REFERENCES sec_group (id);
	
INSERT INTO sec_user (user_id, first_name, last_name) VALUES ('root', 'system', 'user');
INSERT INTO sec_user (user_id, first_name, last_name) VALUES ('ben', 'normal', 'user');
INSERT INTO sec_group (name, description) VALUES ('ADMIN', 'Administrator');
INSERT INTO sec_group (name, description) VALUES ('USER', 'Normal user');
INSERT INTO sec_role (name, description) VALUES ('READ-ONLY', 'Read only access to the ACL service');
INSERT INTO sec_role (name, description) VALUES ('READ-WRITE', 'Read write access to the ACL service');

-- Users
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Users', '^/acl-service/acl/v1.0/users.GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get User', '^/acl-service/acl/v1.0/users/([a-zA-Z0-9_-]*).GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add User', '^/acl-service/acl/v1.0/users.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Update User', '^/acl-service/acl/v1.0/users.PUT$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete User', '^/acl-service/acl/v1.0/users/([a-zA-Z0-9_-]*).DELETE$');

-- Groups
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Groups', '^/acl-service/acl/v1.0/groups.GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Group', '^/acl-service/acl/v1.0/groups/([a-zA-Z0-9_-]*).GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Group', '^/acl-service/acl/v1.0/groups.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Update Group', '^/acl-service/acl/v1.0/groups.PUT$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Group', '^/acl-service/acl/v1.0/groups/([a-zA-Z0-9_-]*).DELETE$');

-- Roles
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Roles', '^/acl-service/acl/v1.0/roles.GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Role', '^/acl-service/acl/v1.0/roles/([a-zA-Z0-9_-]*).GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Role', '^/acl-service/acl/v1.0/roles.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Update Role', '^/acl-service/acl/v1.0/roles.PUT$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Role', '^/acl-service/acl/v1.0/roles/([a-zA-Z0-9_-]*).DELETE$');

-- Permissions
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Permissions', '^/acl-service/acl/v1.0/permissions.GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Get Permission', '^/acl-service/acl/v1.0/permissions/([a-zA-Z0-9_-]*).GET$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Permission', '^/acl-service/acl/v1.0/permissions.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Update Permission', '^/acl-service/acl/v1.0/permissions.PUT$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Permission', '^/acl-service/acl/v1.0/permissions/([a-zA-Z0-9_-]*).DELETE$');

-- Others
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Groups to User', '^/acl-service/acl/v1.0/users/([a-zA-Z0-9_-]*)/groups.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Groups from User', '^/acl-service/acl/v1.0/users/([a-zA-Z0-9_-]*)/groups.DELETE$');

INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Roles to Group', '^/acl-service/acl/v1.0/groups/([a-zA-Z0-9_-]*)/roles.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Roles from Group', '^/acl-service/acl/v1.0/groups/([a-zA-Z0-9_-]*)/roles.DELETE$');

INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Add Permissions to Role', '^/acl-service/acl/v1.0/roles/([a-zA-Z0-9_-]*)/permissions.POST$');
INSERT INTO sec_permission (name, endpoint) VALUES ('ACL Delete Permissions from Role', '^/acl-service/acl/v1.0/roles/([a-zA-Z0-9_-]*)/permissions.DELETE$');

INSERT INTO sec_role_permission (role_id, permission_id) 
	select a.id, b.id 
	from   sec_role a, sec_permission b
	where  a.name = 'READ-ONLY'
	and    b.endpoint LIKE '%.GET$';
    
INSERT INTO sec_role_permission (role_id, permission_id) 
	select a.id, b.id 
	from   sec_role a, sec_permission b
	where  a.name = 'READ-WRITE';
    
INSERT INTO sec_group_role (role_id, group_id) VALUES ((SELECT id FROM sec_role WHERE name = 'READ-WRITE'), (SELECT id FROM sec_group WHERE name = 'ADMIN'));
INSERT INTO sec_group_role (role_id, group_id) VALUES ((SELECT id FROM sec_role WHERE name = 'READ-ONLY'), (SELECT id FROM sec_group WHERE name = 'USER'));
INSERT INTO sec_user_group (user_id, group_id) VALUES ((SELECT id FROM sec_user WHERE user_id = 'root'), (SELECT id FROM sec_group WHERE name = 'ADMIN'));
INSERT INTO sec_user_group (user_id, group_id) VALUES ((SELECT id FROM sec_user WHERE user_id = 'ben'), (SELECT id FROM sec_group WHERE name = 'USER'));
