CREATE TABLE fim_user (
	id serial PRIMARY KEY,
	first_name varchar(100),
	last_name varchar(100),
	email varchar(200) NOT NULL,
	login varchar(200) NOT NULL,
	password varchar(250) NOT NULL,
	status varchar(20) NOT NULL,
	
	version integer,
	created_at timestamp with time zone,
	modified_at timestamp with time zone,
	
	CONSTRAINT fim_user_login_unq UNIQUE (login),
	CONSTRAINT fim_user_email_unq UNIQUE (email),
	
	CONSTRAINT fim_user_status_fk FOREIGN KEY (status) REFERENCES enum_user_status (name)
);

CREATE TABLE user_login (
	id bigserial PRIMARY KEY,
	created_at timestamp with time zone NOT NULL,
	user_id integer NOT NULL,
	
	version integer,
	
	CONSTRAINT user_login_user_id_fk FOREIGN KEY (user_id) REFERENCES fim_user (id)
);
