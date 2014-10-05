ALTER TABLE fim_user RENAME COLUMN login To login_name;
ALTER TABLE fim_user RENAME COLUMN password To user_password;

UPDATE fim_user SET created_at = now() WHERE created_at is null;
UPDATE fim_user SET modified_at = now() WHERE modified_at is null;

ALTER TABLE fim_user ALTER COLUMN created_at SET DEFAULT now();
ALTER TABLE fim_user ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE fim_user ALTER COLUMN modified_at SET DEFAULT now();
ALTER TABLE fim_user ALTER COLUMN modified_at SET NOT NULL;

CREATE OR REPLACE FUNCTION update_modified_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.modified_at = now(); 
   RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_fim_user_modtime BEFORE UPDATE ON fim_user FOR EACH ROW EXECUTE PROCEDURE update_modified_at_column();