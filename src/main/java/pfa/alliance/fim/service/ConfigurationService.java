package pfa.alliance.fim.service;

/**
 * @author Balaceanu Sergiu-Denis
 */
public interface ConfigurationService
{
    String EMAIL_SEND_ISSUE_CREATE = "emailSend.issue.create";

    String EMAIL_SEND_ISSUE_EDITE = "emailSend.issue.edit";

    String EMAIL_SEND_ISSUE_ASSIGNMENT_CHANGE = "emailSend.issue.assignmentChange";

    String EMAIL_SEND_ISSUE_COMMENT_ADD = "emailSend.issue.comment.add";

    String EMAIL_SEND_ISSUE_COMMENT_EDIT = "emailSend.issue.comment.edit";

    String EMAIL_SEND_ISSUE_COMMENT_DELETE = "emailSend.issue.comment.delete";

    String EMAIL_SEND_ISSUE_ATTACHMENT_ADD = "emailSend.issue.attachment.add";

    String EMAIL_SEND_ISSUE_ATTACHMENT_DELETE = "emailSend.issue.attachment.delete";

    String EMAIL_SEND_ISSUE_WORKLOG_ADD = "emailSend.issue.worlLog.add";

    String EMAIL_SEND_ISSUE_WORKLOG_EDIT = "emailSend.issue.worlLog.edit";

    String EMAIL_SEND_ISSUE_WORKLOG_DELETE = "emailSend.issue.worlLog.delete";

    String EMAIL_SEND_PROJECT_CREATE = "emailSend.project.create";

    String EMAIL_SEND_PROJECT_EDIT = "emailSend.project.edite";

    String EMAIL_SEND_PROJECT_STATUS_CHANGE = "emailSend.project.statusChange";

    String EMAIL_SEND_PROJECT_OWNER_CHANGE = "emailSend.project.ownerChange";

    String EMAIL_SEND_PROJECT_USER_ASSIGNMENT_CHANGE = "emailSend.project.userAssignmentChange";


    /**
     * Method that verifies if configuration is completed.
     * 
     * @return true if the configuration is completed , false otherwise
     */
    boolean isConfigurationCompleted();

    /**
     * Gets the boolean value of the given key.
     * 
     * @param key the key witch value to be extracted
     * @return true if the value is found and is true, false otherwise
     */
    boolean getBoolean( String key );

    /**
     * Gets the boolean value of the given key.
     * 
     * @param key the key witch value to be extracted
     * @return true if the value is found and is true, false if the value is found and is not true, null if value is not
     *         found
     */
    Boolean getBooleanObject( String key );
}
