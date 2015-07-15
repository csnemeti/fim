package pfa.alliance.fim.model.project;

import pfa.alliance.fim.model.user.UserRole;


/**
 * This enumeration keeps the roles of a user inside a project.
 * 
 * @author Dennis
 */
public enum UserRoleInsideProject {
    SCRUM_MASTER( UserRole.SCRUM_MASTER ), PRODUCT_OWNER( UserRole.PRODUCT_OWNER ), TEAM( UserRole.TEAM ), STATISTICAL(
        UserRole.STATISTICAL ), OWNER( UserRole.PROJECT_ADMIN );

    /** The corresponding {@link UserRole} to the given role. */
    private final UserRole correspondingUserRole;

    /**
     * Called when instance of this class is created.
     * 
     * @param correspondingUserRole the corresponding {@link UserRole}
     */
    private UserRoleInsideProject( UserRole correspondingUserRole )
    {
        this.correspondingUserRole = correspondingUserRole;
    }

    /**
     * Gets the corresponding {@link UserRole} for this role.
     * 
     * @return the corresponding Role
     */
    public UserRole getCorrespondingUserRole()
    {
        return correspondingUserRole;
    }

    public static UserRoleInsideProject findByUserRole( UserRole role )
    {
        UserRoleInsideProject matchingRole = null;
        for ( UserRoleInsideProject candidate : values() )
        {
            if ( candidate.getCorrespondingUserRole().equals( role ) )
            {
                matchingRole = candidate;
                break;
            }
        }
        return matchingRole;
    }
}
