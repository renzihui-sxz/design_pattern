package com.sxz.designpattern.annotation;

import java.lang.annotation.*;

/**
 * @author suxz
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Strategy {

    /**
     * The id must be unique.
     */
    String id();

}
