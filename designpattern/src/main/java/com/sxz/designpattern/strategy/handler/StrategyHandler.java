package com.sxz.designpattern.strategy.handler;

/**
 * @author suxz
 */
public interface StrategyHandler<X, Y> {

    /**
     * No arguments, no results.
     */
    void execute();

    /**
     * There are parameters, but no results are returned.
     */
    void execute(X arg);

    /**
     * No parameters, return results.
     */
    Y executeAndReturnResults();

    /**
     * There are parameters, and there are returns.
     */
    Y executeAndReturnResults(X arg);

}
