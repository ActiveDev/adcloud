CREATE SCHEMA IF NOT EXISTS acl;

use acl;

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
