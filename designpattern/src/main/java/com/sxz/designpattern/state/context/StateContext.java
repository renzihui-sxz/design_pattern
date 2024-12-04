package com.sxz.designpattern.state.context;

import com.sxz.designpattern.state.handler.StateGroupHandler;
import com.sxz.designpattern.state.handler.StateHandler;

/**
 * @author suxz
 */
public interface StateContext {

    /**
     * Build groups based on group ids.
     */
    StateGroupHandler buildGroup(String groupId);

    /**
     * 根据状态id构建状态处理器。
     */
    StateHandler buildState(String stateId);
}
