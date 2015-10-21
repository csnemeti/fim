package pfa.alliance.fim.dao.impl;

import static pfa.alliance.fim.dao.impl.DaoUtil.uniqueResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import pfa.alliance.fim.dao.ProjectRepository;
import pfa.alliance.fim.dto.ProjectDTO;
import pfa.alliance.fim.dto.ProjectSearchDTO;
import pfa.alliance.fim.dto.ProjectSearchResultDTO;
import pfa.alliance.fim.model.project.Project;
import pfa.alliance.fim.model.project.ProjectState;
import pfa.alliance.fim.model.project.UserProjectRelation;
import pfa.alliance.fim.model.project.UserRoleInsideProject;
import pfa.alliance.fim.model.user.User;
import pfa.alliance.fim.model.user.UserStatus;

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
        whereList.add( cb.notEqual( root.get( "state" ), ProjectState.SCHEDULED_FOR_DELETE ) );
        if ( !searchCriteria.showHidden() )
        {
            whereList.add( cb.equal( root.get( "hidden" ), Boolean.FALSE ) );
        }
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
        if ( ArrayUtils.isNotEmpty( states ) )
        {
            whereList.add( root.get( "state" ).in( Arrays.asList( states ) ) );
        }
        criteria.where( whereList.toArray( new Predicate[whereList.size()] ) );
    }

    @Override
    public List<? extends ProjectDTO> getProjectsSummary( int assignedUserId, UserRoleInsideProject[] roles,
                                                          ProjectState[] allowedStates )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // TODO we should use projection here but Batoo has a problem with String -> enum convertion
        // CriteriaQuery<UserSearchResultDTO> countCriteria = cb.createQuery( UserSearchResultDTO.class );
        CriteriaQuery<UserProjectRelation> criteria = cb.createQuery( UserProjectRelation.class );
        Root<UserProjectRelation> root = criteria.from( UserProjectRelation.class );
        Path<?> user = root.get( "user" );
        Path<?> project = root.get( "project" );

        // add where constraints
        List<Predicate> whereList = new ArrayList<Predicate>();
        whereList.add( cb.equal( user.get( "id" ), assignedUserId ) );
        if ( ArrayUtils.isNotEmpty( allowedStates ) )
        {
            whereList.add( project.get( "state" ).in( buildNameList( allowedStates ) ) );
        }
        if ( ArrayUtils.isNotEmpty( roles ) )
        {
            whereList.add( root.get( "userRole" ).in( buildNameList( roles ) ) );
        }
        criteria.where( whereList.toArray( new Predicate[whereList.size()] ) );

        // add ordering
        criteria.orderBy( cb.asc( project.get( "code" ) ) );

        TypedQuery<UserProjectRelation> query = em.createQuery( criteria );

        List<UserProjectRelation> result = query.getResultList();
        return convertToProjectSearchResultDTO( result );
    }

    private static List<String> buildNameList( Enum<?>[] values )
    {
        List<String> nameList = new ArrayList<>();
        for ( Enum<?> enumValue : values )
        {
            nameList.add( enumValue.name() );
        }
        return nameList;
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
                result.add( buildProjectSearchResultDTO( project, firstItemIndexInTotalResults, i ) );
            }
        }
        return result;
    }

    /**
     * Builds a {@link List} of {@link ProjectSearchResultDTO} from {@link List} of {@link Project}s.
     * 
     * @param projects the {@link List} of {@link Project}s read from DB.
     * @param firstItemIndexInTotalResults the index of the first item in total results
     * @return the list of projects converted to DTOs or an empty {@link List} if original parameter is null or empty
     *         string
     */
    private static List<ProjectSearchResultDTO> convertToProjectSearchResultDTO( List<UserProjectRelation> relations )
    {
        List<ProjectSearchResultDTO> result = new ArrayList<>();
        if ( CollectionUtils.isNotEmpty( relations ) )
        {
            final int totalProjects = relations.size();
            for ( int i = 0; i < totalProjects; i++ )
            {
                Project project = relations.get( i ).getProject();
                result.add( buildProjectSearchResultDTO( project, 0, i ) );
            }
        }
        return result;
    }

    private static ProjectSearchResultDTO buildProjectSearchResultDTO( Project project,
                                                                       final int firstItemIndexInTotalResults,
                                                                       final int index )
    {
        ProjectSearchResultDTO dto = new ProjectSearchResultDTO();
        dto.setId( project.getId() );
        dto.setCode( project.getCode() );
        dto.setName( project.getName() );
        dto.setState( project.getState() );
        dto.setCreateAt( project.getCreatedAt() );
        dto.setIndexInCurrentResults( index );
        dto.setIndexInTotalResults( firstItemIndexInTotalResults + index );
        return dto;
    }

    @Override
    public Map<UserRoleInsideProject, List<User>> findUsersOnProject( int projectId, int maxUsersPerRole,
                                                                      UserRoleInsideProject... roles )
    {
        Map<UserRoleInsideProject, List<User>> result = new HashMap<>();
        for ( UserRoleInsideProject role : roles )
        {
            List<UserProjectRelation> relations =getUsersForProjectWithRole( projectId, maxUsersPerRole, role );
            List<User> users = new ArrayList<>();
            for ( UserProjectRelation relation : relations )
            {
                users.add( relation.getUser() );
            }
            result.put( role, users );
        }
        return result;
    }

    private List<UserProjectRelation> getUsersForProjectWithRole( int projectId, int maxUsersPerRole,
                                                                  UserRoleInsideProject role )
    {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserProjectRelation> criteria = cb.createQuery( UserProjectRelation.class );
        Root<UserProjectRelation> root = criteria.from( UserProjectRelation.class );
        // Fetch<?, ?> user = root.fetch( "user", JoinType.INNER );
        // Path<?> user = root.get( "user" );
        Join<?, ?> user = root.join( "user", JoinType.INNER );
        Path<?> project = root.get( "project" );

        criteria.where( cb.equal( user.get( "status" ), UserStatus.ACTIVE ), cb.equal( root.get( "userRole" ), role ),
                        cb.equal( project.get( "id" ), projectId ) );

        TypedQuery<UserProjectRelation> query = em.createQuery( criteria );
        return query.getResultList();

    }
}
