/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import pfa.alliance.fim.dao.ProjectComponentRepository;
import pfa.alliance.fim.dao.Sort;
import pfa.alliance.fim.model.project.ProjectComponent;

/**
 * This class represents the implementation for {@link ProjectComponentRepository}.
 * 
 * @author Csaba
 */
class ProjectComponentRepositoryImpl
    extends ProjectTagRepositoryImpl<ProjectComponent>
    implements ProjectComponentRepository
{

    @Override
    protected Sort createDefaultSortCriteria()
    {
        return ( new Sort() ).add( "componentName", true );
    }

    @Override
    protected Class<ProjectComponent> getEntityClass()
    {
        return ProjectComponent.class;
    }

}
