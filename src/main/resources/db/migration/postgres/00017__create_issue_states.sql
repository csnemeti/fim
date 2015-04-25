CREATE TABLE issue_state (
	id serial PRIMARY KEY,

	code varchar(5) NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);

CREATE TABLE issue_state_ml (
	idlanguage integer NOT NULL,
	name varchar(40) NOT NULL,
	id_issue_state integer NOT NULL,

	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4
);

INSERT INTO issue_state(code, created_at, modified_at) VALUES 
('NEW', now(), now()),
('PRGS', now(), now()),
('TEST', now(), now()),
('DONE', now(), now());

INSERT INTO issue_state_ml(idlanguage, name, id_issue_state, created_at, modified_at) VALUES 
(1, 'New', (SELECT id FROM issue_state WHERE code = 'NEW'), now(), now()),
(2, 'Nou', (SELECT id FROM issue_state WHERE code = 'NEW'), now(), now()),
(1, 'In progress', (SELECT id FROM issue_state WHERE code = 'PRGS'), now(), now()),
(1, 'In lucru', (SELECT id FROM issue_state WHERE code = 'PRGS'), now(), now()),
(1, 'Testing', (SELECT id FROM issue_state WHERE code = 'TEST'), now(), now()),
(1, 'In testare', (SELECT id FROM issue_state WHERE code = 'TEST'), now(), now()),
(1, 'Done', (SELECT id FROM issue_state WHERE code = 'DONE'), now(), now()),
(1, 'Terminat', (SELECT id FROM issue_state WHERE code = 'DONE'), now(), now());

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
(2, (SELECT id FROM issue_state WHERE code = 'TEST'),(SELECT id FROM issue_state WHERE code = 'DONE'), false);

ALTER TABLE project ADD id_issue_flow int NOT NULL DEFAULT 1;

