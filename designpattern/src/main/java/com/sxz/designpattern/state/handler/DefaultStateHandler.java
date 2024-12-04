package com.sxz.designpattern.state.handler;

import com.sxz.designpattern.context.MethodObj;

import java.lang.reflect.InvocationTargetException;

/**
 * @author suxz
 **/
public class DefaultStateHandler implements StateHandler {

    private DefaultStateHandler previousHandler;

    private DefaultStateHandler nextHandler;

    private MethodObj methodObj;

    public void setPreviousHandler(DefaultStateHandler previousHandler) {
        this.previousHandler = previousHandler;
    }

    public void setNextHandler(DefaultStateHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void setMethodObj(MethodObj methodObj) {
        methodObj.getMethod().setAccessible(true);
        this.methodObj = methodObj;
    }

    public MethodObj getMethodObj() {
        return methodObj;
    }

    @Override
    public void current() {
        try {
            methodObj.getMethod().invoke(methodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void next() {
        MethodObj nextMethodObj = this.nextHandler.getMethodObj();
        try {
            nextMethodObj.getMethod().invoke(nextMethodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void previous() {
        MethodObj previousMethodObj = this.previousHandler.getMethodObj();
        try {
            previousMethodObj.getMethod().invoke(previousMethodObj.getObj());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
