INSERT INTO enum_permission (name) VALUES('PROJECT_EDIT_PROJECT');
INSERT INTO enum_permission (name) VALUES('PROJECT_SHOW_HIDDEN_PROJECTS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5,    'ADMIN', 'PROJECT_SHOW_HIDDEN_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(6,    'ADMIN', 'PROJECT_EDIT_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1004, 'PROJECT_ADMIN', 'PROJECT_SHOW_HIDDEN_PROJECTS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1005, 'PROJECT_ADMIN', 'PROJECT_EDIT_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4003, 'SCRUM_MASTER', 'PROJECT_EDIT_PROJECT');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5004, 'PRODUCT_OWNER', 'PROJECT_EDIT_PROJECT');
