package com.sxz.designpattern.state.context.impl;

import com.sxz.designpattern.annotation.State;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.register.ChainOfResponsibilityRegister;
import com.sxz.designpattern.register.StateRegister;
import com.sxz.designpattern.state.context.StateContext;
import com.sxz.designpattern.state.handler.ConcreteStateGroupHandler;
import com.sxz.designpattern.state.handler.DefaultStateHandler;
import com.sxz.designpattern.state.handler.StateGroupHandler;
import com.sxz.designpattern.state.handler.StateHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 */
public class ConcreteStateContext implements StateContext {

    private static final Map<String, StateGroupHandler> GROUP_CONTEXT = new ConcurrentHashMap<>();

    private static final Map<String, StateHandler> STATE_CONTEXT = new ConcurrentHashMap<>();

    public ConcreteStateContext() {
    }

    public ConcreteStateContext(String packageName) {
        try {
            StateRegister.initStateHandler(packageName);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StateGroupHandler buildGroup(String groupId) {
        StateGroupHandler groupHandler = GROUP_CONTEXT.get(groupId);
        if (Objects.nonNull(groupHandler)) {
            return groupHandler;
        }
        List<MethodObj> groupMethodObjs = getGroupMethodObjs(groupId);
        ConcreteStateGroupHandler concreteStateGroupHandler = new ConcreteStateGroupHandler();
        String minId = groupMethodObjs.get(0).getMethod().getAnnotation(State.class).id();
        String maxId = groupMethodObjs.get(groupMethodObjs.size() - 1).getMethod().getAnnotation(State.class).id();
        concreteStateGroupHandler.setMinId(minId);
        concreteStateGroupHandler.setMaxId(maxId);
        DefaultStateHandler currentHandler = new DefaultStateHandler();
        currentHandler.setPreviousHandler(previousHandler);
        currentHandler.setMethodObj(groupInfos.get(current));
        concreteStateGroupHandler.put(stateId, getMethodStateHandler(stateId, groupMethodObjs));
        GROUP_CONTEXT.put(groupId, concreteStateGroupHandler);
        return concreteStateGroupHandler;
    }

    @Override
    public StateHandler buildState(String stateId) {
        StateHandler stateHandler = STATE_CONTEXT.get(stateId);
        if (Objects.nonNull(stateHandler)) {
            return stateHandler;
        }
        MethodObj methodObj = getMethodObjByStateId(stateId);
        String groupId = methodObj.getMethod().getAnnotation(State.class).groupId();
        List<MethodObj> groupInfos = getGroupMethodObjs(groupId);
        DefaultStateHandler currentHandler = new DefaultStateHandler();
        currentHandler.setPreviousHandler(previousHandler);
        currentHandler.setMethodObj(methodObj);
        stateHandler = getMethodStateHandler(stateId, groupInfos);
        STATE_CONTEXT.put(stateId, stateHandler);
        return stateHandler;
    }

    private List<MethodObj> getGroupMethodObjs(String groupId) {
        List<MethodObj> groupInfos = (List<MethodObj>) MethodContext.list(PatternEnum.STATE, groupId);
        if (Objects.isNull(groupInfos) || groupInfos.isEmpty()) {
            throw new NoSuchElementException("There is no such state actuator.");
        }
        sortMethodObjs(groupInfos);
        return groupInfos;
    }

    private static void sortMethodObjs(List<MethodObj> methodObjs) {
        methodObjs.sort(Comparator.comparingInt((MethodObj o) -> o.getMethod().getAnnotation(State.class).order()));
    }

    public static MethodObj getMethodObjByStateId(String stateId) {
        Map<Object, Collection<MethodObj>> groups = MethodContext.mapOfCollection(PatternEnum.STATE);
        if (Objects.isNull(groups) || groups.isEmpty()) {
            throw new NoSuchElementException("There is no such state actuator.");
        }
        for (Map.Entry<Object, Collection<MethodObj>> entry : groups.entrySet()) {
            MethodObj methodObj = entry.getValue().stream()
                    .filter(x -> stateId.equals(x.getMethod().getAnnotation(State.class).id()))
                    .findFirst().orElse(null);
            if (Objects.nonNull(methodObj)) {
                return methodObj;
            }
        }
        throw new NoSuchElementException("There is no such state actuator.");
    }

    public static DefaultStateHandler getNextMethodStateHandler(DefaultStateHandler previousHandler, List<MethodObj> groupInfos, int previousIndex) {
        if (previousIndex == groupInfos.size() - 1) {
            return null;
        }
        int current = previousIndex + 1;
        DefaultStateHandler currentHandler = new DefaultStateHandler();
        currentHandler.setPreviousHandler(previousHandler);
        currentHandler.setMethodObj(groupInfos.get(current));
        DefaultStateHandler nextHandler = getMethodStateHandler(currentHandler, groupInfos, current);
        currentHandler.setNextHandler(nextHandler);
        return currentHandler;
    }

    public static DefaultStateHandler getPreviousMethodStateHandler(DefaultStateHandler previousHandler, List<MethodObj> groupInfos, int previousIndex) {
        if (previousIndex == groupInfos.size() - 1) {
            return null;
        }
        int current = previousIndex + 1;
        DefaultStateHandler currentHandler = new DefaultStateHandler();
        currentHandler.setPreviousHandler(previousHandler);
        currentHandler.setMethodObj(groupInfos.get(current));
        DefaultStateHandler nextHandler = getMethodStateHandler(currentHandler, groupInfos, current);
        currentHandler.setNextHandler(nextHandler);
        return currentHandler;
    }
}
