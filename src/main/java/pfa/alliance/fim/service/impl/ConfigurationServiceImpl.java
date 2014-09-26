package pfa.alliance.fim.service.impl;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pfa.alliance.fim.service.ConfigurationService;

/**
 * Implementation for {@link ConfigurationService}
 * @author Balaceanu Sergiu-Denis
 *
 */
@Singleton
class ConfigurationServiceImpl
    implements ConfigurationService
{
    /** The logger used in this class. */
    private static final Logger LOG = LoggerFactory.getLogger( ConfigurationServiceImpl.class );

  public ConfigurationServiceImpl() {}

  @Override
  public boolean isConfigurationCompleted() {
    // TODO: replace the mocked value with real configuration status
    return true;
  }
}
