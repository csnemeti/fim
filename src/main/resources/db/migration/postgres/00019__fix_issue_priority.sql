-- create unique and NOT NULL priority_name with value copied from id
ALTER TABLE issue_priority ADD COLUMN priority_name varchar(50);
CREATE UNIQUE INDEX issue_priority_name_unq ON issue_priority (priority_name);
UPDATE issue_priority set priority_name = id;
ALTER TABLE issue_priority ALTER COLUMN priority_name SET NOT NULL;

-- drop existing ID column
ALTER TABLE issue_priority DROP COLUMN id;

-- create a new id column PK
 ALTER TABLE issue_priority ADD COLUMN id bigserial PRIMARY KEY;