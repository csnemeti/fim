/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

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

}
