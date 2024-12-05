package com.sxz.designpattern.state.handler;

import com.sxz.designpattern.annotation.State;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.state.context.impl.ConcreteStateContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author suxz
 **/
public class DefaultStateHandler implements StateHandler {

    private MethodObj methodObj;

    private String stateId;

    public DefaultStateHandler(MethodObj methodObj) {
        methodObj.getMethod().setAccessible(true);
        this.stateId = methodObj.getMethod().getAnnotation(State.class).id();
        this.methodObj = methodObj;
    }

    @Override
    public StateHandler handle() {
        try {
            methodObj.getMethod().invoke(methodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public StateHandler next() {
        MethodObj nextMethod = ConcreteStateContext.getNextMethodObjStateId(this.stateId);
        nextMethod.getMethod().setAccessible(true);
        this.stateId = nextMethod.getMethod().getAnnotation(State.class).id();
        this.methodObj = nextMethod;
        return this;
    }

    @Override
    public StateHandler previous() {
        MethodObj previousMethod = ConcreteStateContext.getPreviousMethodObjStateId(this.stateId);
        previousMethod.getMethod().setAccessible(true);
        this.stateId = previousMethod.getMethod().getAnnotation(State.class).id();
        this.methodObj = previousMethod;
        return this;
    }
}
