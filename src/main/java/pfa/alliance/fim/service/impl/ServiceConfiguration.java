/**
 * 
 */
package pfa.alliance.fim.service.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * This annotation is used for injecting configuration for some service level related classes. This is similar with
 * {@link javax.inject.Named} with the difference that provides value based on annotation. The available types can be
 * found in {@link ServiceConfigurationType}.
 * 
 * @author Nemeti
 * @see ServiceConfigurationType
 */
@BindingAnnotation
@Target( { ElementType.PARAMETER, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface ServiceConfiguration
{
    /**
     * Gets / Sets the type of service configuration.
     * 
     * @return the type of service configuration
     */
    ServiceConfigurationType value();
}
