CREATE TABLE enum_user_default_role (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_user_default_role (name) VALUES('SCRUM_MASTER');
INSERT INTO enum_user_default_role (name) VALUES('PRODUCT_OWNER');
INSERT INTO enum_user_default_role (name) VALUES('TEAM');
INSERT INTO enum_user_default_role (name) VALUES('STATISTICAL');
INSERT INTO enum_user_default_role (name) VALUES('PROJECT_ADMIN');
INSERT INTO enum_user_default_role (name) VALUES('ADMIN');

ALTER TABLE fim_user ADD COLUMN default_role varchar(20);
ALTER TABLE fim_user ADD CONSTRAINT user_default_role_fk FOREIGN KEY (default_role) REFERENCES enum_user_default_role (name);
UPDATE fim_user SET default_role = 'STATISTICAL';
ALTER TABLE fim_user ALTER COLUMN default_role SET NOT NULL;

CREATE INDEX user_default_role_idx ON fim_user (default_role);


CREATE TABLE enum_permission (
	name varchar(30) PRIMARY KEY
);

INSERT INTO enum_permission (name) VALUES('PROJECT_CREATE_PROJECT');
INSERT INTO enum_permission (name) VALUES('PROJECT_LIST_PROJECTS');
INSERT INTO enum_permission (name) VALUES('ADMIN_INVITE_USER');
INSERT INTO enum_permission (name) VALUES('ADMIN_SEARCH_USERS');


CREATE TABLE permission_to_role (
	id integer not null,
	user_permission varchar(30) not null,
	user_role varchar(20) not null,
	primary key (id)
);

ALTER TABLE  permission_to_role ADD CONSTRAINT permission_to_role_unq  unique (user_role,user_permission);
CREATE INDEX permission_to_role_role_idx ON permission_to_role (user_role);
CREATE INDEX permission_to_role_perm_idx ON permission_to_role (user_permission);


INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1,    'ADMIN', 'PROJECT_CREATE_PROJECT');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(2,    'ADMIN', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3,    'ADMIN', 'ADMIN_INVITE_USER');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4,    'ADMIN', 'ADMIN_SEARCH_USERS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1001, 'PROJECT_ADMIN', 'PROJECT_CREATE_PROJECT');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1002, 'PROJECT_ADMIN', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1003, 'PROJECT_ADMIN', 'ADMIN_SEARCH_USERS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(2001, 'STATISTICAL', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(2002, 'STATISTICAL', 'ADMIN_SEARCH_USERS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3001, 'TEAM', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3002, 'TEAM', 'ADMIN_SEARCH_USERS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4001, 'SCRUM_MASTER', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4002, 'SCRUM_MASTER', 'ADMIN_SEARCH_USERS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5001, 'PRODUCT_OWNER', 'PROJECT_CREATE_PROJECT');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5002, 'PRODUCT_OWNER', 'PROJECT_LIST_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5003, 'PRODUCT_OWNER', 'ADMIN_SEARCH_USERS');
