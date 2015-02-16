/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.UserDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.service.EmailGeneratorService;
import pfa.alliance.fim.service.EmailService;
import pfa.alliance.fim.service.EmailType;
import pfa.alliance.fim.service.FimUrlGeneratorService;
import pfa.alliance.fim.service.ProjectManagementService;

import com.google.inject.persist.Transactional;

/**
 * This is the implementation of {@link ProjectManagementService}
 * 
 * @author Csaba
 */
public class ProjectManagementServiceImpl
    implements ProjectManagementService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ProjectManagementServiceImpl.class );

    /** The Project repository to use in this class. */
    private final ProjectRepository projectRepository;

    /** The User repository to use in this class. */
    private final UserRepository userRepository;

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
     * @param emailService the instance of service used for sending e-mails
     * @param emailGeneratorService the instance of service used for generating e-mails
     * @param fimUrlGeneratorService the instance of service used for generating full URLs inside FIM
     */
    @Inject
    ProjectManagementServiceImpl( ProjectRepository projectRepository, UserRepository userRepository,
                                  EmailService emailService, EmailGeneratorService emailGeneratorService,
                                  FimUrlGeneratorService fimUrlGeneratorService )
    {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;

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
        }
        return projectDTO;
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
}
