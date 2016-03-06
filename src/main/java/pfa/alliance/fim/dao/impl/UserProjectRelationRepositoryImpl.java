/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import static pfa.alliance.fim.dao.impl.DaoUtil.uniqueResult;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pfa.alliance.fim.dao.UserProjectRelationRepository;
import pfa.alliance.fim.model.project.UserProjectRelation;

/**
 * This class is the implementation of {@link UserProjectRelationRepository}.
 * 
 * @author Csaba
 */
@Singleton
class UserProjectRelationRepositoryImpl
    extends AbstractJpaRepository<UserProjectRelation, Long>
    implements UserProjectRelationRepository
{

    @Override
    public UserProjectRelation findByUserAndProject( int userId, String projectCode )
    {
        EntityManager em = getEntityManager();
        TypedQuery<UserProjectRelation> query =
            em.createQuery( "SELECT r FROM pfa.alliance.fim.model.project.UserProjectRelation r WHERE r.user.id = :id AND r.project.code = :code",
                            UserProjectRelation.class );
        query.setParameter( "id", userId );
        query.setParameter( "code", projectCode );
        List<UserProjectRelation> result = query.getResultList();
        return uniqueResult( result );
    }

    @Override
    protected Class<UserProjectRelation> getEntityClass()
    {
        return UserProjectRelation.class;
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }

}
