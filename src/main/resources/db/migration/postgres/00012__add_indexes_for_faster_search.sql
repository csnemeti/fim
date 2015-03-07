CREATE INDEX fim_user_lower_firtstname_idx ON fim_user (lower(first_name));
CREATE INDEX fim_user_lower_lastname_idx ON fim_user (lower(last_name));
CREATE INDEX fim_user_lower_email_idx ON fim_user (lower(email));

CREATE INDEX project_lower_name_idx ON project (lower(project_name));