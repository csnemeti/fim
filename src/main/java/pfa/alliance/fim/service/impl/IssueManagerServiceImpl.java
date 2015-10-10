/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.issue.DependencyType;
import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.dto.issue.IssueDTO;
import pfa.alliance.fim.dto.issue.IssueDependencyDTO;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.issue.IssueType;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.IssueManagerService;
import pfa.alliance.fim.service.LocalizationService;

import com.google.inject.persist.Transactional;

/**
 * This is the implementation for {@link IssueManagerService}.
 * 
 * @author Csaba
 */
@Singleton
class IssueManagerServiceImpl
    implements IssueManagerService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( IssueManagerServiceImpl.class );
    /** The {@link IssueRepository} instance to use in this class. */
    private final IssueRepository issueRepository;

    private final IssueStateRepository issueStateRepository;

    /** The {@link ProjectRepository} instance to use. */
    private final ProjectRepository projectRepository;

    /** The {@link IssuePriorityRepository} instance to use. */
    private final IssuePriorityRepository issuePriorityRepository;

    /** The {@link UserRepository} instance to use in this class. */
    private final UserRepository userRepository;

    /** E-mail service, used for sending e-mails. */
    private final EmailService emailService;

    /** The e-mail content builder service instance. */
    private final EmailGeneratorService emailGeneratorService;

    /** Service used for generating URLs inside FIM. */
    private final FimUrlGeneratorService fimUrlGeneratorService;

    private final LocalizationService localizationService;

    /**
     * Called when instance of this class is created.
     * 
     * @param issueRepository the {@link IssueRepository} instance to use in this class
     * @param projectRepository the {@link ProjectRepository} instance to use in this class
     * @param userRepository the {@link UserRepository} instance to use in this class
     * @param issuePriorityRepository the {@link IssuePriorityRepository} instance to use in this class
     * @param emailService the instance of service used for sending e-mails
     * @param emailGeneratorService the instance of service used for generating e-mails
     * @param fimUrlGeneratorService the instance of service used for generating full URLs inside FIM
     */
    @Inject
    IssueManagerServiceImpl( IssueRepository issueRepository, IssueStateRepository issueStateRepository,
                             ProjectRepository projectRepository, UserRepository userRepository,
                             IssuePriorityRepository issuePriorityRepository,
                             EmailService emailService, EmailGeneratorService emailGeneratorService,
                             FimUrlGeneratorService fimUrlGeneratorService, LocalizationService localizationService )
    {
        this.issueRepository = issueRepository;
        this.issueStateRepository = issueStateRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.issuePriorityRepository = issuePriorityRepository;

        this.emailService = emailService;
        this.emailGeneratorService = emailGeneratorService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
        this.localizationService = localizationService;
    }

    @Override
    public Issue findById( long id )
    {
        Issue issue = issueRepository.findOne( id );
        if ( issue != null )
        {
            // load the rest of dependencies
            issue.getProject();
            issue.getState();
            issue.getPriority();
        }
        return issue;
    }

    @Override
    @Transactional
    public Issue create( Long parentId, IssueType type, int projectId, int reportedUserId, Integer assignedUserId,
                         Long priority, String title, String description, String environment )
    {
        LOG.debug( "Creating issue: type = {}, priorityId = {}, parentId = {}, projectId = {}, reportedBy = {}, assignedId = {}, title = {}",
                   type, priority, parentId, projectId, reportedUserId, assignedUserId, title );
        // create the Issue object and start filling it in
        Issue issue =
            createIssue( parentId, type, projectId, reportedUserId, assignedUserId, priority, title, description,
                         environment );
        // save & send e-mail
        issue = issueRepository.save( issue );
        sendCreateIssueEmail( issue );
        return issue;
    }

    private Issue createIssue( Long parentId, IssueType type, int projectId, int reportedUserId,
                               Integer assignedUserId, Long priority, String title, String description,
                               String environment )
    {
        Issue issue = new Issue();

        issue.setType( type );
        issue.setPriority( issuePriorityRepository.findOne( priority ) );
        issue.setTitle( title );
        issue.setDescription( description );
        issue.setEnvironment( environment );

        User user = userRepository.findOne( reportedUserId );
        issue.setReporter( user );
        if ( assignedUserId != null )
        {
            if ( assignedUserId.intValue() != reportedUserId )
            {
                user = userRepository.findOne( assignedUserId );
            }
            issue.setAssigned( user );
        }

        Project project = projectRepository.findOne( projectId );
        issue.setProject( project );

        // find start state for an issue that will belong to a project
        IssueState state = issueStateRepository.findStartStateForProject( project );
        issue.setState( state );

        return issue;
    }

    /**
     * Send e-mail to assigned user about issue creation.
     * 
     * @param issue the created issue
     */
    private void sendCreateIssueEmail( Issue issue )
    {
        User assignedUser = issue.getAssigned();
        if ( assignedUser != null )
        {

        }
    }

    @Override
    public List<IssueBaseDTO> getAncestorsFor( long id, boolean includeTarget )
    {
        LOG.debug( "Find ancestors for issue ID = {}", id );
        List<IssueBaseDTO> descendants = new ArrayList<IssueBaseDTO>();
        getAncestorsFor( id, includeTarget, descendants );
        // in order to get the ancestors, we must reverse the list
        Collections.reverse( descendants );
        LOG.debug( "Ancestors for issue ID = {} : {}", id, descendants );
        return descendants;
    }

    private void getAncestorsFor( Long issueId, boolean includeTarget, List<IssueBaseDTO> descendants )
    {
        if ( issueId != null )
        {
            IssueBaseDTO dto = issueRepository.findIssueBaseDtoById( issueId );
            if ( dto != null )
            {
                // add link inside
                dto.setUrl( fimUrlGeneratorService.getIssueLink( dto ) );
                if ( includeTarget )
                {
                    descendants.add( dto );
                }
                getAncestorsFor( dto.getParentId(), true, descendants );
            }
        }
    }

    @Override
    public List<IssueDTO> getChildernFor( long id, Locale locale )
    {
        LOG.debug( "Find childern for issue ID = {}", id );
        List<IssueDTO> rawChildren = issueRepository.getChildernFor( id );
        // gets the resource bundle
        ResourceBundle rb = localizationService.getBundle( locale );
        for ( IssueDTO dto : rawChildren )
        {
            dto.setUrl( fimUrlGeneratorService.getIssueLink( dto ) );
            Integer userId = dto.getAssignedToId();
            if ( userId == null )
            {
                String userName = rb.getString( "unassigned" );
                dto.setAssignedTo( userName, userId );
            }
        }
        return rawChildren;
    }

    @Override
    public List<IssueDependencyDTO> getDependenciesFor( long id, Locale locale )
    {
        LOG.debug( "Find childern for issue ID = {}", id );
        List<IssueDependencyDTO> dummy = new ArrayList<>();
        dummy.add( createIssueDependencyDTO( 2, null, 5, "P44-2", "Title 2", IssueType.EPIC, DependencyType.DEPENDS, 1,
                                             "User 1", "State 1", 1, "Priority 1" ) );
        dummy.add( createIssueDependencyDTO( 3, null, 5, "P44-3", "Title 3", IssueType.FEATURE, DependencyType.DEPENDS,
                                             1, "User 1", "State 2", 1, "Priority 1" ) );
        dummy.add( createIssueDependencyDTO( 4, null, 5, "P44-4", "Title 4", IssueType.STORY, DependencyType.DEPENDS,
                                             1, "User 1", "State 3", 1, "Priority 1" ) );
        dummy.add( createIssueDependencyDTO( 5, null, 5, "P44-5", "Title 5", IssueType.SUB_STORY,
                                             DependencyType.BLOCKS, 2, "User 2", "State 4", 4, "Priority 4" ) );
        dummy.add( createIssueDependencyDTO( 6, null, 5, "P44-6", "Title 6", IssueType.ENHANCEMENT,
                                             DependencyType.BLOCKS, 2, "User 2", "State 5", 5, "Priority 5" ) );
        dummy.add( createIssueDependencyDTO( 7, null, 5, "P44-7", "Title 7", IssueType.TASK, DependencyType.BLOCKS,
                                             null, null, "State 6", 6, "Priority 6" ) );
        dummy.add( createIssueDependencyDTO( 8, null, 5, "P44-8", "Title 8", IssueType.SUB_TASK, DependencyType.BLOCKS,
                                             null, null, "State 7", 7, "Priority 7" ) );
        // gets the resource bundle
        ResourceBundle rb = localizationService.getBundle( locale );

        for ( IssueDependencyDTO dto : dummy )
        {
            dto.setUrl( fimUrlGeneratorService.getIssueLink( dto ) );
            Integer userId = dto.getAssignedToId();
            if ( userId == null )
            {
                String userName = rb.getString( "unassigned" );
                dto.setAssignedTo( userName, userId );
            }
            String dependencyKey =
                dto.getDependency() == DependencyType.BLOCKS ? "dependency.blocks" : "dependency.blocked";
            dto.setDependencyName( rb.getString( dependencyKey ) );
        }
        return dummy;
    }
    
    private static IssueDependencyDTO createIssueDependencyDTO( long id, Long parentId, int projectId, String code,
                                                                String title, IssueType type,
                                                                DependencyType dependency, Integer assignedUserId,
                                                                String assignedUserName, String state,
                                                                Integer priorityId, String priorityName )
    {
        IssueDependencyDTO dto = new IssueDependencyDTO( id, parentId, projectId, code, title, type );
        dto.setAssignedTo( assignedUserName, assignedUserId );
        dto.setDependency( dependency );
        if ( state != null )
        {
            dto.setState( 1L, state );
        }
        if ( priorityName != null )
        {
            dto.setPriority( 1L, priorityName );
        }
        return dto;
    }
}
