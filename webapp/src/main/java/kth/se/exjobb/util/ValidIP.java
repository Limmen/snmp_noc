/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This interface represents a validator-constraint for the bean-validation-framework.
 * @author Kim Hammar
 */

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIPImplementation.class)
public @interface ValidIP
{
    
    /**
     * Message shown when user enters invalid data.
     * @return the error-message when the validation contract is broken
     */
    String message() default "The IP-address you entered is not a valid IPv4 or IPv6 address";
    
    /**
     * Allows specification of validation groups to which this constraint belongs.
     * @return validation groups
     */
    Class<?>[] groups() default {};
    
    /**
     * Can be used by clients of the API to asign custom payload objects to a constraint.
     *
     * @return custom payloads
     */
    Class<? extends Payload>[] payload() default {};
}
