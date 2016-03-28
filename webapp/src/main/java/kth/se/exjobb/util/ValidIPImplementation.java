/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.util;


import com.google.common.net.InetAddresses;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This is a implementation of the ValidIP validation-constraint.
 * @author Kim Hammar
 */

class ValidIPImplementation implements ConstraintValidator <ValidIP, String>
{
    /* Initialize the validator in preparation for isValid() calls */
    @Override
    public void initialize(ValidIP constraintAnnotation)
    {
    }
    /* Implement the validation logic */
    @Override
    public boolean isValid(String value,
            ConstraintValidatorContext context)
    {
        return InetAddresses.isInetAddress(value);
    }
    
}