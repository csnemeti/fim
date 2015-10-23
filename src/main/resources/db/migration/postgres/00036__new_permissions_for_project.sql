ALTER TABLE enum_permission ALTER COLUMN name TYPE varchar(45);
ALTER TABLE permission_to_role ALTER COLUMN user_permission TYPE varchar(45);

INSERT INTO enum_permission (name) VALUES('PROJECT_EDIT_PROJECT_ASSIGN_PA');
INSERT INTO enum_permission (name) VALUES('PROJECT_EDIT_PROJECT_ASSIGN_PO');
INSERT INTO enum_permission (name) VALUES('PROJECT_EDIT_PROJECT_ASSIGN_SM');
INSERT INTO enum_permission (name) VALUES('PROJECT_EDIT_PROJECT_CHANGE_OWNER');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(12,    'ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_PA');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(13,    'ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_PO');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(14,    'ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_SM');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(15,    'ADMIN', 'PROJECT_EDIT_PROJECT_CHANGE_OWNER');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1012,    'PROJECT_ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_PA');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1013,    'PROJECT_ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_PO');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1014,    'PROJECT_ADMIN', 'PROJECT_EDIT_PROJECT_ASSIGN_SM');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1015,    'PROJECT_ADMIN', 'PROJECT_EDIT_PROJECT_CHANGE_OWNER');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4014, 'SCRUM_MASTER', 'PROJECT_EDIT_PROJECT_ASSIGN_SM');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5013, 'PRODUCT_OWNER', 'PROJECT_EDIT_PROJECT_ASSIGN_PO');
