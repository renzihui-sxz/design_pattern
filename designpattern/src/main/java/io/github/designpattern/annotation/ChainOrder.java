package io.github.designpattern.annotation;

import java.lang.annotation.*;

/**
 * @author suxz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ChainOrder {

    /**
     * The responsibility chain id is used to distinguish multiple responsibility chains. The id must.
     */
    String id();

    /**
     * Specifies the order in which the processor executes. By default, it executes last.
     */
    int order() default Integer.MAX_VALUE;

}
