INSERT INTO enum_permission (name) VALUES('ADMIN_SHOW_ASSIGNED_PROJECTS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(7,    'ADMIN', 'ADMIN_SHOW_ASSIGNED_PROJECTS');

INSERT INTO permission_to_role (id, user_role, user_permission) VALUES(1007, 'PROJECT_ADMIN', 'ADMIN_SHOW_ASSIGNED_PROJECTS');
