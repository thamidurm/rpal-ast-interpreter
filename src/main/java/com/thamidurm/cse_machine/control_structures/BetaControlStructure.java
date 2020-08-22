package com.thamidurm.cse_machine.control_structures;
/**
 * BetaControlStructure represents a beta control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class BetaControlStructure extends BaseControlStructure {
    public BetaControlStructure() {
    }

    private String getType() {
        return "BetaControlStructure";
    }

    @Override
    public String toString() {
        return getType();
    }
}
