package io.github.designpattern.chain.context;

import io.github.designpattern.chain.execuor.ChainOfResponsibilityExecutor;

/**
 * @author suxz
 */
public interface ChainOfResponsibilityContext<T> {

    /**
     * Build a specific chain of responsibility based on id.
     *
     * @return Return to the specific chain of responsibility.
     */
    ChainOfResponsibilityExecutor<T> build(String id);

}
