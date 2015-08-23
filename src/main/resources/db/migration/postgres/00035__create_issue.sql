CREATE SEQUENCE issue_code_seq;

CREATE TABLE issue (
	id bigserial PRIMARY KEY,
	parent_id bigint,
	issue_code integer NOT NULL,
	project_id integer NOT NULL,
	reported_by integer NOT NULL,
	assigned_to integer,
	title varchar(250) NOT NULL,
	description varchar(4000),
	environment varchar(1000),
	issue_type varchar(20) NOT NULL,
	state_id bigint NOT NULL,
	resolution varchar(20),
	priority_id bigint NOT NULL,
	
	version integer NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
  	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	
	CONSTRAINT issue_prj_code_unq UNIQUE (project_id, issue_code),
	
	CONSTRAINT issue_parent_id_fk 	FOREIGN KEY (parent_id) REFERENCES issue (id),
	CONSTRAINT issue_project_id_fk 	FOREIGN KEY (project_id) REFERENCES project (id),
	CONSTRAINT issue_reported_by_fk FOREIGN KEY (reported_by) REFERENCES fim_user (id),
	CONSTRAINT issue_assigned_to_fk FOREIGN KEY (assigned_to) REFERENCES fim_user (id),
	CONSTRAINT issue_issue_type_fk 	FOREIGN KEY (issue_type) REFERENCES enum_issue_type (name),
	CONSTRAINT issue_state_id_fk 	FOREIGN KEY (state_id) REFERENCES issue_state (id),
	CONSTRAINT issue_resolution_fk 	FOREIGN KEY (resolution) REFERENCES enum_Issue_resolution (name),
	CONSTRAINT issue_priority_id_fk FOREIGN KEY (priority_id) REFERENCES issue_priority (id)
);

CREATE INDEX issue_title_idx ON issue (title);

CREATE OR REPLACE FUNCTION IssueCodeAutoincrement()
RETURNS "trigger" AS
$BODY$
 	BEGIN
	 	-- Select * from Project where id = New.project_id;
   		New.issue_code:=nextval('issue_code_seq');
   		Return NEW;
 	END;
$BODY$
LANGUAGE 'plpgsql' VOLATILE;
 
CREATE TRIGGER IssueCodeAutoincrementTrg BEFORE INSERT ON issue FOR EACH ROW EXECUTE PROCEDURE IssueCodeAutoincrement();