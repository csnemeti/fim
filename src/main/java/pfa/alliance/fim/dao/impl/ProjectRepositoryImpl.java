package pfa.alliance.fim.dao.impl;

import static pfa.alliance.fim.dao.impl.DaoUtil.uniqueResult;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;

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

    @Override
    protected Class<Integer> getIdClass()
    {
        return Integer.class;
    }

    @Override
    public Project findByCode( String code )
    {
        EntityManager em = getEntityManager();
        TypedQuery<Project> query =
            em.createQuery( "SELECT p FROM pfa.alliance.fim.model.project.Project p WHERE p.code = :code",
                            Project.class );
        query.setParameter( "code", code );
        List<Project> result = query.getResultList();
        return uniqueResult( result );
    }

    @Override
    public User findOwnerForProject( int id )
    {
        EntityManager em = getEntityManager();
        TypedQuery<User> query =
            em.createQuery( "SELECT p.user FROM pfa.alliance.fim.model.project.UserProjectRelation p WHERE p.project.id = :id AND p.userRole = :role",
                            User.class );
        query.setParameter( "id", id );
        query.setParameter( "role", UserRoleInsideProject.OWNER.name() );
        List<User> result = query.getResultList();
        return uniqueResult( result );
    }

}
