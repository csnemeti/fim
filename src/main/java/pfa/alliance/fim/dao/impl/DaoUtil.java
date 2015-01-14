/**
 * 
 */
package pfa.alliance.fim.dao.impl;

import java.util.List;

import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class with utility methods for DAO.
 * 
 * @author Csaba
 */
public final class DaoUtil
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( DaoUtil.class );
    /**
     * Hidden constructor.
     */
    private DaoUtil(){
        throw new IllegalStateException( "Utility class, doesn't need constructor call" );
    }

    /**
     * Handle unique result.
     * 
     * @param results the list of results
     * @return the result or null if {@link List} is empty
     */
    static <T> T uniqueResult( List<T> results )
    {
        T result = null;
        switch ( results.size() )
        {
            case 0:
                result = null;
                break;
            case 1:
                result = results.get( 0 );
                break;
            default:
                NonUniqueResultException ex = new NonUniqueResultException( results.size() + " > 1" );
                LOG.error( "Got too many results for a unique result", ex );
                throw ex;
        }
        return result;
    }

}
