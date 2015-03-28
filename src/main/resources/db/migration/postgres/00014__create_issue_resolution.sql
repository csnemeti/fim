CREATE TABLE enum_issue_resolution (
	name varchar(20) PRIMARY KEY
);

INSERT INTO enum_issue_resolution (name) VALUES('FIXED');
INSERT INTO enum_issue_resolution (name) VALUES('WONT_FIX');
INSERT INTO enum_issue_resolution (name) VALUES('CANNOT_REPRODUCE');
INSERT INTO enum_issue_resolution (name) VALUES('DUPLICATE');
