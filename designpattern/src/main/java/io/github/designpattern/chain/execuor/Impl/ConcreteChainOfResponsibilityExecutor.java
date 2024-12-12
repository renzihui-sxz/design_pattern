package io.github.designpattern.chain.execuor.Impl;

import io.github.designpattern.chain.execuor.ChainOfResponsibilityExecutor;
import io.github.designpattern.chain.handler.ChainOfResponsibilityHandler;

import java.util.Objects;

/**
 * @author suxz
 */
public class ConcreteChainOfResponsibilityExecutor<T> implements ChainOfResponsibilityExecutor<T> {

    private ChainOfResponsibilityHandler<T> chainOfResponsibilityHandler;

    private ChainOfResponsibilityExecutor<T> nextExecutor;

    public void setChainOfResponsibilityHandler(ChainOfResponsibilityHandler<T> chainOfResponsibilityHandler) {
        this.chainOfResponsibilityHandler = chainOfResponsibilityHandler;
    }

    public void setNextExecutor(ChainOfResponsibilityExecutor<T> nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public void execute() {
        chainOfResponsibilityHandler.handle();
        if (Objects.nonNull(nextExecutor)) {
            nextExecutor.execute();
        }
    }

    @Override
    public void execute(T arg) {
        chainOfResponsibilityHandler.handle(arg);
        if (Objects.nonNull(nextExecutor)) {
            nextExecutor.execute(arg);
        }
    }
}
