package pfa.alliance.fim.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.SolrConnectException;
import pfa.alliance.fim.service.SolrManager;
import pfa.alliance.fim.service.SolrOperationFailedException;

/**
 * This is the implementation of {@link SolrManager}.
 * 
 * @author Csaba
 */
@Singleton
class SolrManagerImpl
    implements SolrManager
{
    private static final Logger LOG = LoggerFactory.getLogger( SolrManagerImpl.class );

    private final String solrUrl;

    private final Map<String, String> dbConfig;

    /** Flag indicating start command was sent to Solr. */
    private static boolean started = false;

    /**
     * Called when instance of this class is created.
     * 
     * @param solrConfig the configuration for Solr
     * @param jpaConfig the configuration for JPA
     */
    @Inject
    SolrManagerImpl( @ServiceConfiguration( ServiceConfigurationType.SOLR ) Properties solrConfig,
                     @ServiceConfiguration( ServiceConfigurationType.JPA ) Properties jpaConfig )
    {
        this.solrUrl = solrConfig.getProperty( "solr.url" );
        // add in the DB config the parameters as expected by Solr filter
        this.dbConfig = new HashMap<>();
        this.dbConfig.put( "driver", jpaConfig.getProperty( "javax.persistence.jdbc.driver" ) );
        this.dbConfig.put( "url", jpaConfig.getProperty( "javax.persistence.jdbc.url" ) );
        this.dbConfig.put( "username", jpaConfig.getProperty( "javax.persistence.jdbc.user" ) );
        this.dbConfig.put( "password", jpaConfig.getProperty( "javax.persistence.jdbc.password" ) );
    }

    @Override
    public void initDb()
        throws SolrOperationFailedException, SolrConnectException
    {
        String params = buildInitParameters();
        try
        {
            String response = makeInitCall( params );
            JSONObject responseJSon = parseResonse( response );
            if ( "ERROR".equalsIgnoreCase( responseJSon.getString( "status" ) ) )
            {
                LOG.error( "Solr#init respond with error: {}", response );
                throw new SolrOperationFailedException( "Solr respond with error on init: "
                    + responseJSon.getString( "message" ) );
            }
        }
        catch ( MalformedURLException | ProtocolException | ConnectException e )
        {
            LOG.error( "Error while trying to call init on Solr server", e );
            throw new SolrConnectException( "Could not contact Solr server", e );
        }
        catch ( IOException e )
        {
            LOG.error( "Error while trying to init Solr server", e );
            throw new SolrOperationFailedException( "Error while calling Solr#init", e );
        }
    }

    /**
     * Build the parameters for Solr#init.
     * 
     * @return the built parameters
     */
    private String buildInitParameters()
    {
        String separator = "";
        StringBuilder sb = new StringBuilder();
        for ( Map.Entry<String, String> entry : this.dbConfig.entrySet() )
        {
            sb.append( separator ).append( entry.getKey() ).append( "=" ).append( entry.getValue() );
            separator = "&";
        }
        return sb.toString();
    }

    /**
     * Make a call for initialize the Solr filter and returns the response.
     * 
     * @param urlParameters the parameters for init
     * @return the Solr response of null if call was a HTTP error
     * @throws IOException in case of error
     */
    private String makeInitCall( String urlParameters )
        throws IOException
    {
        URL url = new URL( this.solrUrl + "/fim-solr/initdb" );
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod( "POST" );
        con.setRequestProperty( "User-Agent", "F.I.M. User Agent" );
        con.setRequestProperty( "Accept-Language", "en-US,en;q=0.5" );

        con.setDoOutput( true );
        try (DataOutputStream wr = new DataOutputStream( con.getOutputStream() ))
        {
            wr.writeBytes( urlParameters );
            wr.flush();
        }
        LOG.debug( "Performing a POST call to {}, with parameters: {}", url, urlParameters );

        return processResponse( con );
    }

    @Override
    public void start()
        throws SolrOperationFailedException, SolrConnectException
    {
        String url = this.solrUrl + "/fim-solr/start";
        try
        {
            String response = makeGetCall( url );
            JSONObject responseJSon = parseResonse( response );
            if ( "ERROR".equalsIgnoreCase( responseJSon.getString( "status" ) ) )
            {
                LOG.error( "Solr#start respond with error: {}", response );

                throw new SolrOperationFailedException( "Solr respond with error on start: "
                    + responseJSon.getString( "message" ) );
            }
            started = true;
        }
        catch ( MalformedURLException | ProtocolException | ConnectException e )
        {
            LOG.error( "Error while trying to call start on Solr server", e );
            throw new SolrConnectException( "Could not contact Solr server", e );
        }
        catch ( IOException e )
        {
            LOG.error( "Error while trying to start Solr server", e );
            throw new SolrOperationFailedException( "Error while calling Solr#start", e );
        }
        // start indexing
        ( new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    runUserFullIndex();
                    runActiveUserFullIndex();
                }
                catch ( Exception e )
                {
                    LOG.error( "Error while running index after start", e );
                }
            }
        } ).start();
    }

    @Override
    public void runUserFullIndex()
        throws SolrOperationFailedException, SolrConnectException
    {
        String url = this.solrUrl + "/users/dataimport?command=full-import&wt=json";
        runIndex( url );
    }

    @Override
    public void runUserDeltaIndex()
        throws SolrOperationFailedException, SolrConnectException
    {
        String url = this.solrUrl + "/users/dataimport?command=full-import&wt=json";
        runIndex( url );
    }

    @Override
    public void runQuiteUserDeltaIndexInSeparateThread()
    {
        ( new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    runUserDeltaIndex();
                }
                catch ( SolrOperationFailedException | SolrConnectException e )
                {
                    LOG.error( "Could not send DELTA index command for Users", e );
                }
            }
        } ).start();
    }

    @Override
    public void runActiveUserFullIndex()
        throws SolrOperationFailedException, SolrConnectException
    {
        String url = this.solrUrl + "/active_users/dataimport?command=full-import&wt=json";
        runIndex( url );
    }

    @Override
    public void runActiveUserDeltaIndex()
        throws SolrOperationFailedException, SolrConnectException
    {
        String url = this.solrUrl + "/active_users/dataimport?command=full-import&wt=json";
        runIndex( url );
    }

    /**
     * Runs an Solr core index.
     * 
     * @param url the URL where the call must be made
     * @throws SolrOperationFailedException in case the call failed
     * @throws SolrConnectException in case it cannot connect to Solr server
     */
    private void runIndex( String url )
        throws SolrOperationFailedException, SolrConnectException
    {
        if ( !started )
        {
            return;
        }
        try
        {
            String response = makeGetCall( url );
            LOG.debug( "Index response: {}", response );
            JSONObject responseJSon = parseResonse( response );
            // if ( responseJSon == null || "ERROR".equalsIgnoreCase( responseJSon.getString( "status" ) ) )
            // {
            // LOG.error( "Solr#index respond with error: {}", response );
            // String message = ( responseJSon == null ) ? "<<null>>" : responseJSon.getString( "message" );
            // throw new SolrOperationFailedException( "Solr respond with error on index: " + message );
            // }
        }
        catch ( MalformedURLException | ProtocolException | ConnectException e )
        {
            LOG.error( "Error while trying to call Solr server with URL {}", url, e );
            throw new SolrConnectException( "Could not contact Solr server", e );
        }
        catch ( IOException e )
        {
            LOG.error( "Error while trying to call Solr server with URL {}", url, e );
            throw new SolrOperationFailedException( "Error while calling Solr#index", e );
        }
    }

    /**
     * Performs a GET call.
     * 
     * @param url the URL to call
     * @return the processed response
     * @throws IOException in case of error
     */
    private String makeGetCall( String url )
        throws IOException
    {
        URL urlObject = new URL( url );
        HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();

        con.setRequestMethod( "GET" );
        con.setRequestProperty( "User-Agent", "F.I.M. User Agent" );
        con.setRequestProperty( "Accept-Language", "en-US,en;q=0.5" );
        LOG.debug( "Performing a GET call to {}", url );

        return processResponse( con );
    }

    /**
     * Process a Solr response.
     * 
     * @param con the connection to Solr
     * @return the response from server
     * @throws IOException in case of error
     */
    private static String processResponse( HttpURLConnection con )
        throws IOException
    {
        int responseCode = con.getResponseCode();
        String result = null;
        if ( responseCode < 300 )
        {
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) ))
            {
                String inputLine;
                while ( ( inputLine = in.readLine() ) != null )
                {
                    response.append( inputLine );
                }
            }
            result = response.toString();
        }
        LOG.debug( "Receiving response HTTP Code {}, response: {}", responseCode, result );

        return result;
    }

    /**
     * Parse a JSon response send by Solr server.
     * 
     * @param response the response sent by server
     * @return the response or null if original response was blank or parsing failed
     */
    private static JSONObject parseResonse( String response )
    {
        JSONObject object = null;
        if ( StringUtils.isNotBlank( response ) )
        {
            try
            {
                object = new JSONObject( response );
            }
            catch ( Exception e )
            {
                LOG.error( "Error while parsing response JSon {}", response, e );
            }
        }
        return object;
    }
}
