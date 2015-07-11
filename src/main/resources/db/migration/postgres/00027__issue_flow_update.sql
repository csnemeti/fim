UPDATE issue_state set version = 0;


ALTER TABLE issue_state_flow ADD COLUMN description varchar(255);
UPDATE issue_state_flow set description = 'Simple flow: NEW -> IN PROGRESS -> DONE', version = 0, name = 'Simple flow', modified_at= now()  WHERE name = 'DEFAULT_FLOW_1';
UPDATE issue_state_flow set description = 'Simple flow with TEST: NEW -> IN PROGRESS -> IN TESTING -> DONE', version = 0, name = 'Simple flow with TEST', modified_at= now()  WHERE name = 'DEFAULT_FLOW_2';

ALTER TABLE issue_state_flow ALTER COLUMN description SET NOT NULL;
ALTER TABLE issue_state_flow ADD CONSTRAINT issue_state_flow_name_unq UNIQUE (name);
ALTER TABLE issue_state_flow RENAME COLUMN name TO flow_name;

UPDATE issue_state_relation SET bidirectional_flag = true WHERE initial_state = (SELECT id FROM issue_state WHERE code = 'NEW') and next_state = (SELECT id FROM issue_state WHERE code = 'PRGS');
ALTER TABLE issue_state_relation RENAME COLUMN initial_state TO from_state;

ALTER TABLE project RENAME COLUMN id_issue_flow TO issue_flow_id;
ALTER TABLE project ALTER COLUMN issue_flow_id DROP DEFAULT;
