package com.thamidurm.cse_machine.control_structures;

import com.thamidurm.cse_machine.types.BaseType;
/**
 * RatorControlStructure represents a builtin function control structure in the
 * stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public abstract class RatorControlStructure extends BaseControlStructure {

//    public String getName() {
//        return name;
//    }

    public RatorControlStructure() {
    }

    public abstract BaseType apply(BaseType operand);
}
