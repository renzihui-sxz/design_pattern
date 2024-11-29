package com.sxz.designpattern.enums;

/**
 * @author suxz
 */
public enum PatternEnum {

    STRATEGY("STRATEGY"),

    CHAIN_OF_RESPONSIBILITY("CHAIN_OF_RESPONSIBILITY");

    private String model;

    public String getModel() {
        return model;
    }

    PatternEnum(String model) {
        this.model = model;
    }
}
