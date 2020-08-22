

package com.thamidurm.cse_machine.control_structures;

import com.thamidurm.cse_machine.types.BaseType;
/**
 * ValueControlStructure represents a value control structure in the control
 * or stack of the CSE machine
 *
 * @author Thamidu Muthukumarana
 * @version 0.1
 *
 */
public class ValueControlStructure extends BaseControlStructure {
    final BaseType value;

    public ValueControlStructure(BaseType value) {
        this.value = value;
    }

    public BaseType getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ValueControlStructure{" +
                "value=" + value +
                '}';
    }
}
