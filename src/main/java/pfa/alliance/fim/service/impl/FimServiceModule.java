/**
 * 
 */
package pfa.alliance.fim.service.impl;

import pfa.alliance.fim.service.ConfigurationService;
import pfa.alliance.fim.service.PersistenceService;

import com.google.inject.AbstractModule;

/**
 * Guice module for Service level
 * 
 * @author Csaba
 */
public class FimServiceModule
    extends AbstractModule
{

    @Override
    protected void configure()
    {
        // bind services
        bind( ConfigurationService.class ).to( ConfigurationServiceImpl.class );
        //bind( PersistenceService.class ).to( DummPersistenceService.class );
        bind( PersistenceService.class ).to( PersistenceServiceImpl.class );
    }

}
