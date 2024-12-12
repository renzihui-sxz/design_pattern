package io.github.designpattern.state.handler;

/**
 * @author suxz
 */
public interface StateGroupHandler {

    /**
     * Gets the status handler based on the status id.
     */
    StateHandler get(String stateId);

    /**
     * Gets the first state handler.
     */
    FirstStateHandler first();

    /**
     * Gets the last state handler.
     */
    LastStateHandler last();

}
