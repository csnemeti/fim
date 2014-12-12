package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.model.project.Project;

/**
 * This is the implementation of {@link ProjectRepository}.
 * 
 * @author Balaceanu Sergiu-Denis
 * @author Csaba
 */
@Singleton
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
