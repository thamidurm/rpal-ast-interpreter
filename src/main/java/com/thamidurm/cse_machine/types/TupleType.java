package com.thamidurm.cse_machine.types;

import java.util.ArrayList;

/**
 * Class representing the tuple type in the language
 */
public class TupleType extends BaseType {
    private final ArrayList<BaseType> value;

//    public TupleType() {
//        this.value = new ArrayList<>();
//    }

    public TupleType(ArrayList<BaseType> value) {
        this.value = value;
    }

    public ArrayList<BaseType> getValue() {
        return value;
    }

    @Override
    public String getType() {
        return "Tuple";
    }

    @Override
    public String toString() {

        StringBuilder tuple = new StringBuilder("(");
        for (BaseType item: value){
            tuple.append(item.toString()).append(", ");
        }
        tuple.append(")");
        return "TupleType{" +
                "value=" + tuple+
                '}';
    }

    @Override
    public String toPrintString() {
        if (value.size() ==0){
            return "nil";
        }
        StringBuilder tuple = new StringBuilder("(");
        for(int i = 0; i < value.size(); i++){
            BaseType item = value.get(i);
            tuple.append(item.toPrintString());
            if (i < value.size() - 1)
                tuple.append(", ");
        }
        tuple.append(")");
        return tuple.toString();
    }
}
