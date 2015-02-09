CREATE TABLE enum_project_state (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_project_state (name) VALUES('IN_PREPARATION');
INSERT INTO enum_project_state (name) VALUES('ACTIVE');
INSERT INTO enum_project_state (name) VALUES('CLOSED');
INSERT INTO enum_project_state (name) VALUES('SCHEDULED_FOR_DELETE');

ALTER TABLE project ADD project_state varchar(20) NOT NULL DEFAULT 'IN_PREPARATION';
ALTER TABLE project ADD state_changed_at timestamp with time zone NOT NULL DEFAULT now();
ALTER TABLE project ADD CONSTRAINT project_state_fk FOREIGN KEY (project_state) REFERENCES enum_project_state (name);
ALTER TABLE project ADD hidden bool NOT NULL DEFAULT false;


CREATE INDEX project_state_idx ON project (project_state);
CREATE INDEX project_hidden_idx ON project (hidden);
