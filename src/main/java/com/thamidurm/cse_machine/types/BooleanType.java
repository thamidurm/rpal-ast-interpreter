package com.thamidurm.cse_machine.types;

/**
 * Class representing a boolean type in the language
 */
public class BooleanType extends BaseType {
    final boolean value;

    public BooleanType(boolean value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "Truthvalue";
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public String toString() {
        return "BooleanType{" +
                "value=" + value +
                '}';
    }

    @Override
    public String toPrintString() {
        return String.valueOf(value);
    }
}
