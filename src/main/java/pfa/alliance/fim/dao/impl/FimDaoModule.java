/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.dao.ProjectComponentRepository;
import pfa.alliance.fim.dao.ProjectLabelRepository;
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
        bind( ProjectComponentRepository.class ).to( ProjectComponentRepositoryImpl.class );
        bind( ProjectLabelRepository.class ).to( ProjectLabelRepositoryImpl.class );
        bind( UserOneTimeLinkRepository.class ).to( UserOneTimeLinkRepositoryImpl.class );
        bind( RoleAndPermissionRepository.class ).to( RoleAndPermissionRepositoryImpl.class );
        bind( IssuePriorityRepository.class ).to( IssuePriorityRepositoryImpl.class );
        bind( IssueStateRepository.class ).to( IssueStateRepositoryImpl.class );
        bind( IssueRepository.class ).to( IssueRepositoryImpl.class );
    }

}
