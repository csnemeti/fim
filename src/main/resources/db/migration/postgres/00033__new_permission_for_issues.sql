INSERT INTO enum_permission (name) VALUES('ISSUE_CRAETE_ISSUE');
INSERT INTO enum_permission (name) VALUES('ISSUE_SHOW_ISSUE');
INSERT INTO enum_permission (name) VALUES('ISSUE_EDIT_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(9,    'ADMIN', 'ISSUE_CRAETE_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(10,   'ADMIN', 'ISSUE_SHOW_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(11,   'ADMIN', 'ISSUE_EDIT_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1009,   'PROJECT_ADMIN', 'ISSUE_CRAETE_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1010,   'PROJECT_ADMIN', 'ISSUE_SHOW_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1011,   'PROJECT_ADMIN', 'ISSUE_EDIT_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(2010,   'STATISTICAL', 'ISSUE_SHOW_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3009,   'TEAM', 'ISSUE_CRAETE_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3010,   'TEAM', 'ISSUE_SHOW_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(3011,   'TEAM', 'ISSUE_EDIT_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4009,   'SCRUM_MASTER', 'ISSUE_CRAETE_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4010,   'SCRUM_MASTER', 'ISSUE_SHOW_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(4011,   'SCRUM_MASTER', 'ISSUE_EDIT_ISSUE');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5009,   'PRODUCT_OWNER', 'ISSUE_CRAETE_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5010,   'PRODUCT_OWNER', 'ISSUE_SHOW_ISSUE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(5011,   'PRODUCT_OWNER', 'ISSUE_EDIT_ISSUE');
