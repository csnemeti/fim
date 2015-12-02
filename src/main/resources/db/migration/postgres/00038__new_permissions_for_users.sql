INSERT INTO enum_permission (name) VALUES('ADMIN_EDIT_OTHER_USER_PROFILE');
INSERT INTO enum_permission (name) VALUES('ADMIN_MODIFY_USER_STATUS');
INSERT INTO enum_permission (name) VALUES('ADMIN_RESET_USER_PASSWORD');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(17,    'ADMIN', 'ADMIN_EDIT_OTHER_USER_PROFILE');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(18,    'ADMIN', 'ADMIN_MODIFY_USER_STATUS');
INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(19,    'ADMIN', 'ADMIN_RESET_USER_PASSWORD');
