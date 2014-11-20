package org.whirlwin;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CompositionProxyEndpoint {

    /**
     * @return the timeout limit in milliseconds for the request.
     */
    long timeout() default 1000;

    /**
     * @return the cache period for the incoming request.
     */
    long cachePeriod() default 0;

    /**
     *
     * @return if the request should fail gracefully or not
     */
    boolean gracefulFailure() default false;
}
