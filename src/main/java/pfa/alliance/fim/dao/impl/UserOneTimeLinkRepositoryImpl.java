/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.sql.Timestamp;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This is the implementation for {@link UserOneTimeLinkRepository}.
 * 
 * @author Csaba
 */
@Singleton
class UserOneTimeLinkRepositoryImpl
    extends AbstractJpaRepository<UserOneTimeLink, Long>
    implements UserOneTimeLinkRepository
{

    @Override
    protected Class<UserOneTimeLink> getEntityClass()
    {
        return UserOneTimeLink.class;
    }

    @Override
    protected Class<Long> getIdClass()
    {
        return Long.class;
    }

    @Override
    public int deleteExpiredLinks()
    {
        EntityManager em = getEntityManager();
        Query query = em.createQuery( "DELETE From " + getEntityClass().getName() + " l WHERE l.expiresAt < :now" );
        query.setParameter( "now", new Timestamp( System.currentTimeMillis() ) );
        return query.executeUpdate();
    }

}
