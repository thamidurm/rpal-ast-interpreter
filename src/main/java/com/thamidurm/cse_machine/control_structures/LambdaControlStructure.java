package com.thamidurm.cse_machine.control_structures;

import java.util.ArrayList;
/**
 * LambdaControlStructure represents a lambda control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class LambdaControlStructure extends BaseControlStructure {

    private int c;
    private final ArrayList<String> x;
    private final int k;

    public LambdaControlStructure(String x, int k){
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(x);
        this.x = tmp;
        this.k = k;
    }

//
//    public LambdaControlStructure(int c, String x, int k){
//        ArrayList<String> tmp = new ArrayList<>();
//        tmp.add(x);
//        this.x = tmp;
//        this.k = k;
//        this.c = c;
//    }
//

    public LambdaControlStructure(ArrayList<String> x, int k) {
        this.x = x;
        this.k = k;
    }

    public LambdaControlStructure(int c, ArrayList<String> x, int k) {
        this(x,k);
        this.c = c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getC() {
        return c;
    }

    public int getK() {
        return k;
    }

    public ArrayList<String> getX() {
        return x;
    }

    @Override
    public String toString() {
        return "LambdaControlStructure{" +
                "c=" + c +
                ", x=" + x +
                ", k=" + k +
                '}';
    }
}
