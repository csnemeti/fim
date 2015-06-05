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

}
