/**
 * 
 */
package pfa.alliance.fim.service.impl;

import javax.persistence.PersistenceException;

/**
 * Utility class for repositories.
 * 
 * @author Csaba
 */
final class RepositoryUtil
{
    private RepositoryUtil()
    {
        throw new IllegalStateException( "Utility class, doesn;t require instance" );
    }

    /**
     * Checks if the exception represents a duplicate record exception.
     * 
     * @param e the exception we received
     * @return true if information is duplicate record related exception
     */
    public static boolean isDuplicateUserInfoRelatedException( PersistenceException e )
    {
        Throwable t = e;
        boolean found = false;
        do
        {
            String message = t.getMessage();
            if ( message.contains( "violates unique constraint" ) )
            {
                found = true;
            }
            t = t.getCause();
        }
        while ( t != null && !found );
        return found;
    }

}
