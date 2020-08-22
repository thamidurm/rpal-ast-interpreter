package com.thamidurm.cse_machine.types;

import com.thamidurm.cse_machine.control_structures.RatorControlStructure;

/**
 * Class representing a builtin function in the language (eg.- Print, Conc,...)
 */
public class BuiltinFunctionType extends BaseType {
    public BuiltinFunctionType(RatorControlStructure value) {
        this.value = value;
    }

// --Commented out by Inspection START (8/16/2020 3:01 PM):
//    public BuiltinFunctionType(){
//        this.value = null;
//    }
// --Commented out by Inspection STOP (8/16/2020 3:01 PM)

    private final RatorControlStructure value;

    @Override
    public String getType() {
        return "Function";
    }

    public RatorControlStructure getValue() {
        return value;
    }

    @Override
    public String toPrintString() {
        return null;
    }
}
