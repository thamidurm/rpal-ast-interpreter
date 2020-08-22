package com.thamidurm.cse_machine.control_structures;

import com.thamidurm.cse_machine.environment.Environment;
/**
 * EnvironmentControlStructure represents an environment control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class EnvironmentControlStructure extends BaseControlStructure{
    private final Environment environment;
    private final int number;
    private static int count = 0;

    public EnvironmentControlStructure(Environment environment) {
        this.environment = environment;
        this.number = count++;
    }


/*    public int getNumber() {
        return number;
    }*/

    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "EnvironmentControlStructure{" +
                "parent=" + environment.toString() +
                ", number=" + number +
                '}';
    }
}
