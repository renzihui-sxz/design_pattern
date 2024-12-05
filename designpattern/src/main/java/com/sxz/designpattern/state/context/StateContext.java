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
     * Build a status processor based on the status id.
     */
    StateHandler buildState(String stateId);
}
