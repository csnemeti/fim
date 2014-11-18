CREATE TABLE enum_user_role_inside_project (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_user_role_inside_project (name) VALUES('SCRUM_MASTER');
INSERT INTO enum_user_role_inside_project (name) VALUES('PRODUCT_OWNER');
INSERT INTO enum_user_role_inside_project (name) VALUES('TEAM');
INSERT INTO enum_user_role_inside_project (name) VALUES('STATISTICAL');

CREATE TABLE project (
	id serial PRIMARY KEY,
	name varchar(50) not null,
	code varchar(20) not null,
	description varchar(2000),

	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);
CREATE TRIGGER update_project_modtime BEFORE UPDATE ON project FOR EACH ROW EXECUTE PROCEDURE update_modified_at_column();
CREATE INDEX project_name_idx ON project (name);
CREATE UNIQUE INDEX project_code_idx ON project (code);

create table user_project_relation (
	id  bigserial PRIMARY KEY,
	user_id int4 not null,
	project_id int4 not null,
	user_role varchar(20) not null,

	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4,

	CONSTRAINT user_project_relation_userid_fk FOREIGN KEY (user_id) REFERENCES fim_user (id),
	CONSTRAINT user_project_relation_projectid_fk FOREIGN KEY (project_id) REFERENCES project (id),
	CONSTRAINT user_project_relation_userrole_fk FOREIGN KEY (user_role) REFERENCES enum_user_role_inside_project (name),
	CONSTRAINT user_project_relation_unq UNIQUE (user_id, project_id)
);
CREATE TRIGGER update_user_project_relation_modtime BEFORE UPDATE ON user_project_relation FOR EACH ROW EXECUTE PROCEDURE update_modified_at_column();
CREATE INDEX user_project_relation_userid_idx ON user_project_relation (user_id);
CREATE INDEX user_project_relation_projectid_idx ON user_project_relation (project_id);
