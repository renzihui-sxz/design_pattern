package com.sxz.designpattern.state.handler;

/**
 * @author suxz
 */
public interface FirstStateHandler extends CurrentStateHandler {

    /**
     * The next state handler executes.
     */
    void next();

}
