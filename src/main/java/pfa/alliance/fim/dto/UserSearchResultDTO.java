/**
 * 
 */
package pfa.alliance.fim.dto;

import pfa.alliance.fim.model.user.UserRole;
import pfa.alliance.fim.model.user.UserStatus;

/**
 * This class defines a search result for user.
 * 
 * @author Csaba
 */
public class UserSearchResultDTO
    extends UserDTO
{
    private static final long serialVersionUID = 2652129191165807648L;

    /** This represents the index value of this item in the total results list. */
    private int indexInTotalResults;
    /** This represents the index value of this item in the currently displayed results list. */
    private int indexInCurrentResults;

    /** Textual name of the default role. */
    private String defaultRoleAsText;

    /** Textual name of the user status. */
    private String userStatusAsText;

    /** The user status. This will not be persisted into JSon. */
    private transient UserStatus userStatus;

    /** HTML encoded text for actions to fill in. */
    private String actions = "";

    /**
     * Called when instance of this class is created.
     */
    public UserSearchResultDTO()
    {
        super();
    }
    
    /**
     * Called when instance of this class is created.
     * 
     * @param id the User ID
     * @param firstName the User first name
     * @param lastName the user last name
     * @param email the user e-mail address
     * @param userStatus the status of the user
     * @param defaultRole the suer default role
     */
    public UserSearchResultDTO( Integer id, String firstName, String lastName, String email, UserStatus userStatus,
                                UserRole defaultRole )
    {
        super( id, firstName, lastName, email );
        setUserStatus( userStatus );
        setDefaultRole( defaultRole );
    }

    public void setDefaultRole( UserRole defaultRole )
    {
        this.defaultRoleAsText = defaultRole.name();
    }
    public void setUserStatus( UserStatus userStatus )
    {
        this.userStatus = userStatus;
        this.userStatusAsText = userStatus.name();
    }

    public UserStatus getUserStatus()
    {
        return userStatus;
    }
    public int getIndexInTotalResults()
    {
        return indexInTotalResults;
    }

    public void setIndexInTotalResults( int indexInTotalResults )
    {
        this.indexInTotalResults = indexInTotalResults;
    }

    public int getIndexInCurrentResults()
    {
        return indexInCurrentResults;
    }

    public void setIndexInCurrentResults( int indexInCurrentResults )
    {
        this.indexInCurrentResults = indexInCurrentResults;
    }

    public String getDefaultRoleAsText()
    {
        return getEmptyStringIfNull( defaultRoleAsText );
    }

    public String getUserStatusAsText()
    {
        return getEmptyStringIfNull( userStatusAsText );
    }

    public String getActions()
    {
        return getEmptyStringIfNull( actions );
    }

    public void setActions( String actions )
    {
        this.actions = actions;
    }

    @Override
    public String getFirstName()
    {
        return getEmptyStringIfNull( super.getFirstName() );
    }

    @Override
    public String getLastName()
    {
        return getEmptyStringIfNull( super.getLastName() );
    }

    @Override
    public String getEmail()
    {
        return getEmptyStringIfNull( super.getEmail() );
    }

    /**
     * Corrects the string value by returning empty string in case value is null. This is required by JSon
     * serialization.
     * 
     * @param value the value to correct
     * @return the corrected value
     */
    private static String getEmptyStringIfNull( final String value )
    {
        return ( value != null ) ? value : "";
    }
}