/**
 * 
 */
package pfa.alliance.fim.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * This entity is used for reading from DB the mapping between the {@link UserRole} and corresponding list of
 * {@link UserPermission}. This entity contains only one such mapping (between role and permission) so the set of
 * permissions for a given role should be a Set<PermissionToRole>.
 * 
 * @author Csaba
 */
@Entity( name = "permission_to_role" )
@Table( uniqueConstraints = @UniqueConstraint( name = "permission_to_role_unq", columnNames = { "user_role",
    "user_permission" } ) )
public class PermissionToRole
{
    @Id
    @Column( name = "id" )
    private Integer id;

    @Enumerated( EnumType.STRING )
    @Column( name = "user_role", length = 20, nullable = false )
    private UserRole userRole;

    @Enumerated( EnumType.STRING )
    @Column( name = "user_permission", length = 30, nullable = false )
    private UserPermission userPermission;

    public Integer getId()
    {
        return id;
    }

    public UserRole getUserRole()
    {
        return userRole;
    }

    public UserPermission getUserPermission()
    {
        return userPermission;
    }

}
