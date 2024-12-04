package com.sxz.designpattern.state.test;

import com.sxz.designpattern.annotation.State;
import com.sxz.designpattern.state.context.StateContext;
import com.sxz.designpattern.state.context.impl.ConcreteStateContext;

public class StateTest {

    public static StateContext stateContext() {
        return new ConcreteStateContext("com.sxz");
    }

    public static void main(String[] args) {
        StateContext context = stateContext();
        context.buildGroup("state-group-001").get("group-001-001").current();
        context.buildGroup("state-group-001").get("group-001-001").next();
        context.buildGroup("state-group-001").get("group-001-001").previous();

        context.buildGroup("state-group-001").first().current();
        context.buildGroup("state-group-001").first().next();

        context.buildGroup("state-group-001").last().previous();
        context.buildGroup("state-group-001").last().current();

        context.buildState("group-001-001").current();
        context.buildState("group-001-001").next();
        context.buildState("group-001-001").previous();
    }

    @State(id = "group-001-001", groupId = "state-group-001", order = 1)
    public void first() {
        System.out.println("state-001");
    }

    @State(id = "group-001-002", groupId = "state-group-001", order = 2)
    public void second() {
        System.out.println("state-002");
    }

    @State(id = "group-001-003", groupId = "state-group-001", order = 3)
    public void third() {
        System.out.println("state-003");
    }
}
