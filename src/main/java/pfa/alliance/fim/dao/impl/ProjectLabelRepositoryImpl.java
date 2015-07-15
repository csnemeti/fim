/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import javax.inject.Singleton;

import pfa.alliance.fim.dao.ProjectLabelRepository;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.model.project.ProjectLabel;

/**
 * This class represents the implementation for {@link ProjectLabelRepository}.
 * 
 * @author Csaba
 */
@Singleton
class ProjectLabelRepositoryImpl
    extends ProjectTagRepositoryImpl<ProjectLabel>
    implements ProjectLabelRepository
{

    @Override
    protected Sort createDefaultSortCriteria()
    {
        return ( new Sort() ).add( "label", true );
    }

    @Override
    protected Class<ProjectLabel> getEntityClass()
    {
        return ProjectLabel.class;
    }

    @Override
    public boolean deleteLabelBy( final long id, final int projectId )
    {
        return deleteBy( id, projectId );
    }
}
