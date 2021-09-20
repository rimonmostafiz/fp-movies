package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * @author Rimon Mostafiz
 */
public enum Status {

    Rumored("Rumored"),
    PostProduction("Post Production"),
    UnReleased("Un Released"),
    Released("Released");

    private final String val;

    Status(String v) {
        this.val = v;
    }

    public static Status getStatus(String val) {
        return Arrays.stream(Status.values())
                .filter(v -> v.getVal().equalsIgnoreCase(val))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getVal() {
        return val;
    }
}
