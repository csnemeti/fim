DROP TABLE IF EXISTS issue_state_localized CASCADE;
DROP TABLE IF EXISTS issue_state_ml CASCADE;

DROP TABLE IF EXISTS issue_state_relation CASCADE;
DROP TABLE IF EXISTS issue_state_flow CASCADE;

DROP TABLE IF EXISTS issue_state CASCADE;

CREATE TABLE issue_state (
	id serial PRIMARY KEY,
	initial_state boolean NOT NULL DEFAULT FALSE,
	final_state boolean  NOT NULL DEFAULT FALSE,
	state_name varchar(50) NOT NULL,
	code varchar(5) NOT NULL,
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
	id serial PRIMARY KEY,
	name varchar(40) NOT NULL,
	
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);

INSERT INTO issue_state_flow(name, created_at, modified_at) VALUES 
('DEFAULT_FLOW_1', now(), now()),
('DEFAULT_FLOW_2', now(), now());

CREATE TABLE issue_state_relation (
	id serial PRIMARY KEY,
	id_flow int NOT NULL,
	initial_state int NOT NULL,
	next_state int NOT NULL,
	bidirectional_flag bool NOT NULL DEFAULT false
);

INSERT INTO issue_state_relation(id_flow, initial_state, next_state, bidirectional_flag) VALUES 
(1, (SELECT id FROM issue_state WHERE code = 'NEW'),(SELECT id FROM issue_state WHERE code = 'PRGS'), false),
(1, (SELECT id FROM issue_state WHERE code = 'PRGS'),(SELECT id FROM issue_state WHERE code = 'DONE'), false),
(2, (SELECT id FROM issue_state WHERE code = 'NEW'),(SELECT id FROM issue_state WHERE code = 'PRGS'), false),
(2, (SELECT id FROM issue_state WHERE code = 'PRGS'),(SELECT id FROM issue_state WHERE code = 'TEST'), false),
(2, (SELECT id FROM issue_state WHERE code = 'TEST'),(SELECT id FROM issue_state WHERE code = 'DONE'), false),
(2, (SELECT id FROM issue_state WHERE code = 'TEST'),(SELECT id FROM issue_state WHERE code = 'NEW'), false);


