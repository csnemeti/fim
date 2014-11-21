package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.model.project.Project;

/**
 * This is the implementation of {@link ProjectRepository}.
 * 
 * @author Balaceanu Sergiu-Denis
 * @author Csaba
 */
public class ProjectRepositoryImpl
    extends AbstractJpaRepository<Project, Integer>
    implements ProjectRepository
{

    @Override
    protected Class<Project> getEntityClass()
    {
        return Project.class;
    }

}
