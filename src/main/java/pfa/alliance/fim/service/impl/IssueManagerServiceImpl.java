/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.IssueRepository;
import pfa.alliance.fim.dao.IssueStateRepository;
import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.issue.IssueBaseDTO;
import pfa.alliance.fim.model.issue.Issue;
import pfa.alliance.fim.model.issue.IssueType;
import pfa.alliance.fim.model.issue.states.IssueState;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.IssueManagerService;

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
                             FimUrlGeneratorService fimUrlGeneratorService )
    {
        this.issueRepository = issueRepository;
        this.issueStateRepository = issueStateRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.issuePriorityRepository = issuePriorityRepository;

        this.emailService = emailService;
        this.emailGeneratorService = emailGeneratorService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
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
        List<IssueBaseDTO> dummy = new ArrayList<IssueBaseDTO>();
        dummy.add( new IssueBaseDTO( 2, null, 5, "P44-2", "Title 2", IssueType.EPIC ) );
        dummy.add( new IssueBaseDTO( 3, null, 5, "P44-3", "Title 3", IssueType.FEATURE ) );
        dummy.add( new IssueBaseDTO( 4, null, 5, "P44-4", "Title 4", IssueType.STORY ) );
        dummy.add( new IssueBaseDTO( 5, null, 5, "P44-5", "Title 5", IssueType.SUB_STORY ) );
        dummy.add( new IssueBaseDTO( 6, null, 5, "P44-6", "Title 6", IssueType.ENHANCEMENT ) );
        dummy.add( new IssueBaseDTO( 7, null, 5, "P44-7", "Title 7", IssueType.TASK ) );
        dummy.add( new IssueBaseDTO( 8, null, 5, "P44-8", "Title 8", IssueType.SUB_TASK ) );
        return dummy;
    }

    @Override
    public List<IssueBaseDTO> getChildernFor( long id )
    {
        List<IssueBaseDTO> dummy = new ArrayList<IssueBaseDTO>();
        dummy.add( new IssueBaseDTO( 2, null, 5, "P44-2", "Title 2", IssueType.EPIC ) );
        dummy.add( new IssueBaseDTO( 3, null, 5, "P44-3", "Title 3", IssueType.FEATURE ) );
        dummy.add( new IssueBaseDTO( 4, null, 5, "P44-4", "Title 4", IssueType.STORY ) );
        dummy.add( new IssueBaseDTO( 5, null, 5, "P44-5", "Title 5", IssueType.SUB_STORY ) );
        dummy.add( new IssueBaseDTO( 6, null, 5, "P44-6", "Title 6", IssueType.ENHANCEMENT ) );
        dummy.add( new IssueBaseDTO( 7, null, 5, "P44-7", "Title 7", IssueType.TASK ) );
        dummy.add( new IssueBaseDTO( 8, null, 5, "P44-8", "Title 8", IssueType.SUB_TASK ) );
        return dummy;
    }
}
