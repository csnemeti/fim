DELETE FROM issue_priority;
DROP INDEX issue_priority_order_unq;
DROP INDEX issue_priority_default_option_idx;
DROP INDEX issue_priority_name_unq;

ALTER TABLE issue_priority ADD COLUMN project_id integer NOT NULL;

CREATE UNIQUE INDEX issue_priority_order_unq ON issue_priority (order_value, project_id);
CREATE UNIQUE INDEX issue_priority_name_unq ON issue_priority (priority_name, project_id);
CREATE INDEX issue_priority_default_option_idx ON issue_priority (default_option, project_id);

INSERT INTO issue_priority (priority_name, default_option, order_value, project_id) SELECT 'MINOR' as priority_name, false as default_option, 10 as order_value, id from project;
INSERT INTO issue_priority (priority_name, default_option, order_value, project_id) SELECT 'MEDIUM' as priority_name, true as default_option, 20 as order_value, id from project;
INSERT INTO issue_priority (priority_name, default_option, order_value, project_id) SELECT 'HIGH' as priority_name, false as default_option,  30 as order_value, id from project;
INSERT INTO issue_priority (priority_name, default_option, order_value, project_id) SELECT 'CRTICAL' as priority_name, false as default_option, 40 as order_value, id from project;
INSERT INTO issue_priority (priority_name, default_option, order_value, project_id) SELECT 'BLOCKER' as priority_name, false as default_option, 50 as order_value, id from project;
