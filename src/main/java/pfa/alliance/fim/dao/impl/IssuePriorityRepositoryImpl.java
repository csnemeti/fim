/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.IssuePriorityRepository;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.model.issue.IssuePriority;

/**
 * This class is the implementation of {@link IssuePriorityRepository}.
 * 
 * @author Csaba
 */
@Singleton
class IssuePriorityRepositoryImpl
    extends AbstractJpaRepository<IssuePriority, String>
    implements IssuePriorityRepository
{

    @Override
    protected Class<IssuePriority> getEntityClass()
    {
        return IssuePriority.class;
    }

    @Override
    protected Class<String> getIdClass()
    {
        return String.class;
    }

    @Override
    public List<IssuePriority> findAll()
    {
        return super.findAll( createDefaultSorting() );
    }

    @Override
    public List<String> findAllIds()
    {
        return super.findAllIds( createDefaultSorting() );
    }

    /**
     * Creates the default ordering that puts the {@link IssuePriority} in a natural order.
     * 
     * @return the default sorting order
     */
    private static Sort createDefaultSorting()
    {
        Sort sort = new Sort();
        sort.add( "order", false );
        return sort;
    }
}
