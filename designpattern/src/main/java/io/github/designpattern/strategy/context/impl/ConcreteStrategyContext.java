package io.github.designpattern.strategy.context.impl;

import io.github.designpattern.context.ClassContext;
import io.github.designpattern.context.MethodContext;
import io.github.designpattern.context.MethodObj;
import io.github.designpattern.enums.PatternEnum;
import io.github.designpattern.register.StrategyRegister;
import io.github.designpattern.strategy.context.StrategyContext;
import io.github.designpattern.strategy.handler.DefaultStrategyHandler;
import io.github.designpattern.strategy.handler.StrategyHandler;
import io.github.designpattern.util.ReflectUtil;

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

    public ConcreteStrategyContext() {
    }

    static {
        try {
            StrategyRegister.initStrategyHandler();
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
