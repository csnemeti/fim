/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.ProjectComponentRepository;
import pfa.alliance.fim.dao.ProjectLabelRepository;
import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.dto.UserDTO;
import pfa.alliance.fim.dto.issue.IssueFlowDTO;
import pfa.alliance.fim.dto.issue.IssueStateDTO;
import pfa.alliance.fim.dto.issue.IssueStateRelationDTO;
import pfa.alliance.fim.model.issue.IssueFlow;
import pfa.alliance.fim.model.issue.states.IssueStateRelation;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectComponent;
import pfa.alliance.fim.model.project.ProjectLabel;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.ProjectTag;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.ProjectManagementService;
import pfa.alliance.fim.util.ColorUtils;

import com.google.inject.persist.Transactional;

/**
 * This is the implementation of {@link ProjectManagementService}
 * 
 * @author Csaba
 */
@Singleton
class ProjectManagementServiceImpl
    implements ProjectManagementService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ProjectManagementServiceImpl.class );

    /** The Project repository to use in this class. */
    private final ProjectRepository projectRepository;

    /** The User repository to use in this class. */
    private final UserRepository userRepository;

    /** The {@link ProjectComponentRepository} to be used by this service. */
    private final ProjectComponentRepository componentRepository;

    /** The {@link ProjectLabelRepository} to be used by this service. */
    private final ProjectLabelRepository labelRepository;

    /** E-mail service, used for sending e-mails. */
    private final EmailService emailService;

    private final EmailGeneratorService emailGeneratorService;

    /** Service used for generating URLs inside FIM. */
    private final FimUrlGeneratorService fimUrlGeneratorService;

    /**
     * Called when instance of this class is created.
     * 
     * @param projectRepository the instance of Project repository to use in this class
     * @param userRepository the instance of User repository to use in this class
     * @param componentRepository the instance of Component repository to be used here
     * @param labelRepository the instance of Label repository to be used here
     * @param emailService the instance of service used for sending e-mails
     * @param emailGeneratorService the instance of service used for generating e-mails
     * @param fimUrlGeneratorService the instance of service used for generating full URLs inside FIM
     */
    @Inject
    ProjectManagementServiceImpl( ProjectRepository projectRepository, UserRepository userRepository,
                                  ProjectComponentRepository componentRepository,
                                  ProjectLabelRepository labelRepository, EmailService emailService,
                                  EmailGeneratorService emailGeneratorService,
                                  FimUrlGeneratorService fimUrlGeneratorService )
    {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.componentRepository = componentRepository;
        this.labelRepository = labelRepository;

        this.emailService = emailService;
        this.emailGeneratorService = emailGeneratorService;
        this.fimUrlGeneratorService = fimUrlGeneratorService;
    }

    @Override
    @Transactional
    public Project create( String name, String code, String description, final boolean hidden,
                           final ProjectState state, final int creatorUserId,
                           Map<Integer, UserRoleInsideProject> additionalUsers, Locale locale )
    {
        Project project = createBaseProjectInstance( name, code, description, hidden, state );
        try
        {
            UserProjectRelation owner = addToProject( project, creatorUserId, UserRoleInsideProject.OWNER );
            project = projectRepository.save( project );
            // project = projectRepository.save( project );
            // send e-mail about project creation to owner
            sendProjectCreatedEmail( owner, locale );
        }
        catch ( PersistenceException e )
        {
            if ( isDuplicateInfoRelatedException( e ) )
            {
                LOG.warn( "Duplicate data for project: {}", project, e );
                throw new DuplicateDataException( "Duplicate project data", e );
            }
            else
            {
                LOG.error( "Could not create the project: {}", project, e );
                throw e;
            }

        }
        return project;
    }

    @Override
    @Transactional
    public Project update( final String oldCode, final String name, final String code, final String description, final boolean hidden,
                           final ProjectState state, final Locale locale )
    {
        LOG.debug( "Updating project with code = {}, name = {}, code = {}, description = {}, hidden = {}, state = {}, locale = {}",
                   oldCode, name, code, description, hidden, state, locale );
        Project project = projectRepository.findByCode( oldCode );
        if ( project != null )
        {
            updateProject( project, name, code, description, hidden, state );
            try
            {
                project = projectRepository.save( project );
            }
            catch ( PersistenceException e )
            {
                if ( isDuplicateInfoRelatedException( e ) )
                {
                    LOG.warn( "Duplicate data for project: {}", project, e );
                    throw new DuplicateDataException( "Duplicate project data", e );
                }
                else
                {
                    LOG.error( "Could not update the project: {}", project, e );
                    throw e;
                }

            }
        }
        return project;
    }

    /**
     * Updates the project with new information.
     * 
     * @param project the project that must be updated
     * @param name the new name of the project
     * @param code the new code of the project
     * @param description the new description of the project
     * @param hidden the new hidden flag of the project
     * @param state the new state of the project
     */
    private void updateProject( Project project, final String name, final String code, final String description,
                                final boolean hidden, final ProjectState state )
    {
        final String oldCode = project.getCode();
        project.setHidden( hidden );
        if ( StringUtils.isNotBlank( name ) )
        {
            LOG.debug( "Setting name = {} for code = {}", name, oldCode );
            project.setName( name );
        }
        if ( StringUtils.isNotBlank( code ) )
        {
            LOG.debug( "Setting code = {} for code = {}", code, oldCode );
            project.setCode( code );
        }
        if ( StringUtils.isNotBlank( description ) )
        {
            LOG.debug( "Setting description = {} for code = {}", description, oldCode );
            project.setDescription( description );
        }
        if ( state != null )
        {
            LOG.debug( "Setting state = {} for code = {}", state, oldCode );
            project.setState( state );
        }
    }

    /**
     * Send an e-mail about created project. If e-mail sending fails, this method will not thrown any error.
     * 
     * @param owner the owner who should receive the e-mail
     */
    private void sendProjectCreatedEmail( UserProjectRelation owner, Locale locale )
    {
        User user = owner.getUser();
        Project project = owner.getProject();
        String subject = null;

        try
        {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put( "name", project.getName() );
            subject = emailGeneratorService.getSubject( EmailType.CREATE_PROJECT, parameters, locale );

            parameters = new HashMap<String, Object>();
            parameters.put( "userName", user.getFirstName() + " " + user.getLastName() );
            parameters.put( "projectName", project.getName() );
            parameters.put( "link", fimUrlGeneratorService.getProjectLink( project.getCode() ) );
            String content = emailGeneratorService.getContent( EmailType.CREATE_PROJECT, parameters, locale );

            emailService.sendEmail( user.getEmail(), subject, content );
        }
        catch ( Exception e )
        {
            LOG.error( "Error sending e-mail: subject = {}, to = {}", subject, user.getEmail(), e );
        }

    }

    /**
     * Checks if the user data that is provided is duplicate or not.
     * 
     * @param e the exception we received
     * @return true if information about duplicate e-mail or username was found
     */
    private boolean isDuplicateInfoRelatedException( PersistenceException e )
    {
        Throwable t = e;
        boolean found = false;
        do
        {
            String message = t.getMessage();
            if ( message.contains( "violates unique constraint" ) )
            {
                found = true;
            }
            t = t.getCause();
        }
        while ( t != null && !found );
        return found;
    }

    /**
     * Add a {@link User} to {@link Project} in the given {@link UserRoleInsideProject}.
     * 
     * @param project the project where user should be added
     * @param userId the ID of the user to be added
     * @param role the user role on the project
     * @return the created relation
     */
    private UserProjectRelation addToProject( Project project, Integer userId, UserRoleInsideProject role )
    {
        User user = userRepository.findOne( userId );
        UserProjectRelation relation = new UserProjectRelation();
        relation.setProject( project );
        relation.setUser( user );
        relation.setUserRole( role );
        Set<UserProjectRelation> relations = project.getUserBoardData();
        if ( relations == null )
        {
            relations = new HashSet<UserProjectRelation>();
            project.setUserBoardData( relations );
        }
        relations.add( relation );
        return relation;
    }

    /**
     * Creates the {@link Project} instance from scratch.
     * 
     * @param name the name of the project
     * @param code the code of the project
     * @param description the project description
     * @param hidden flag indicating project is hidden (not visible on search)
     * @param state the initial state of the project (Accepted values are {@link ProjectState#ACTIVE} and the default
     *            value {@link ProjectState#IN_PREPARATION}.)
     * @return the created Project instance
     */
    private static Project createBaseProjectInstance( String name, String code, String description,
                                                      final boolean hidden, final ProjectState state )
    {
        Project project = new Project();
        project.setName( name );
        project.setCode( code );
        project.setHidden( hidden );
        if ( ProjectState.ACTIVE.equals( state ) )
        {
            project.setState( ProjectState.ACTIVE );
        }
        else
        {
            project.setState( ProjectState.IN_PREPARATION );
        }
        project.setDescription( description );
        return project;
    }

    @Override
    public ProjectDTO getProjectDetails( String code )
    {
        LOG.debug( "Getting project Infor for code = {}", code );
        // unitOfWork.begin();
        try
        {
            Project project = projectRepository.findByCode( code );
            ProjectDTO projectDTO = convertToProjectDTO( project );
            LOG.debug( "Project code = {}, project = {}", code, projectDTO );
            if ( projectDTO != null )
            {
                User owner = projectRepository.findOwnerForProject( project.getId() );
                projectDTO.setOwner( convertToUserDTO( owner ) );
            }
            return projectDTO;
        }
        finally
        {
            // unitOfWork.end();
        }
    }

    @Override
    public Project findByCode( String code )
    {
        return projectRepository.findByCode( code );
    }

    /**
     * Convert {@link Project} into {@link ProjectDTO}
     * 
     * @param project the Project to be converted (could be null)
     * @return the converted project or null if initial Project was null
     */
    private static ProjectDTO convertToProjectDTO( Project project )
    {
        ProjectDTO projectDTO = null;
        if ( project != null )
        {
            projectDTO = new ProjectDTO();
            projectDTO.setId( project.getId() );
            projectDTO.setCode( project.getCode() );
            projectDTO.setName( project.getName() );
            projectDTO.setDescription( project.getDescription() );
            projectDTO.setCreateAt( project.getCreatedAt() );
            projectDTO.setHidden( project.isHidden() );
            projectDTO.setState( project.getState() );
            projectDTO.setStateChangedAt( project.getStateChangedAt() );
            /* projectDTO.setIssueFlow( convertToIssueFlowDTO( project.getIssueFlow() ) ); */

        }
        return projectDTO;
    }

    @SuppressWarnings( "unused" )
    private static IssueFlowDTO convertToIssueFlowDTO( IssueFlow issueFlow )
    {
        IssueFlowDTO issueFlowDTO = null;
        if ( issueFlow != null )
        {
            issueFlowDTO = new IssueFlowDTO();

            if ( CollectionUtils.isNotEmpty( issueFlow.getRelations() ) )
            {

                issueFlowDTO.setRelations( new ArrayList<IssueStateRelationDTO>() );
                for ( IssueStateRelation relation : issueFlow.getRelations() )
                {

                    IssueStateRelationDTO relationDTO = new IssueStateRelationDTO();
                    relationDTO.setBidirectional( relation.isBidirectional() );
                    if ( relation.getInitialState() != null )
                    {
                        IssueStateDTO initialStateDTO = new IssueStateDTO();
                        // TODO fix this
                        // initialStateDTO.setLocalizedName(relation.getInitialState().getLocalizedNamesMap());
                    }
                    if ( relation.getNextState() != null )
                    {
                        IssueStateDTO nextStateDTO = new IssueStateDTO();
                        // TODO fix this
                        // nextStateDTO.setLocalizedName(relation.getInitialState().getLocalizedNamesMap());
                    }
                }
            }

        }
        return issueFlowDTO;
    }

    /**
     * Convert {@link User} into {@link UserDTO}
     * 
     * @param project the User to be converted (could be null)
     * @return the converted user or null if initial User was null
     */
    private static UserDTO convertToUserDTO( User user )
    {
        UserDTO userDTO = null;
        if ( user != null )
        {
            userDTO = new UserDTO();
            userDTO.setId( user.getId() );
            userDTO.setFirstName( user.getFirstName() );
            userDTO.setLastName( user.getLastName() );
            userDTO.setEmail( user.getEmail() );
        }
        return userDTO;
    }

    @Override
    public long count( ProjectSearchDTO criteriaCriteria )
    {
        LOG.debug( "Count the projects matching criteria: {}", criteriaCriteria );
        return projectRepository.count( criteriaCriteria );
    }

    @Override
    public List<ProjectSearchResultDTO> search( ProjectSearchDTO criteria )
    {
        LOG.debug( "Getting the projects matching criteria: {}", criteria );
        return projectRepository.search( criteria );
    }

    @Transactional
    @Override
    public ProjectComponent createComponent( String projectCode, String component, String textColor )
    {
        LOG.debug( "Creating component: name = {}, textColor = {}, project code = {}", component, textColor,
                   projectCode );

        Project project = projectRepository.findByCode( projectCode );

        ProjectComponent projectComponent = new ProjectComponent();
        projectComponent.setBackgroundColor( getOppositeColor( textColor ) );
        projectComponent.setTextColor( textColor );
        projectComponent.setTagValue( component );
        projectComponent.setProject( project );

        return componentRepository.save( projectComponent );
    }

    @Transactional
    @Override
    public ProjectLabel createLabel( String projectCode, String label, String bgColor )
    {
        LOG.debug( "Creating label: name = {}, bgColor = {}, project code = {}", label, bgColor, projectCode );
        Project project = projectRepository.findByCode( projectCode );

        ProjectLabel projectLabel = new ProjectLabel();
        projectLabel.setBackgroundColor( bgColor );
        projectLabel.setTextColor( getOppositeColor( bgColor ) );
        projectLabel.setTagValue( label );
        projectLabel.setProject( project );

        return labelRepository.save( projectLabel );
    }

    @Override
    public List<ProjectComponent> findComponentsByProjectCode( final int projectId )
    {
        return componentRepository.findAllByProject( projectId );
    }

    @Override
    public List<ProjectLabel> findLabelsByProjectId( final int projectId )
    {
        return labelRepository.findAllByProject( projectId );
    }

    @Override
    @Transactional
    public boolean updateComponent( long id, String projectCode, String labelName, String labelColor )
    {
        LOG.debug( "Update component (id = {}, component = {}, color = {} ) IF belongs to Project with code: {}", id,
                   labelName, labelColor, projectCode );
        Project project = projectRepository.findByCode( projectCode );
        ProjectComponent component = componentRepository.findOne( id );
        boolean deleted = false;
        if ( isTagBelongingToProject( component, project ) )
        {
            component.setTagValue( labelName );
            component.setTextColor( labelColor );
            component.setBackgroundColor( getOppositeColor( labelColor ) );
            componentRepository.save( component );
            deleted = true;
        }
        return deleted;
    }

    @Override
    @Transactional
    public boolean updateLabel( long id, String projectCode, String labelName, String labelColor )
    {
        LOG.debug( "Update label (id = {}, label = {}, bg.color = {} ) IF belongs to Project with code: {}", id,
                   labelName, labelColor, projectCode );
        Project project = projectRepository.findByCode( projectCode );
        ProjectLabel label = labelRepository.findOne( id );
        boolean deleted = false;
        if ( isTagBelongingToProject( label, project ) )
        {
            label.setTagValue( labelName );
            label.setBackgroundColor( labelColor );
            label.setTextColor( getOppositeColor( labelColor ) );
            labelRepository.save( label );
            deleted = true;
        }
        return deleted;
    }

    @Override
    @Transactional
    public boolean deleteComponent( long id, String projectCode )
    {
        LOG.debug( "Delete component (id = {} ) IF belongs to Project with code: {}", id, projectCode );
        Project project = projectRepository.findByCode( projectCode );
        ProjectComponent component = componentRepository.findOne( id );
        boolean deleted = false;
        if ( isTagBelongingToProject( component, project ) )
        {
            componentRepository.delete( component );
            deleted = true;
        }
        return deleted;
    }

    @Override
    @Transactional
    public boolean deleteLabel( long id, String projectCode )
    {
        LOG.debug( "Delete label (id = {} ) IF belongs to Project with code: {}", id, projectCode );
        Project project = projectRepository.findByCode( projectCode );
        ProjectLabel label = labelRepository.findOne( id );
        boolean deleted = false;
        if ( isTagBelongingToProject( label, project ) )
        {
            labelRepository.delete( label );
            deleted = false;
        }
        return deleted;
    }

    private String getOppositeColor( String color )
    {
        return ColorUtils.isDarkColor( color ) ? "#FFFFFF" : "#000000";
    }

    /**
     * This method verifies if update is safe.
     * 
     * @param tag the {@link ProjectTag} (component or label) instance to check
     * @param projectId the ID of the project
     * @return true if update is safe
     */
    private static boolean isTagBelongingToProject( ProjectTag tag, Project project )
    {
        return tag != null && project != null && tag.getProject().getId().equals( project.getId() );
    }
}
