package com.thamidurm.cse_machine.types;

import com.thamidurm.cse_machine.control_structures.LambdaControlStructure;

/**
 * Class representing a function type in the language
 */
public class LambdaType extends BaseType {
    private final LambdaControlStructure lambda;

    public LambdaType(LambdaControlStructure lambda) {
        this.lambda = lambda;
    }

    public LambdaControlStructure getLambda() {
        return lambda;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String toPrintString() {
        return "[lambda closure: "
                + lambda.getX().get(0)
                + ": "
                + lambda.getK()
                +"]";
    }
}
