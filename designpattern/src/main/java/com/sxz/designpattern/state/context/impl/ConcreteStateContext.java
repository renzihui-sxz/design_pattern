package com.sxz.designpattern.state.context.impl;

import com.sxz.designpattern.annotation.State;
import com.sxz.designpattern.context.MethodContext;
import com.sxz.designpattern.context.MethodObj;
import com.sxz.designpattern.enums.PatternEnum;
import com.sxz.designpattern.register.StateRegister;
import com.sxz.designpattern.state.context.StateContext;
import com.sxz.designpattern.state.handler.ConcreteStateGroupHandler;
import com.sxz.designpattern.state.handler.DefaultStateHandler;
import com.sxz.designpattern.state.handler.StateGroupHandler;
import com.sxz.designpattern.state.handler.StateHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author suxz
 */
public class ConcreteStateContext implements StateContext {

    public ConcreteStateContext() {
    }

    static {
        try {
            StateRegister.initStateHandler();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StateGroupHandler buildGroup(String groupId) {
        List<MethodObj> groupMethodObjs = getGroupMethodObjs(groupId);
        ConcreteStateGroupHandler concreteStateGroupHandler = new ConcreteStateGroupHandler();
        String minId = groupMethodObjs.get(0).getMethod().getAnnotation(State.class).id();
        String maxId = groupMethodObjs.get(groupMethodObjs.size() - 1).getMethod().getAnnotation(State.class).id();
        concreteStateGroupHandler.setMinId(minId);
        concreteStateGroupHandler.setMaxId(maxId);
        for (MethodObj methodObj : groupMethodObjs) {
            String stateId = methodObj.getMethod().getAnnotation(State.class).id();
            DefaultStateHandler handler = getDefaultStateHandler(methodObj);
            concreteStateGroupHandler.put(stateId, handler);
        }
        return concreteStateGroupHandler;
    }

    private static DefaultStateHandler getDefaultStateHandler(MethodObj methodObj) {
        return new DefaultStateHandler(methodObj);
    }

    @Override
    public StateHandler buildState(String stateId) {
        MethodObj methodObj = getMethodObjByStateId(stateId);
        return new DefaultStateHandler(methodObj);
    }

    private static List<MethodObj> getGroupMethodObjs(String groupId) {
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

    public static MethodObj getNextMethodObjStateId(String stateId) {
        List<MethodObj> group = getGroupByStateId(stateId);
        for (int current = 0; current < group.size(); current++) {
            String targetId = group.get(current).getMethod().getAnnotation(State.class).id();
            if (stateId.equals(targetId) && current < group.size() - 1) {
                return group.get(++current);
            }
        }
        throw new NoSuchElementException("There is no next state handler.");
    }

    public static MethodObj getPreviousMethodObjStateId(String stateId) {
        List<MethodObj> group = getGroupByStateId(stateId);
        for (int current = 0; current < group.size(); current++) {
            String targetId = group.get(current).getMethod().getAnnotation(State.class).id();
            if (stateId.equals(targetId) && current > 0) {
                return group.get(--current);
            }
        }
        throw new NoSuchElementException("There is no previous state handler.");
    }

    public static MethodObj getMethodObjByStateId(String stateId) {
        List<MethodObj> group = getGroupByStateId(stateId);
        return group.stream()
                .filter(x -> stateId.equals(x.getMethod().getAnnotation(State.class).id()))
                .findFirst().orElse(null);
    }

    public static List<MethodObj> getGroupByStateId(String stateId) {
        Map<Object, Collection<MethodObj>> groups = MethodContext.mapOfCollection(PatternEnum.STATE);
        if (Objects.isNull(groups) || groups.isEmpty()) {
            throw new NoSuchElementException("There is no such state actuator.");
        }
        for (Map.Entry<Object, Collection<MethodObj>> entry : groups.entrySet()) {
            MethodObj methodObj = entry.getValue().stream()
                    .filter(x -> stateId.equals(x.getMethod().getAnnotation(State.class).id()))
                    .findFirst().orElse(null);
            if (Objects.nonNull(methodObj)) {
                List<MethodObj> group = (List<MethodObj>) entry.getValue();
                sortMethodObjs(group);
                return group;
            }
        }
        throw new NoSuchElementException("There is no such state actuator.");
    }
}
