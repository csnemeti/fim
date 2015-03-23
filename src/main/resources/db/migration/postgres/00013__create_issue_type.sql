CREATE TABLE enum_issue_type (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_issue_type (name) VALUES('SUB_TASK');
INSERT INTO enum_issue_type (name) VALUES('TASK');
INSERT INTO enum_issue_type (name) VALUES('ENHANCEMENT');
INSERT INTO enum_issue_type (name) VALUES('BUG');
INSERT INTO enum_issue_type (name) VALUES('SUB_STORY');
INSERT INTO enum_issue_type (name) VALUES('STORY');
INSERT INTO enum_issue_type (name) VALUES('FEATURE');
INSERT INTO enum_issue_type (name) VALUES('EPIC');
