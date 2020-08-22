package com.thamidurm.cse_machine.control_structures;
/**
 * UnOpControlStructure represents a unary operator control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class UnOpControlStructure extends BaseControlStructure {
    private final String operation;

    public UnOpControlStructure(String operation) {
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
        return "UnOpControlStructure{" +
                "operation='" + operation + '\'' +
                '}';
    }
}
