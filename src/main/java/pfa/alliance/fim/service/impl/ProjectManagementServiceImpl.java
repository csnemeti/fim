/**
 * 
 */
package pfa.alliance.fim.service.impl;

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
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
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

    /**
     * Called when instance of this class is created.
     * 
     * @param projectRepository the instance of Project repository to use in this class
     * @param userRepository the instance of User repository to use in this class
     */
    @Inject
    ProjectManagementServiceImpl( ProjectRepository projectRepository, UserRepository userRepository )
    {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Project create( String name, String code, String description, int creatorUserId,
                           Map<Integer, UserRoleInsideProject> additionalUsers, Locale locale )
    {
        Project project = createBaseProjectInstance( name, code, description );
        try
        {
            UserProjectRelation owner = addToProject( project, creatorUserId, UserRoleInsideProject.OWNER );
            project = projectRepository.save( project );
            // project = projectRepository.save( project );
            // TODO send e-mail about project creation to owner
        }
        catch ( PersistenceException e )
        {
            if ( isDuplicateUserInfoRelatedException( e ) )
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
     * Checks if the user data that is provided is duplicate or not.
     * 
     * @param e the exception we received
     * @return true if information about duplicate e-mail or username was found
     */
    private boolean isDuplicateUserInfoRelatedException( PersistenceException e )
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
     * @return the created Project instance
     */
    private static Project createBaseProjectInstance( String name, String code, String description )
    {
        Project project = new Project();
        project.setName( name );
        project.setCode( code );
        project.setDescription( description );
        return project;
    }

}
