/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.RoleAndPermissionRepository;
import pfa.alliance.fim.dao.UserOneTimeLinkRepository;
import pfa.alliance.fim.dao.UserRepository;

import com.google.inject.AbstractModule;

/**
 * Module containing the DAO module.
 * 
 * @author Csaba
 */
public class FimDaoModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        bind( UserRepository.class ).to( UserRepositoryImpl.class );
        bind( ProjectRepository.class ).to( ProjectRepositoryImpl.class );
        bind( UserOneTimeLinkRepository.class ).to( UserOneTimeLinkRepositoryImpl.class );
        bind( RoleAndPermissionRepository.class ).to( RoleAndPermissionRepositoryImpl.class );
    }

}
