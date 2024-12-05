package com.sxz.designpattern.state.handler;

/**
 * @author suxz
 */
public interface CurrentStateHandler {

    /**
     * Current status processor execution.
     */
    StateHandler handle();

}
