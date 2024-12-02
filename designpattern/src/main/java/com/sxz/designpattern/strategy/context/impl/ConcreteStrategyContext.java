package com.sxz.designpattern.strategy.context.impl;

import com.sxz.designpattern.context.ClassContext;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.register.StrategyRegister;
import com.sxz.designpattern.strategy.context.StrategyContext;
import com.sxz.designpattern.strategy.handler.DefaultStrategyHandler;
import com.sxz.designpattern.strategy.handler.StrategyHandler;
import com.sxz.designpattern.util.ReflectUtil;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 */
public class ConcreteStrategyContext<X, Y> implements StrategyContext<X, Y> {

    private static final Map<String, StrategyHandler<?, ?>> CONTEXT = new ConcurrentHashMap<>();

    public ConcreteStrategyContext(String packageName){
        try {
            StrategyRegister.initStrategyHandler(packageName);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StrategyHandler<X, Y> build(String id) {
        if (Objects.nonNull(CONTEXT.get(id))) {
            return (StrategyHandler<X, Y>) CONTEXT.get(id);
        }
        Class<?> aClass = ClassContext.get(PatternEnum.STRATEGY, id);
        StrategyHandler<X, Y> handler = null;
        if (Objects.nonNull(aClass)) {
            handler = (StrategyHandler<X, Y>) ReflectUtil.newInstance(aClass);
        }
        MethodObj methodObj = MethodContext.get(PatternEnum.STRATEGY, id);
        if (Objects.nonNull(methodObj)) {
            handler = buildDefaultHandler(methodObj);
        }
        if (Objects.isNull(handler)) {
            throw new NoSuchElementException("No such policy executor exists.");
        }
        CONTEXT.put(id, handler);
        return handler;
    }

    private StrategyHandler<X, Y> buildDefaultHandler(MethodObj methodObj) {
        return new DefaultStrategyHandler<>(methodObj);
    }
}
