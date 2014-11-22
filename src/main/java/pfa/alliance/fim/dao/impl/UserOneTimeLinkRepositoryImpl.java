/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.model.user.UserOneTimeLink;

/**
 * This is the implementation for {@link UserOneTimeLinkRepository}.
 * 
 * @author Csaba
 */
public class UserOneTimeLinkRepositoryImpl
    extends AbstractJpaRepository<UserOneTimeLink, Long>
    implements UserOneTimeLinkRepository
{

    @Override
    protected Class<UserOneTimeLink> getEntityClass()
    {
        return UserOneTimeLink.class;
    }

}
