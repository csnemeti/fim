CREATE TABLE project_component (
	id  bigserial PRIMARY KEY,
	component_name varchar(50) NOT NULL,
	project_id int4 NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4,
	background_color varchar(10),
	text_color varchar(10),

	CONSTRAINT project_component_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id)
);
CREATE UNIQUE INDEX project_component_unq ON project_component (component_name, project_id);

CREATE TABLE project_label (
	id  bigserial PRIMARY KEY,
	label varchar(50) NOT NULL,
	project_id int4 NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4,
	background_color varchar(10),
	text_color varchar(10),

	CONSTRAINT project_label_project_id_fk FOREIGN KEY (project_id) REFERENCES project (id)
);
CREATE UNIQUE INDEX project_label_unq ON project_label (label, project_id);