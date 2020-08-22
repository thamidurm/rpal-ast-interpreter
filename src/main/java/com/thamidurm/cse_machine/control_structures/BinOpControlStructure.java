package com.thamidurm.cse_machine.control_structures;
/**
 * BinOpControlStructure represents a binary operator control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class BinOpControlStructure extends BaseControlStructure {
    private final String operation;

    public BinOpControlStructure(String operation) {
        this.operation = operation;
    }

    /**
     * Returns the operation's string representation
     * @return String
     */
    public String getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "BinOpControlStructure{" +
                "operation='" + operation + '\'' +
                '}';
    }
}
