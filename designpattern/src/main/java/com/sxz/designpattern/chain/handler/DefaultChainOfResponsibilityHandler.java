package com.sxz.designpattern.chain.handler;

import com.sxz.designpattern.context.MethodObj;

import java.lang.reflect.InvocationTargetException;

/**
 * @author suxz
 */
public class DefaultChainOfResponsibilityHandler<T> implements ChainOfResponsibilityHandler<T> {

    private final MethodObj methodObj;

    public DefaultChainOfResponsibilityHandler(MethodObj methodObj) {
        methodObj.getMethod().setAccessible(true);
        this.methodObj = methodObj;
    }

    @Override
    public void handle() {
        try {
            methodObj.getMethod().invoke(methodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(T arg) {
        try {
            methodObj.getMethod().invoke(methodObj.getObj(), arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
