CREATE INDEX fim_user_firtstname_idx ON fim_user (first_name);
CREATE INDEX fim_user_lastname_idx ON fim_user (last_name);
CREATE INDEX fim_user_status_idx ON fim_user (status);

CREATE INDEX user_one_time_link_uuid_idx ON user_one_time_link (uuid);
CREATE INDEX user_one_time_link_designation_idx ON user_one_time_link (designation);
CREATE INDEX user_one_time_link_expiresat_idx ON user_one_time_link (expires_at);