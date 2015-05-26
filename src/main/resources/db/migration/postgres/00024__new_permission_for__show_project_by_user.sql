INSERT INTO enum_permission (name) VALUES('PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(8,    'ADMIN', 			'PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1008, 'PROJECT_ADMIN', 	'PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(2008, 'STATISTICAL', 	'PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3008, 'TEAM', 			'PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4008, 'SCRUM_MASTER', 	'PROJECT_SHOW_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5008, 'PRODUCT_OWNER', 	'PROJECT_SHOW_PROJECT');
