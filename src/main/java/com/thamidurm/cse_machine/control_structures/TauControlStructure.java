package com.thamidurm.cse_machine.control_structures;
/**
 * TauControlStructure represents a Tau control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class TauControlStructure extends BaseControlStructure {
    private final int n;
    public TauControlStructure(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return "TauControlStructure{" +
                "n=" + n +
                '}';
    }
}
