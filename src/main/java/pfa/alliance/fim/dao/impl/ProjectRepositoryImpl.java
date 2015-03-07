package pfa.alliance.fim.dao.impl;

import static pfa.alliance.fim.dao.impl.DaoUtil.uniqueResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
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

    @Override
    public long count( ProjectSearchDTO searchCriteria )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery( Long.class );
        Root<Project> root = criteria.from( Project.class );
        criteria.select( cb.count( root ) );
        // add where constraints
        addWhereFor( cb, criteria, root, searchCriteria );

        TypedQuery<Long> query = em.createQuery( criteria );
        return query.getSingleResult();
    }

    @Override
    public List<ProjectSearchResultDTO> search( ProjectSearchDTO searchCriteria )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // TODO we should use projection here but Batoo has a problem with String -> enum convertion
        // CriteriaQuery<UserSearchResultDTO> countCriteria = cb.createQuery( UserSearchResultDTO.class );
        CriteriaQuery<Project> criteria = cb.createQuery( Project.class );
        Root<Project> root = criteria.from( Project.class );

        // add where constraints
        addWhereFor( cb, criteria, root, searchCriteria );

        // add ordering
        Path<?> orderPath = root.get( searchCriteria.getOrderBy() );
        Order order = ( searchCriteria.isAscending() ) ? cb.asc( orderPath ) : cb.desc( orderPath );
        criteria.orderBy( order, cb.asc( root.get( "id" ) ) );

        TypedQuery<Project> query = em.createQuery( criteria );

        // set pagination constraints
        final int offset = searchCriteria.getStartIndex();
        query.setFirstResult( offset );
        query.setMaxResults( searchCriteria.getItemsPerPage() );

        List<Project> result = query.getResultList();
        return convertToUserSearchResultDTO( result, offset );
    }

    private void addWhereFor( CriteriaBuilder cb, CriteriaQuery<?> criteria, Root<Project> root,
                              ProjectSearchDTO searchCriteria )
    {
        List<Predicate> whereList = new ArrayList<Predicate>();
        String name = searchCriteria.getName();
        if ( StringUtils.isNotBlank( name ) )
        {
            whereList.add( cb.like( cb.lower( root.get( "name" ) ), "%" + name.toLowerCase() + "%" ) );
        }
        String code = searchCriteria.getCode();
        if ( StringUtils.isNotBlank( code ) )
        {
            whereList.add( cb.like( root.get( "code" ), "%" + code.toUpperCase() + "%" ) );
        }
        String[] states = searchCriteria.getStates();
        if ( states != null && states.length > 0 )
        {
            whereList.add( root.get( "state" ).in( Arrays.asList( states ) ) );
        }
        if ( whereList.size() > 0 )
        {
            criteria.where( whereList.toArray( new Predicate[whereList.size()] ) );
        }
    }

    /**
     * Builds a {@link List} of {@link ProjectSearchResultDTO} from {@link List} of {@link Project}s.
     * 
     * @param projects the {@link List} of {@link Project}s read from DB.
     * @param firstItemIndexInTotalResults the index of the first item in total results
     * @return the list of projects converted to DTOs or an empty {@link List} if original parameter is null or empty
     *         string
     */
    private static List<ProjectSearchResultDTO> convertToUserSearchResultDTO( List<Project> projects,
                                                                           final int firstItemIndexInTotalResults )
    {
        List<ProjectSearchResultDTO> result = new ArrayList<>();
        if ( CollectionUtils.isNotEmpty( projects ) )
        {
            final int totalProjects = projects.size();
            for ( int i = 0; i < totalProjects; i++ )
            {
                Project project = projects.get( i );
                ProjectSearchResultDTO dto = new ProjectSearchResultDTO();
                dto.setId( project.getId() );
                dto.setCode( project.getCode() );
                dto.setName( project.getName() );
                dto.setState( project.getState() );
                dto.setCreateAt( project.getCreatedAt() );
                dto.setIndexInCurrentResults( i );
                dto.setIndexInTotalResults( firstItemIndexInTotalResults + i );
                result.add( dto );
            }
        }
        return result;
    }

}
