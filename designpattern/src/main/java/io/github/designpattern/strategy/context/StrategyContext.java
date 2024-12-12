package io.github.designpattern.strategy.context;

import io.github.designpattern.strategy.handler.StrategyHandler;

/**
 * @author suxz
 */
public interface StrategyContext<X, Y> {

    /**
     * Build concrete policy classes.
     *
     * @return Returns the policy processor.
     */
    StrategyHandler<X, Y> build(String id);
}
