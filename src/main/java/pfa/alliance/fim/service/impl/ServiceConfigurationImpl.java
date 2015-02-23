/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

/**
 * Implementation for {@link ServiceConfiguration} annotation. See:
 * https://github.com/google/guice/wiki/BindingAnnotations
 * 
 * @author Csaba
 */
public class ServiceConfigurationImpl
    implements ServiceConfiguration, Serializable
{
    private static final long serialVersionUID = -1963078148695760016L;

    /** The type of service configuration. */
    private final ServiceConfigurationType value;

    /**
     * Called when instance of this class is created.
     * 
     * @param value the type of service configuration
     */
    public ServiceConfigurationImpl( ServiceConfigurationType value )
    {
        this.value = value;
    }

    @Override
    public Class<? extends Annotation> annotationType()
    {
        return ServiceConfiguration.class;
    }

    @Override
    public ServiceConfigurationType value()
    {
        return value;
    }

    @Override
    public int hashCode()
    {
        // This is specified in java.lang.Annotation.
        return ( 127 * "value".hashCode() ) ^ value.hashCode();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( !( o instanceof ServiceConfiguration ) )
        {
            return false;
        }

        ServiceConfiguration other = (ServiceConfiguration) o;
        return value.equals( other.value() );
    }

    @Override
    public String toString()
    {
        return "@" + ServiceConfiguration.class.getName() + "(value=" + value + ")";
    }

}
