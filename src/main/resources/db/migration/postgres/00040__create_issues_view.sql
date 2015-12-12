CREATE OR REPLACE VIEW issues_view AS 
select i.id as iid, i.issue_code, i.parent_id as parent_iid, pi.issue_code as parent_code, i.project_id, i.title, i.description, i.issue_type, 
i.state_id, s.state_name, s.code as state_code, i.resolution, i.priority_id, pr.priority_name, i.created_at, i.modified_at,
i.reported_by as reported_by_id, fim_user_name(i.reported_by) as reported_by_name, fim_user_name_and_email(i.reported_by) as reported_by_name_and_email,
i.assigned_to as assigned_to_id, fim_user_name(i.assigned_to) as assigned_to_name, fim_user_name_and_email(i.assigned_to) as assigned_to_name_and_email,
false as has_attachments, false as has_image_attachments, false as has_comments
from issue i left join issue pi on i.parent_id = pi.id
inner join issue_state s on i.state_id = s.id
inner join issue_priority pr on i.priority_id = pr.id
