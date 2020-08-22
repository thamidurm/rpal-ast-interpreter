package com.thamidurm.cse_machine.control_structures;
/**
 * NameControlStructure represents an identifier control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class NameControlStructure extends BaseControlStructure {
    final String name;

    public NameControlStructure(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NameControlStructure{" +
                "name='" + name + '\'' +
                '}';
    }
}
