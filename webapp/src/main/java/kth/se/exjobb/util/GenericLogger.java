/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/

package kth.se.exjobb.util;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 * Logger interface for generic logging.
 * @author Kim Hammar
 */

@InterceptorBinding
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface GenericLogger {
}
