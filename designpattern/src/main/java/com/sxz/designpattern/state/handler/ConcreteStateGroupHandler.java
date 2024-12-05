package com.sxz.designpattern.state.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author suxz
 **/
public class ConcreteStateGroupHandler implements StateGroupHandler {

    private final Map<String, StateHandler> context = new ConcurrentHashMap<>();

    private String minId;

    private String maxId;

    public void setMinId(String minId) {
        this.minId = minId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    @Override
    public StateHandler get(String stateId) {
        return context.get(stateId);
    }

    @Override
    public FirstStateHandler first() {
        return context.get(minId);
    }

    @Override
    public LastStateHandler last() {
        return context.get(maxId);
    }

    public void put(String stateId, StateHandler stateHandler) {
        context.put(stateId, stateHandler);
    }
}
