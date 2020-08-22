package com.thamidurm.cse_machine.control_structures;

import java.util.ArrayList;
/**
 * DeltaControlStructure represents a delta control structure resulting
 * from a conditional operation in the control or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class DeltaControlStructure extends BaseControlStructure {
    private final ArrayList<BaseControlStructure> controlStructures;

    public DeltaControlStructure(ArrayList<BaseControlStructure> controlStructures) {
        this.controlStructures = controlStructures;
    }

    public ArrayList<BaseControlStructure> getControlStructures() {
        return controlStructures;
    }

    @Override
    public String toString() {
        return "DeltaControlStructure{" +
                "controlStructures=" + controlStructures +
                '}';
    }
}
