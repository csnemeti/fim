/**
 * 
 */
package pfa.alliance.fim.service;

import java.util.List;

import pfa.alliance.fim.dto.UserDTO;

/**
 * This interface defines methods used for interacting with a Search server (like Lucene, Solr, ElasticSearch, etc.).
 * 
 * @author Csaba
 */
public interface SearchService
{
    /**
     * Finds active users that are not assigned to project with given ID.
     * 
     * @param projectId the ID of the project
     * @param contains the text the user should contain
     * @return a not-null {@link List} of users
     */
    List<UserDTO> findActiveUsersNotAssignedToProject( int projectId, String contains );
}
