CREATE TABLE issue_priority_localized (
	id  bigserial PRIMARY KEY,
	locale varchar(6) NOT NULL,
	value varchar(100) NOT NULL,
	record_id int8 NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4,
	
	CONSTRAINT issue_priority_localized_locale_fk FOREIGN KEY (locale) REFERENCES enum_supported_locales (locale),
	CONSTRAINT issue_priority_localized_record_id_fk FOREIGN KEY (record_id) REFERENCES issue_priority (id)
);
CREATE UNIQUE INDEX issue_priority_localized_unq ON issue_priority_localized (locale, record_id);

-- we delete english because it's default and stored in record name / tag / label / etc.
DELETE FROM enum_supported_locales WHERE locale = 'en';

-- add issue_state column as default text (in english)
ALTER TABLE issue_state ADD COLUMN state_name varchar(50);
UPDATE issue_state set state_name = 'New' WHERE code = 'NEW';
UPDATE issue_state set state_name = 'In progress' WHERE code = 'PRGS';
UPDATE issue_state set state_name = 'Testing' WHERE code = 'TEST';
UPDATE issue_state set state_name = 'Done' WHERE code = 'DONE';
ALTER TABLE issue_priority ALTER COLUMN priority_name SET NOT NULL;

-- add localized table
CREATE TABLE issue_state_localized (
	id  bigserial PRIMARY KEY,
	locale varchar(6) NOT NULL,
	value varchar(100) NOT NULL,
	record_id int8 NOT NULL,
	created_at timestamp with time zone NOT NULL DEFAULT now(),
	modified_at timestamp with time zone NOT NULL DEFAULT now(),
	version int4,
	
	CONSTRAINT issue_state_localized_locale_fk FOREIGN KEY (locale) REFERENCES enum_supported_locales (locale),
	CONSTRAINT issue_state_localized_record_id_fk FOREIGN KEY (record_id) REFERENCES issue_state (id)
);
CREATE UNIQUE INDEX issue_state_localized_unq ON issue_state_localized (locale, record_id);

INSERT INTO issue_state_localized(locale, value, record_id, created_at, modified_at) VALUES 
('ro', 'Nou', (SELECT id FROM issue_state WHERE code = 'NEW'), now(), now()),
('ro', 'In lucru', (SELECT id FROM issue_state WHERE code = 'PRGS'), now(), now()),
('ro', 'In testare', (SELECT id FROM issue_state WHERE code = 'TEST'), now(), now()),
('ro', 'Terminat', (SELECT id FROM issue_state WHERE code = 'DONE'), now(), now());
