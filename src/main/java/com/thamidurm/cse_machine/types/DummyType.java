package com.thamidurm.cse_machine.types;

/**
 * Class representing the dummy type in the language
 */
public class DummyType extends BaseType {

    @Override
    public String getType() {
        return "Dummy";
    }

    @Override
    public String toPrintString() {
        return "dummy";
    }
}
