/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.model.issue.IssuePriority;
import pfa.alliance.fim.service.IssuePriorityManagementService;

/**
 * This is the implementation for {@link IssuePriorityManagementService}
 * 
 * @author Csaba
 */
@Singleton
class IssuePriorityManagementServiceImpl
    implements IssuePriorityManagementService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( IssuePriorityManagementServiceImpl.class );
    /** The {@link IssuePriorityRepository} instance to be used in this class. */
    private final IssuePriorityRepository issuePriorityRepository;

    /**
     * Called when instance of this class is created
     * 
     * @param issuePriorityRepository - the {@link IssuePriorityRepository} instance to be used in this class
     */
    @Inject
    public IssuePriorityManagementServiceImpl( IssuePriorityRepository issuePriorityRepository )
    {
        this.issuePriorityRepository = issuePriorityRepository;
    }

    @Override
    public List<IssuePriority> getPrioritiesForProject( int projectId )
    {
        LOG.debug( "Getting priorities for Project: {}", projectId );
        return issuePriorityRepository.findAll( projectId );
    }

}
