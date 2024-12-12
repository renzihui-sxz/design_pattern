package io.github.designpattern.strategy.handler;

import io.github.designpattern.context.MethodObj;

import java.lang.reflect.InvocationTargetException;

/**
 * @author suxz
 */
public class DefaultStrategyHandler<X, Y> implements StrategyHandler<X, Y> {

    private final MethodObj methodObj;

    public DefaultStrategyHandler(MethodObj methodObj) {
        methodObj.getMethod().setAccessible(true);
        this.methodObj = methodObj;
    }

    @Override
    public void execute() {
        try {
            methodObj.getMethod().invoke(methodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(X arg) {
        try {
            methodObj.getMethod().invoke(methodObj.getObj(), arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Y executeAndReturnResults() {
        try {
            return (Y) methodObj.getMethod().invoke(methodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Y executeAndReturnResults(X arg) {
        try {
            return (Y) methodObj.getMethod().invoke(methodObj.getObj(), arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
