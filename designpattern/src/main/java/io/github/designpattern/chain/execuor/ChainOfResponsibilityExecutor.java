package io.github.designpattern.chain.execuor;

/**
 * @author suxz
 */
public interface ChainOfResponsibilityExecutor<T> {

    /**
     * Execute the chain of responsibility.
     */
    void execute();

    /**
     * Execute the chain of responsibility. Parameters can be passed in, but must be consistent with the parameters received by each processor.
     */
    void execute(T arg);

}
