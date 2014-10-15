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
 * This annotation is used for injecting e-mail configuration.
 * 
 * @author Nemeti
 */
@BindingAnnotation
@Target( { ElementType.PARAMETER, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
public @interface EmailConfiguration
{

}
