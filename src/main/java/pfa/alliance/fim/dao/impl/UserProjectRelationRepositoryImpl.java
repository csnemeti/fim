/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

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
