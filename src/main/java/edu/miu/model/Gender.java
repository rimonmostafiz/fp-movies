package edu.miu.model;

/**
 * @author Rimon Mostafiz
 */
public enum Gender {
    Male(1), Female(2);

    private Integer val;

    Gender(Integer v) {
        this.val = v;
    }
}
