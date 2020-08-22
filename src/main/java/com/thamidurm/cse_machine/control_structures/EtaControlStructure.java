package com.thamidurm.cse_machine.control_structures;

import java.util.ArrayList;

/**
 * EtaControlStructure represents an eta control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class EtaControlStructure extends BaseControlStructure {
    private final int c;
    private final ArrayList<String> v;
    private final int k;

    public EtaControlStructure(int c, ArrayList<String> v, int k) {
        this.c = c;
        this.v = v;
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public ArrayList<String> getV() {
        return v;
    }

    public int getC() {
        return c;
    }
}
