/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.util.Map;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dao.UserRepository;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
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
    ProjectManagementServiceImpl( ProjectRepository projectRepository, UserRepository userRepository )
    {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Project create( String name, String code, String description, int creatorUserId,
                           Map<Integer, UserRoleInsideProject> additionalUsers )
    {
        Project project = createBaseProjectInstance( name, code, description );
        project = projectRepository.save( project );
        return project;
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
