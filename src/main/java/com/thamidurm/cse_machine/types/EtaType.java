package com.thamidurm.cse_machine.types;

import com.thamidurm.cse_machine.control_structures.EtaControlStructure;

/**
 * Class representing an Eta control structure that can be bound to a variable
 */
public class EtaType extends BaseType{

    private final EtaControlStructure value;

    public EtaType(EtaControlStructure value) {
        this.value = value;
    }

    public EtaControlStructure getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "EtaType";
    }

    @Override
    public String toPrintString() {
        return "[eta closure: "
                + value.getV().get(0)
                + ": "
                + value.getK()
                +"]";
    }
}