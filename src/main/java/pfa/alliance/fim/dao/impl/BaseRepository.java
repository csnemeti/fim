/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

/**
 * This is a class that can be used as base for repository classes.
 * 
 * @author Csaba
 */
abstract class BaseRepository
{
    /** The entity manager used in the application. */
    private Provider<EntityManager> entityManager;

    /**
     * Sets (by Guice) the {@link EntityManager} for this repository.
     * 
     * @param entityManager the entity manager to use
     */
    @Inject
    void setEntityManager( Provider<EntityManager> entityManager )
    {
        this.entityManager = entityManager;
    }

    /**
     * Gets the {@link EntityManager} instance set in this repository.
     * 
     * @return the entity manager to use
     */
    protected EntityManager getEntityManager()
    {
        return entityManager.get();
    }

}
