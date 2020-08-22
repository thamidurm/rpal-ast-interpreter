package com.thamidurm.cse_machine.types;

/**
 * Class representing the string type in the language
 */
public class StringType extends BaseType{

    private final String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "String";
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "StringType{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public String toPrintString() {
        return value;
    }
}
