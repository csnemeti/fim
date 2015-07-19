ALTER TABLE project DROP issue_flow_id;

DROP TABLE IF EXISTS issue_state_relation CASCADE;
DROP TABLE IF EXISTS issue_state_flow CASCADE;
DROP TABLE IF EXISTS issue_state CASCADE;

CREATE TABLE issue_state (
	id bigserial PRIMARY KEY,
	initial_state boolean NOT NULL DEFAULT FALSE,
	final_state boolean  NOT NULL DEFAULT FALSE,
	state_name varchar(50) NOT NULL,
	code varchar(20) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);

INSERT INTO issue_state(code, state_name, created_at, modified_at, initial_state, final_state) VALUES 
('NEW','New', now(), now(), TRUE , FALSE),
('PRGS','In progress', now(), now(), FALSE, FALSE),
('TEST','Testing', now(), now(), FALSE, FALSE),
('DONE','Done', now(), now(), FALSE, TRUE);

CREATE TABLE issue_state_flow (
	id bigserial PRIMARY KEY,
	flow_name varchar(40) NOT NULL,
	description varchar(255) NOT NULL,
	
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);

INSERT INTO issue_state_flow(flow_name, description, created_at, modified_at) VALUES 
('DEFAULT_FLOW_1', 'Simple flow: NEW -> IN PROGRESS -> DONE', now(), now()),
('DEFAULT_FLOW_2', 'Simple flow with TEST: NEW -> IN PROGRESS -> IN TESTING -> DONE', now(), now());

CREATE TABLE issue_state_relation (
	id bigserial PRIMARY KEY,
	flow_id bigint NOT NULL,
	initial_state bigint NOT NULL,
	next_state bigint NOT NULL,
	bidirectional_flag bool NOT NULL DEFAULT false
);

INSERT INTO issue_state_relation(flow_id, initial_state, next_state, bidirectional_flag) VALUES 
(1, (SELECT id FROM issue_state WHERE code = 'NEW'),(SELECT id FROM issue_state WHERE code = 'PRGS'), true),
(1, (SELECT id FROM issue_state WHERE code = 'PRGS'),(SELECT id FROM issue_state WHERE code = 'DONE'), false),
(2, (SELECT id FROM issue_state WHERE code = 'NEW'),(SELECT id FROM issue_state WHERE code = 'PRGS'), true),
(2, (SELECT id FROM issue_state WHERE code = 'PRGS'),(SELECT id FROM issue_state WHERE code = 'TEST'), false),
(2, (SELECT id FROM issue_state WHERE code = 'TEST'),(SELECT id FROM issue_state WHERE code = 'DONE'), false),
(2, (SELECT id FROM issue_state WHERE code = 'TEST'),(SELECT id FROM issue_state WHERE code = 'NEW'), false);

ALTER TABLE project ADD issue_flow_id int NOT NULL DEFAULT 1;
ALTER TABLE project ALTER COLUMN issue_flow_id DROP DEFAULT;