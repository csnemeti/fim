CREATE TABLE enum_user_status (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_user_status (name) VALUES('NEW');
INSERT INTO enum_user_status (name) VALUES('ACTIVE');
INSERT INTO enum_user_status (name) VALUES('DISABLED');
