package edu.miu.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Rimon Mostafiz
 */
public enum Gender {
    Other(0), Male(1), Female(2);

    private Integer value;

    Gender(Integer v) {
        this.value = v;
    }

    @JsonValue
    public Integer getValue() {
        return value;
    }
}
