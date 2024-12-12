package io.github.designpattern.chain.handler;

/**
 * @author suxz
 */
public interface ChainOfResponsibilityHandler<T> {

    /**
     * Functional business implementation.
     */
    void handle();

    /**
     * Functional business implementation.Parameters can be passed in, but must be consistent with the parameters received by each processor.
     */
    void handle(T arg);

}
