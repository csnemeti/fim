package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.user.User;

public class ProjectRepositoryImpl extends AbstractJpaRepository<Project, Integer> implements ProjectRepository {

  @Override
  public User findByName(String username) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected Class<Project> getEntityClass() {
    return Project.class;
  }

}
