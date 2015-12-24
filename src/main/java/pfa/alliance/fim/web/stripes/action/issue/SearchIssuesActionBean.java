/**
 * 
 */
package pfa.alliance.fim.web.stripes.action.issue;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.stripes.action.UrlBinding;
import pfa.alliance.fim.dto.AbstractSearchDTO;
import pfa.alliance.fim.web.common.FimPageURLs;
import pfa.alliance.fim.web.security.FimSecurity;
import pfa.alliance.fim.web.stripes.action.AbstractDatatablesSearchActionBean;

/**
 * Issue search action bean.
 * 
 * @author Csaba
 */
@UrlBinding( "/issue/search" )
@FimSecurity( )
public class SearchIssuesActionBean
    extends AbstractDatatablesSearchActionBean
{

    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( SearchIssuesActionBean.class );

    @Inject
    public SearchIssuesActionBean()
    {
        super( FimPageURLs.ISSUE_SEARCH_JSP );
        LOG.debug( "New instance created..." );
    }

    @Override
    protected AbstractSearchDTO getSearchObject()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
