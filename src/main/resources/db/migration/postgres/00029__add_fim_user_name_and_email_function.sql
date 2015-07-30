DROP VIEW IF EXISTS users_view; 
DROP VIEW IF EXISTS active_users_view; 
DROP FUNCTION IF EXISTS fim_user_name_and_email(integer);
DROP FUNCTION IF EXISTS fim_user_projects_and_role(integer);


CREATE OR REPLACE FUNCTION fim_user_name_and_email(user_id integer) RETURNS varchar(1024) AS $$
	DECLARE ret varchar(1024); BEGIN select into ret trim(trim(coalesce(first_name, '') || ' ' || coalesce(last_name, '')) || ' <' || email || '>') from fim_user where id = user_id; return ret; END; $$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION fim_user_projects_and_role(p_user_id integer) RETURNS TEXT AS $$
	DECLARE rec RECORD; result TEXT = ''; BEGIN FOR rec IN Select ' ' || p.project_id || '(' || p.user_role || ')' AS relation from user_project_relation p where p.user_id = p_user_id LOOP result = result || rec.relation; END LOOP; return result; END; $$ LANGUAGE plpgsql;	
	
	
CREATE OR REPLACE VIEW users_view AS 
	SELECT id, first_name, last_name, email, status, default_role, created_at, modified_at 
	FROM fim_user;
	
CREATE OR REPLACE VIEW active_users_view AS 
	SELECT u.id, u.first_name, u.last_name, u.email,  
		fim_user_name_and_email(u.id) AS name_and_email, 
		fim_user_projects_and_role(u.id) AS projects_and_roles, 
		u.default_role, u.created_at, GREATEST(MAX(upr.modified_at), u.modified_at) AS modified_at
	FROM fim_user u 
	LEFT JOIN user_project_relation upr ON upr.user_id = u.id
	WHERE u.status = 'ACTIVE'
	GROUP BY u.id, u.first_name, u.last_name, u.email, name_and_email, projects_and_roles, u.default_role, u.created_at;