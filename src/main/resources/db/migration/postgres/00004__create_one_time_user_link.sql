CREATE TABLE enum_one_time_link_type (
	name varchar(30) PRIMARY KEY
);

INSERT INTO enum_one_time_link_type (name) VALUES('USER_REGISTRATION');
INSERT INTO enum_one_time_link_type (name) VALUES('USER_INVITE');
INSERT INTO enum_one_time_link_type (name) VALUES('FORGOT_PASWORD');

CREATE TABLE user_one_time_link (
	id bigserial PRIMARY KEY,
	uuid varchar(200) NOT NULL,
	designation varchar(30) NOT NULL,
	expires_at timestamp with time zone NOT NULL,
	user_id integer NOT NULL,
	
	version integer,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	
	CONSTRAINT user_one_time_link_uuid_unq UNIQUE (uuid),
	
	CONSTRAINT user_one_time_userid_fk FOREIGN KEY (user_id) REFERENCES fim_user (id),
	CONSTRAINT user_one_time_designation_fk FOREIGN KEY (designation) REFERENCES enum_one_time_link_type (name)	
);
CREATE TRIGGER update_user_one_time_link_modtime BEFORE UPDATE ON user_one_time_link FOR EACH ROW EXECUTE PROCEDURE update_modified_at_column();