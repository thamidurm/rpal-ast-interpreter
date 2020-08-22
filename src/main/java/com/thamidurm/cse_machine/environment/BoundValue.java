package com.thamidurm.cse_machine.environment;

import com.thamidurm.cse_machine.types.BaseType;

/**
 * Class representing a value that can be bound/ has been bound to an environment
 */
public class BoundValue {
    private final BaseType value;

    public BoundValue(BaseType value) {
        this.value = value;
    }

    public BaseType getValue() {
        return value;
    }

}
