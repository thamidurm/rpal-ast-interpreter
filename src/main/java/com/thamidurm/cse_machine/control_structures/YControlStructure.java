package com.thamidurm.cse_machine.control_structures;

/**
 * YControlStructure Represents a Y* control structure in the control
 * or stack of the CSE
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class YControlStructure extends BaseControlStructure {

    public YControlStructure() {
    }

    private String getType() {
        return "YControlStructure";
    }

    @Override
    public String toString() {
        return getType();
    }
}
