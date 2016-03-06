/**
 * 
 */
package pfa.alliance.fim.dao;

import pfa.alliance.fim.model.project.UserProjectRelation;

/**
 * This interface refines the repository methods for managing {@link UserProjectRelation}.
 * 
 * @author Csaba
 */
public interface UserProjectRelationRepository
    extends JpaRepository<UserProjectRelation, Long>
{
    /**
     * Finds a relation by User id and project code.
     * 
     * @param userId the ID of the user
     * @param projectCode the project code
     * @return the relation if found, null if relation is not found
     */
    UserProjectRelation findByUserAndProject( int userId, String projectCode );
}
