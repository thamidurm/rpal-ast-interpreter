package com.thamidurm.cse_machine.control_structures;

/**
 * GammaControlStructure represents a gamma control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class GammaControlStructure extends BaseControlStructure{
    public GammaControlStructure() {

    }

    private String getType() {
        return "GammaControlStructure";
    }

    @Override
    public String toString() {
        return getType();
    }
}
