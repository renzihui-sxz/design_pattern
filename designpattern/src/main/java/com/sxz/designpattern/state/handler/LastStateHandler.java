package com.sxz.designpattern.state.handler;

/**
 * @author suxz
 */
public interface LastStateHandler extends CurrentStateHandler{

    /**
     * The previous state handler executes.
     */
    StateHandler previous();

}
