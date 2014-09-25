/**
 * 
 */
package pfa.alliance.fim.model;

import java.io.Serializable;

/**
 * Interface used for entities that can be simply identified through one column Primary Key.
 * 
 * @author Csaba
 */
public interface Identifiable<ID extends Serializable>
{
    /**
     * Gets the ID of the entity.
     * 
     * @return the ID of the entity, null if entity has not received a Primary Key yet
     */
    ID getId();

    /**
     * Sets the ID of the entity.
     * 
     * @param id the ID this entity should have
     */
    void setId( ID id );
}
