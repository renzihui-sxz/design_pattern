package io.github.designpattern.enums;

/**
 * @author suxz
 */
public enum PatternEnum {

    STRATEGY("STRATEGY"),

    STATE("STATE"),

    CHAIN_OF_RESPONSIBILITY("CHAIN_OF_RESPONSIBILITY");

    private String model;

    public String getModel() {
        return model;
    }

    PatternEnum(String model) {
        this.model = model;
    }
}
