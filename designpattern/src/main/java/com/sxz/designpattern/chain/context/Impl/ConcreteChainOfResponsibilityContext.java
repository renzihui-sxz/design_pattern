package com.sxz.designpattern.chain.context.Impl;

import com.sxz.designpattern.annotation.ChainOrder;
import com.sxz.designpattern.chain.context.ChainOfResponsibilityContext;
import com.sxz.designpattern.chain.execuor.ChainOfResponsibilityExecutor;
import com.sxz.designpattern.chain.execuor.Impl.ConcreteChainOfResponsibilityExecutor;
import com.sxz.designpattern.chain.handler.ChainOfResponsibilityHandler;
import com.sxz.designpattern.chain.handler.DefaultChainOfResponsibilityHandler;
import com.sxz.designpattern.context.ClassContext;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.register.ChainOfResponsibilityRegister;
import com.sxz.designpattern.util.ReflectUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 */
public class ConcreteChainOfResponsibilityContext<T> implements ChainOfResponsibilityContext<T> {

    private static final Map<String, ConcreteChainOfResponsibilityExecutor<?>> CONTEXT = new ConcurrentHashMap<>();

    static {
        try {
            ChainOfResponsibilityRegister.initChainOfResponsibilityHandler();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChainOfResponsibilityExecutor<T> build(String id) {
        ConcreteChainOfResponsibilityExecutor<T> executor = (ConcreteChainOfResponsibilityExecutor<T>) CONTEXT.get(id);
        if (Objects.nonNull(executor)) {
            return executor;
        }
        List<ChainOfResponsibilityHandlerContainer<T>> containers = getExecutorById(id);
        if (containers.isEmpty()) {
            throw new NoSuchElementException("There is no chain of responsibility.");
        }
        containers.sort(Comparator.comparingInt(ChainOfResponsibilityHandlerContainer::getOrder));
        executor = getCurrentExecutor(containers, 0);
        CONTEXT.put(id, executor);
        return executor;
    }

    private List<ChainOfResponsibilityHandlerContainer<T>> getExecutorById(String id) {
        List<ChainOfResponsibilityHandlerContainer<T>> containers = new ArrayList<>();
        Collection<Class<?>> classCollection = ClassContext.list(PatternEnum.CHAIN_OF_RESPONSIBILITY, id);
        if (Objects.nonNull(classCollection) && !classCollection.isEmpty()) {
            classCollection.forEach(x -> containers.add(convertToContainer(x)));
        }
        Collection<MethodObj> methodCollection = MethodContext.list(PatternEnum.CHAIN_OF_RESPONSIBILITY, id);
        if (Objects.nonNull(methodCollection) && !methodCollection.isEmpty()) {
            methodCollection.forEach(x -> containers.add(convertToContainer(x)));
        }
        return containers;
    }

    private ChainOfResponsibilityHandlerContainer<T> convertToContainer(MethodObj methodObj) {
        ChainOfResponsibilityHandlerContainer<T> container = new ChainOfResponsibilityHandlerContainer<>();
        ChainOrder annotation = methodObj.getMethod().getAnnotation(ChainOrder.class);
        container.setOrder(annotation.order());
        DefaultChainOfResponsibilityHandler<T> handler = new DefaultChainOfResponsibilityHandler<>(methodObj);
        container.setHandler(handler);
        return container;
    }

    private ChainOfResponsibilityHandlerContainer<T> convertToContainer(Class<?> clazz) {
        ChainOfResponsibilityHandlerContainer<T> container = new ChainOfResponsibilityHandlerContainer<>();
        ChainOrder annotation = clazz.getAnnotation(ChainOrder.class);
        container.setOrder(annotation.order());
        container.setHandler((ChainOfResponsibilityHandler<T>) ReflectUtil.newInstance(clazz));
        return container;
    }

    private ConcreteChainOfResponsibilityExecutor<T> getCurrentExecutor(List<ChainOfResponsibilityHandlerContainer<T>> containers, int index) {
        if (index == containers.size()) {
            return null;
        }
        ConcreteChainOfResponsibilityExecutor<T> executor = new ConcreteChainOfResponsibilityExecutor<>();
        executor.setChainOfResponsibilityHandler(containers.get(index).getHandler());
        executor.setNextExecutor(getCurrentExecutor(containers, ++index));
        return executor;
    }

    static class ChainOfResponsibilityHandlerContainer<T> {

        private int order;

        private ChainOfResponsibilityHandler<T> handler;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public ChainOfResponsibilityHandler<T> getHandler() {
            return handler;
        }

        public void setHandler(ChainOfResponsibilityHandler<T> handler) {
            this.handler = handler;
        }
    }
}