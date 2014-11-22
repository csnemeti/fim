/**
 * 
 */
package pfa.alliance.fim.web.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Using this annotation you can define security rules for accessing ActionBeans and methods (actions) inside
 * ActionBeans. If at least one method from ActionBean has this annotation, the class must also have it! If a class has
 * this annotation, and no method from class has this annotation, every method inherits the rules from class. If a
 * method has a this annotation and the class has another annotation, when executing the method only rule from method
 * apply.
 * 
 * @author Csaba
 */
@Documented
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD, ElementType.TYPE } )
public @interface FimSecurity
{
    /**
     * Checks if logged in user has ALL {@link Permission}s from the list.
     * 
     * @return the {@link Permission}s user must have
     */
    Permission[] checkIfAll() default {};

    /**
     * Checks if logged in user has ANY {@link Permission}s from the list.
     * 
     * @return the {@link Permission}s user must have at least one
     */
    Permission[] checkIfAny() default {};
}
