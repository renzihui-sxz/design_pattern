package com.sxz.designpattern.annotation;

import java.lang.annotation.*;

/**
 * @author suxz
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface State {

    /**
     * The status actuator id must be unique.
     */
    String id();

    /**
     * The group id identifies the group to which the status actuator belongs. The id must.
     */
    String groupId();

    /**
     * Specifies the order of the state actuators, the smaller the earlier. By default, it executes last.
     */
    int order() default Integer.MAX_VALUE;

}
