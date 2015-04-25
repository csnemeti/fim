CREATE TABLE issue_priority (
	id varchar(50) PRIMARY KEY,
	order_value integer NOT NULL,
	default_option boolean NOT NULL
);

CREATE UNIQUE INDEX issue_priority_order_unq ON issue_priority (order_value);
CREATE INDEX issue_priority_default_option_idx ON issue_priority (default_option);

INSERT INTO issue_priority (id, order_value, default_option) VALUES('MINOR', 10, false);
INSERT INTO issue_priority (id, order_value, default_option) VALUES('MEDIUM', 20, true);
INSERT INTO issue_priority (id, order_value, default_option) VALUES('HIGH', 30, false);
INSERT INTO issue_priority (id, order_value, default_option) VALUES('CRTICAL', 60, false);
INSERT INTO issue_priority (id, order_value, default_option) VALUES('BLOCKER', 100, false);
