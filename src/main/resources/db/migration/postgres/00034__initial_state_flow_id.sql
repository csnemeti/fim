ALTER TABLE issue_state ADD COLUMN flow_id bigint;
UPDATE issue_state SET version = 0, flow_id = (SELECT max(id) FROM issue_state_flow);
ALTER TABLE issue_state ALTER COLUMN flow_id SET NOT NULL;

INSERT INTO issue_state(code, state_name, created_at, modified_at, version, initial_state, final_state, flow_id) VALUES 
('NEW','New', now(), now(), 0, TRUE , FALSE, (SELECT min(id) FROM issue_state_flow)),
('PRGS','In progress', now(), now(), 0, FALSE, FALSE, (SELECT min(id) FROM issue_state_flow)),
('TEST','Testing', now(), now(), 0, FALSE, FALSE, (SELECT min(id) FROM issue_state_flow)),
('DONE','Done', now(), now(), 0, FALSE, TRUE, (SELECT min(id) FROM issue_state_flow));