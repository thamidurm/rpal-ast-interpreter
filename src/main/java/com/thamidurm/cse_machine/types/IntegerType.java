package com.thamidurm.cse_machine.types;

/**
 * Class representing an Integer in the language
 */
public class IntegerType extends BaseType{

    final long value;

    public IntegerType(long value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "Integer";
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegerType{" +
                "value=" + value +
                '}';
    }

    @Override
    public String toPrintString() {
        return String.valueOf(value);
    }
}
