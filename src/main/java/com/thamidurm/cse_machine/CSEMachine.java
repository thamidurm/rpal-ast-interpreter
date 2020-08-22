package com.thamidurm.cse_machine;

import com.thamidurm.cse_machine.control_structures.*;
import com.thamidurm.cse_machine.environment.BoundValue;
import com.thamidurm.cse_machine.environment.Environment;
import com.thamidurm.cse_machine.types.*;
import com.thamidurm.tree.TreeNode;

import java.util.ArrayList;

/**
 * Class containing the logic for the execution of the CSE machine
 */
public class CSEMachine {
    private static final ArrayList<BaseControlStructure> control = new ArrayList<>();
    private static final ArrayList<BaseControlStructure> stack =new ArrayList<>();
    private static Environment currentEnv;
    private static ArrayList<ArrayList<BaseControlStructure>> csList = new ArrayList<>();
    private static ArrayList<BaseControlStructure> oldStack = null;

//    private  static  void printCss(ArrayList<ArrayList<BaseControlStructure>>
//                             css){
//        for(var csl: css){
//            for (var cs : csl){
//                System.out.print(cs.toString()+ " ");
//            }
//            System.out.println('\n');
//        }
//    }

    /** Run a standardize tree
     * @param node the node of the standardized tree to be executed
     */
    public static void run(TreeNode node){
        // Generate Control Structures
        csList = CSUtil.generateControlStructures(node);

        // Initialize stack and control
        initialize(csList);

        // Loop until control is empty
        while(!control.isEmpty()){
            CSERule1();
            CSERule2();
            CSERule3();
            CSERule4();
            CSERule5();
            CSERule6();
            CSERule7();
            CSERule8();
            CSERule9();
            CSERule10();
            CSERule11();
            CSERule12();
            CSERule13();
            checkLoop();
        }
    }

    /**
     * Checks whether the CSE Machine has entered an infinite loop
     * by comparing the stack before all rules are tried and the stack after
     * all rules are tried.
     */
    private static void checkLoop() {
        if (oldStack == null){
            oldStack = new ArrayList<>(stack);
        }else{
            if (oldStack.equals(stack)){
                System.out.println("\nCSEMachine entered an infinite loop");
                System.exit(-1);
            }else{
                oldStack = new ArrayList<>(stack);
            }
        }
    }

    /**
     * Checks whether rule 13 is applicable and applies if possible
     */
    private static void CSERule13() {
        if (control.size() >= 1 && stack.size() >= 2){
            BaseControlStructure tmpGamma =  control.get(control.size() - 1);
            BaseControlStructure tempEta = stack.get(stack.size() - 1);

            if (tmpGamma instanceof GammaControlStructure
            && tempEta instanceof EtaControlStructure){
                EtaControlStructure eta = (EtaControlStructure)tempEta;

                control.add(new GammaControlStructure());
                stack.add(new LambdaControlStructure(eta.getC(), eta.getV(), eta.getK()));
            }
        }
    }

    /**
     * Checks whether rule 12 is applicable and applies if possible
     */
    private static void CSERule12() {
        if (control.size() >= 1 && stack.size() >= 2){
            BaseControlStructure tmpGamma = control.get(control.size() - 1);
            BaseControlStructure tmpY = stack.get(stack.size() - 1);
            BaseControlStructure tmpLambda = stack.get(stack.size() - 2);

            if (tmpGamma instanceof GammaControlStructure
            && tmpY instanceof YControlStructure
            && tmpLambda instanceof LambdaControlStructure){
                control.remove(control.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                LambdaControlStructure lambda = (LambdaControlStructure)tmpLambda;
                stack.add(new EtaControlStructure(lambda.getC(), lambda.getX(), lambda.getK()));
            }
        }
    }

    /**
     * Checks whether rule 11 is applicable and applies if possible
     */
    private static void CSERule11() {
        if(control.size()>= 1 && stack.size()>=2){
            BaseControlStructure tmpGamma = control.get(control.size() - 1);
            BaseControlStructure tmpLambda = stack.get(stack.size() - 1);
            BaseControlStructure tmpRand = stack.get(stack.size() - 2);

            if(tmpGamma instanceof GammaControlStructure
            && tmpLambda instanceof LambdaControlStructure
            && tmpRand instanceof ValueControlStructure){
                // var gamma = (GammaControlStructure)tmpGamma;
                LambdaControlStructure lambda = (LambdaControlStructure)tmpLambda;
                ValueControlStructure rand = (ValueControlStructure)tmpRand;

                if (lambda.getX().size() <= 1)
                    return;

                if(rand.getValue() instanceof TupleType){
                    currentEnv = new Environment(Environment.getEnv(lambda.getC()));
                    ArrayList<BaseType> tuple = ((TupleType)rand.getValue()).getValue();
                    for (int i = 0; i < tuple.size(); i++){
                        currentEnv.addValue(
                                lambda.getX().get(i),
                                new BoundValue(tuple.get(i))
                        );
                    }
                    control.remove(control.size() -1);
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    EnvironmentControlStructure envCS = new EnvironmentControlStructure(currentEnv);
                    control.add(envCS);
                    control.addAll(csList.get(lambda.getK()));
                    stack.add(envCS);
                }
            }
        }
    }

    /**
     * Checks whether rule 10 is applicable and applies if possible
     */
    private static void CSERule10() {
        if (control.size() >= 1 && stack.size() >= 2){
            BaseControlStructure tmpGamma = control.get(control.size() - 1);
            BaseControlStructure tmpTuple = stack.get(stack.size() - 1);
            BaseControlStructure tmpRand = stack.get(stack.size() - 2);

            if (tmpGamma instanceof GammaControlStructure
            && tmpTuple instanceof ValueControlStructure
            && tmpRand instanceof ValueControlStructure){
                ValueControlStructure value1 = (ValueControlStructure) tmpTuple;
                ValueControlStructure value2 = (ValueControlStructure) tmpRand;
                if (! (value1.getValue() instanceof TupleType) )
                    return;
                ArrayList<BaseType> tuple = ((TupleType)value1.getValue()).getValue();
                long index =  ((IntegerType)value2.getValue()).getValue();
                control.remove(control.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.add(new ValueControlStructure(tuple.get((int)index-1)));
            }
        }
    }

    /**
     * Checks whether rule 9 is applicable and applies if possible
     */
    private static void CSERule9() {
        if (control.size() >= 1 && stack.size() >=1){
            BaseControlStructure tmpTau = control.get(control.size() - 1);
            BaseControlStructure first = stack.get(stack.size() - 1);
            if (tmpTau instanceof  TauControlStructure
            && (first instanceof ValueControlStructure
                    || first instanceof  LambdaControlStructure
                    || first instanceof EtaControlStructure)){
                TauControlStructure tau = (TauControlStructure)tmpTau;

                ArrayList<BaseType> tuple = new ArrayList<>();

                // Add items to the tuple
                for(int i = 0; i < tau.getN(); i++){

                    BaseControlStructure value = stack.get(stack.size() - 1 - i);
                    if ((value instanceof ValueControlStructure)){
                        ValueControlStructure valueCS = (ValueControlStructure) value;
                        tuple.add(valueCS.getValue());
                    }else if ((value instanceof LambdaControlStructure)){
                        LambdaControlStructure valueCS = (LambdaControlStructure) value;
                        tuple.add(new LambdaType(valueCS));
                    }else if ((value instanceof EtaControlStructure)){
                        EtaControlStructure valueCS = (EtaControlStructure) value;
                        tuple.add(new EtaType(valueCS));
                    }else {
                        System.out.println("Invalid type for tuple element");
                        System.exit(-1);
                    }



                }
                control.remove(control.size()-1);
                for(int i = 0; i < tau.getN(); i++){
                    stack.remove(stack.size() - 1 );
                }
                stack.add(new ValueControlStructure(new TupleType(tuple)));
            }
        }
    }

    /**
     * Checks whether rule 8 is applicable and applies if possible
     */
    private static void CSERule8() {
        if (control.size() >=3 && stack.size() >= 1){
            BaseControlStructure tmpThen = control.get(control.size() - 3);
            BaseControlStructure tmpElse = control.get(control.size() - 2);
            BaseControlStructure tmpBeta = control.get(control.size() - 1);

            BaseControlStructure tmpCheck = stack.get(stack.size() - 1);

            if (tmpThen instanceof DeltaControlStructure
            && tmpElse instanceof DeltaControlStructure
            && tmpBeta instanceof  BetaControlStructure
            && tmpCheck instanceof ValueControlStructure){
                DeltaControlStructure then = (DeltaControlStructure)tmpThen;
                DeltaControlStructure els = (DeltaControlStructure)tmpElse;
                //Check boolean conversion

                BaseType valCheck = ((ValueControlStructure) tmpCheck).getValue();
                if (valCheck instanceof  BooleanType){
                    boolean check = ((BooleanType)valCheck).getValue();
                    control.remove(control.size() - 1);
                    control.remove(control.size()  - 1);
                    control.remove(control.size() - 1);
                    stack.remove(stack.size() - 1);
                    ArrayList<BaseControlStructure> arrayList;
                    if(check){
                        arrayList = then.getControlStructures();
                    }else {
                        arrayList = els.getControlStructures();
                    }
                    control.addAll(arrayList);
                }
            }
        }
    }

    /**
     * Checks whether rule 7 is applicable and applies if possible
     */
    private static void CSERule7() {
        if(control.size() >= 1
        && stack.size() >= 1){
            BaseControlStructure tmpOp = control.get(control.size()-1);
            BaseControlStructure tmpRand = stack.get(stack.size() - 1);

            if(tmpOp instanceof UnOpControlStructure
            && tmpRand instanceof ValueControlStructure){
                UnOpControlStructure op = (UnOpControlStructure)tmpOp;
                ValueControlStructure rand = (ValueControlStructure)tmpRand;

                if(rand.getValue() instanceof IntegerType
                || rand.getValue() instanceof BooleanType){
                    // If the conditions for at least one unary operator is set
                    // apply it and add result to stack
                    control.remove(control.size() - 1);
                    stack.remove(stack.size() - 1);
                    BaseType value = apply(op,rand);
                    stack.add(new ValueControlStructure(value));
                }
            }
        }
    }

    /**
     * Checks whether rule 6 is applicable and applies if possible
     */
    private static void CSERule6() {
        if (control.size() >= 1
            && stack.size() >= 2){
            BaseControlStructure tmpOp = control.get(control.size()-1);
            BaseControlStructure tmpRand1 = stack.get(stack.size() - 1);
            BaseControlStructure tmpRand2 = stack.get(stack.size() - 2);

            if (tmpOp instanceof  BinOpControlStructure
            && tmpRand1 instanceof ValueControlStructure
            && tmpRand2 instanceof ValueControlStructure){
                BinOpControlStructure op = (BinOpControlStructure)tmpOp;
                ValueControlStructure rand1 = (ValueControlStructure)tmpRand1;
                ValueControlStructure rand2 = (ValueControlStructure)tmpRand2;

                if((rand1.getValue() instanceof  IntegerType
                && rand2.getValue() instanceof  IntegerType)
                || (rand1.getValue() instanceof  BooleanType
                        && rand2.getValue() instanceof  BooleanType)
                || (rand1.getValue() instanceof  StringType
                        && rand2.getValue() instanceof  StringType)
                || (rand1.getValue() instanceof TupleType)){

                    // If the conditions for at least one bin op is set
                    // apply it and add result to stack

                    control.remove(control.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    BaseType value = apply(op, rand1, rand2);
                    ValueControlStructure valueCS = new ValueControlStructure(value);
                    stack.add(valueCS);
                }
            }
        }
    }

    /**
     * Checks whether rule 5 is applicable and applies if possible
     */
    private static void CSERule5() {
        // When there is a value in the stack head
        if (control.size() >=1 && stack.size() >=2){
            BaseControlStructure controlEnv = control.get(control.size() - 1);
            // BaseControlStructure value = stack.get(stack.size()-1);
            BaseControlStructure stackEnv = stack.get(stack.size() - 2);

            if (controlEnv instanceof EnvironmentControlStructure
                    && stackEnv instanceof  EnvironmentControlStructure
                    && sameEnvironment(stackEnv,controlEnv)
            ){
                control.remove(control.size() - 1);
                stack.remove(stack.size() - 2);
            }
        }
        // When there isn't a value in stack head (i.e. - when the stack head is the environment
        // control structure)
        if (control.size()>=1 && stack.size() >=1){
            BaseControlStructure controlEnv = control.get(control.size() - 1);
            BaseControlStructure stackEnv = stack.get(stack.size() - 1);

            if (controlEnv instanceof EnvironmentControlStructure
                    && stackEnv instanceof  EnvironmentControlStructure
                    && sameEnvironment(stackEnv,controlEnv)
            ){
                control.remove(control.size() - 1);
                stack.remove(stack.size() - 1);
            }
        }

        // Update the current environment
        for(int i = stack.size() - 1; i >=0 ; i--){
            BaseControlStructure item = stack.get(i);
            if (item instanceof EnvironmentControlStructure){
                currentEnv = ((EnvironmentControlStructure) item).getEnvironment();
                return;
            }
        }
    }

    /**
     * Checks whether rule 4 is applicable and applies if possible
     */
    private static void CSERule4() {
        if(control.size() >=1
                && stack.size() >= 2) {
            BaseControlStructure tmpGamma = control.get(control.size() - 1);
            BaseControlStructure tmpLambda = stack.get(stack.size() - 1);
            BaseControlStructure tmpRand = stack.get(stack.size() - 2);

            if (tmpGamma instanceof GammaControlStructure
                    && tmpLambda instanceof LambdaControlStructure) {
                LambdaControlStructure lambda = (LambdaControlStructure) tmpLambda;

                if (lambda.getX().size() == 1) {
                    // If the conditions for Rule 4 are satisfied
                    if (tmpRand instanceof LambdaControlStructure) {
                        // If the value to be bound is a lambda
                        LambdaControlStructure rand = (LambdaControlStructure) tmpRand;
                        Environment newEnv = new Environment( Environment.getEnv(lambda.getC()));
                        currentEnv = newEnv;
                        newEnv.addValue(
                                lambda.getX().get(0),
                                new BoundValue(new LambdaType(rand))
                        );
                    }else if (tmpRand instanceof  EtaControlStructure){
                        // If the value to be bound is an eta
                        EtaControlStructure rand = (EtaControlStructure) tmpRand;
                        Environment newEnv = new Environment( Environment.getEnv(lambda.getC()));
                        currentEnv = newEnv;
                        newEnv.addValue(
                                lambda.getX().get(0),
                                new BoundValue(new EtaType(rand))
                        );

                    } else if (tmpRand instanceof ValueControlStructure) {
                        // If the value to be bound is a literal
                        ValueControlStructure rand = (ValueControlStructure) tmpRand;
                        Environment newEnv = new Environment( Environment.getEnv(lambda.getC()));
                        currentEnv = newEnv;
                        newEnv.addValue(
                                lambda.getX().get(0),
                                new BoundValue(rand.getValue())
                        );
                    }

                    control.remove(control.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);

                    EnvironmentControlStructure envCS = new EnvironmentControlStructure(currentEnv);
                    control.add(envCS);
                    control.addAll(csList.get(lambda.getK()));
                    stack.add(envCS);
                }
            }
        }
    }

    /**
     * Checks whether rule 3 is applicable and applies if possible
     */
    private static void CSERule3() {
        if(control.size() >=1
                && stack.size() >= 2){
            BaseControlStructure tmpGamma = control.get(control.size()-1);
            BaseControlStructure tmpRator = stack.get(stack.size()-1);
            BaseControlStructure tmpRand = stack.get(stack.size() -2);

            if(tmpGamma instanceof GammaControlStructure
                    && tmpRator instanceof RatorControlStructure)
            {
                if( tmpRand instanceof  ValueControlStructure
                        || tmpRand instanceof LambdaControlStructure
                        || tmpRand instanceof RatorControlStructure){
                    control.remove(control.size() - 1);
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() -1);

                    BaseType value;

                    // Get the true values of the operands (by unwrapping the values)
                    if (tmpRand instanceof ValueControlStructure){
                        value = ( (ValueControlStructure) tmpRand).getValue();
                    } else if (tmpRand instanceof  RatorControlStructure) {
                        value = new BuiltinFunctionType((RatorControlStructure) tmpRand);
                    }else{
                        value = new LambdaType((LambdaControlStructure) tmpRand);
                    }
                    RatorControlStructure rator = (RatorControlStructure) tmpRator;

                    // Apply the operator
                    BaseType ret = rator.apply(value);

                    if (ret instanceof BooleanType ||
                            ret instanceof StringType
                            || ret instanceof IntegerType
                            || ret instanceof TupleType
                            || ret instanceof DummyType
                    ){
                        // Add the result of the operation to stack
                        stack.add(new ValueControlStructure(ret));
                    }else if (ret instanceof BuiltinFunctionType){
                        // If the result is a built in function add it to the stack.
                        // This is used for the Conc builtin function which needs to
                        // take two parameters.
                        stack.add(((BuiltinFunctionType) ret).getValue());
                    }
                }
            }
        }
    }

    /**
     * Checks whether rule 2 is applicable and applies if possible
     */
    private static void CSERule2() {
        if(control.size() >= 1){
            BaseControlStructure cs = control.get(control.size() - 1);

            if(cs instanceof LambdaControlStructure){
                // If top of the stack is a lambda, set C and
                // add it to the stack, remove from control
                LambdaControlStructure lambda = (LambdaControlStructure)cs;
                lambda.setC(currentEnv.getId());
                control.remove(control.size() -1);
                stack.add(lambda);
            }
        }
    }

    /**
     * Checks whether rule 1 is applicable and applies if possible
     */
    private static void CSERule1() {
        if(control.size() >= 1){
            BaseControlStructure value = control.get(control.size() - 1);
            if (value instanceof ValueControlStructure
                    || value instanceof YControlStructure){
                // If the top of the control is a literal or a Y*, removes it from
                // control and inserts it to stack
                control.remove(control.size() - 1);
                stack.add( value);
            } else if(value instanceof  NameControlStructure) {
                // If the top of the stack is a name, search for it in the
                // environment tree and add to the stack if a match is found

                control.remove(control.size() - 1);
                BoundValue found = currentEnv.getValue(((NameControlStructure)value).getName());
                if (found.getValue() instanceof LambdaType){
                    // If the found value is a lambda, add it to stack
                    stack.add(
                            ((LambdaType) found.getValue()).getLambda()
                    );
                    return;
                } else if (found.getValue() instanceof BuiltinFunctionType){
                    // If the found value is a builtin function, add it to stack
                    BuiltinFunctionType rator = (BuiltinFunctionType)found.getValue();
                    stack.add(rator.getValue());
                    return;
                } else if (found.getValue() instanceof EtaType){
                    // If the found value is an eta, add it to stack
                    EtaType eta = (EtaType)found.getValue();
                    stack.add(eta.getValue());
                    return;
                }
                // If the found value doesn't match any above conditions
                // then it is a literal, and the literal is added to stack
                stack.add( new ValueControlStructure(found.getValue()));
            }
        }
    }

    /** Apply the unary operator op to the operand
     * @param op unary operator to be applied
     * @param rand the operand
     * @return the result
     */
    private static BaseType apply(UnOpControlStructure op, ValueControlStructure rand) {
        switch (op.getOperation()) {
            case "neg" : {
                if(! (rand.getValue() instanceof IntegerType) ){
                    System.out.println("Cannot negate non-integer value");
                    System.exit(-1);
                }
                long result = -((IntegerType) rand.getValue()).getValue();
                return new IntegerType(result);
            }
            case "not" : {
                if (! (rand.getValue() instanceof  BooleanType)){
                    System.out.println("Non-boolean for 'not' application");
                    System.exit(-1);
                }
                boolean result = ((BooleanType) rand.getValue()).getValue();
                return new BooleanType(!result);
            }
        }
        return null;
    }

    /** Apply the boolean binary operator to the operands and return the result
     * wrapped in the correct type object
     * @param operator unary operator to be applied
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @return the result
     */
    private static BaseType applyBooleanBinOp(boolean operand1, boolean operand2, String operator){
        switch (operator){
            case "&" : {
                boolean result = operand1 && operand2;
                return new BooleanType(result);
            }
            case "or" : {
                boolean result = operand1 || operand2;
                return new BooleanType(result);
            }
            default:{
                System.out.println("Invalid operator");
                System.exit(-1);
            }
        }
        return null;
    }

    /** Apply the integer binary operator to the operands and return the result
     * wrapped in the correct type object
     * @param operator unary operator to be applied
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @return the result
     */
    private static BaseType applyIntegerBinOp(long operand1, long operand2, String operator){
        switch (operator){
            case "+" : {
                long result = operand1 + operand2;
                return new IntegerType(result);
            }
            case "-" : {
                long result = operand1 - operand2;
                return new IntegerType(result);
            }
            case "/" : {
                if (operand2 == 0){
                    System.out.println("Division by zero");
                    System.exit(-1);
                }
                long result = operand1 / operand2;
                return new IntegerType(result);
            }
            case "*" : {
                long result = operand1 * operand2;
                return new IntegerType(result);
            }
            case "**" : {
                long result = Math.round(Math.pow(operand1, operand2));
                return new IntegerType(result);
            }
            case "ge" : {
                boolean result = operand1 >= operand2;
                return new BooleanType(result);
            }
            case "gr" : {
                boolean result = operand1 > operand2;
                return new BooleanType(result);
            }
            case "le" : {
                boolean result = operand1 <= operand2;
                return new BooleanType(result);
            }
            case "ls" : {
                boolean result = operand1 < operand2;
                return new BooleanType(result);
            }
            default:{
                System.out.println("Invalid operator");
                System.exit(-1);
            }
        }
        return null;
    }

    /** Apply the binary operator op to the operands
     * @param op unary operator to be applied
     * @param rand1 the first operand
     * @param rand2 the second operand
     * @return the result
     */
    private static BaseType apply(BinOpControlStructure op, ValueControlStructure rand1, ValueControlStructure rand2) {
        switch (op.getOperation()) {
            case "aug" : {
                if (!(rand1.getValue() instanceof TupleType)){
                    System.out.println("Cannot augment a non tuple");
                    System.exit(-1);
                }
                ArrayList<BaseType> tuple = ((TupleType) rand1.getValue()).getValue();
                BaseType value = rand2.getValue();
                tuple.add(value);
                return new TupleType(tuple);
            }
            case "+" :
            case "-" :
            case "/":
            case "*":
            case "**":
            case "ge":
            case "gr":
            case "le":
            case "ls": {

                if (! (rand1.getValue() instanceof IntegerType)
                    || !(rand2.getValue() instanceof  IntegerType)){
                    System.out.println("Illegal Operands for '" + op.getOperation() + "'");
                    System.exit(-1);
                }

                long operand1 = ((IntegerType) rand1.getValue()).getValue();
                long operand2 = ((IntegerType) rand2.getValue()).getValue();
                return applyIntegerBinOp(operand1, operand2, op.getOperation());
            }
            case "or":
            case "&":{

                if (!(rand1.getValue() instanceof BooleanType)
                        || !(rand2.getValue() instanceof  BooleanType)){
                    System.out.println("Invalid value used in logical expression '"
                            + op.getOperation() + "'");
                    System.exit(-1);
                }
                boolean operand1 = ((BooleanType) rand1.getValue()).getValue();
                boolean operand2 = ((BooleanType) rand2.getValue()).getValue();
                return applyBooleanBinOp(operand1, operand2, op.getOperation());
            }
            case "ne" : {
                boolean result = !equals(rand1.getValue(), rand2.getValue());
                return new BooleanType(result);
            }
            case "eq" : {
                boolean result = equals(rand1.getValue(), rand2.getValue());
                return new BooleanType(result);
            }
        }
        return null;
    }

    /** Check if two values are equal
     * @param value first value for equality test
     * @param value1 second value for equality test
     * @return whether both values are equal
     */
    private static boolean equals(BaseType value, BaseType value1) {
        if (value.getType().equals(value1.getType())){
            switch (value.getType()){
                case "Integer":
                    return ((IntegerType)value).getValue() == ((IntegerType)value1).getValue();
                case "String":
                    return ((StringType)value).getValue().equals(((StringType)value1).getValue());
                case "Truthvalue":
                    return ((BooleanType)value).getValue() == ((BooleanType)value1).getValue();
                case "Tuple":
                    return  ((TupleType)value).getValue().equals(((TupleType)value1).getValue());
                default:
                    System.out.println("Unexpected type given for equality test");
                    System.exit(-1);
            }
        }
        return false;
    }


    /** Check whether two environment objects are the same
     * @param stackEnv first environment
     * @param controlEnv second environment
     * @return whether the environments are same
     */
    private static boolean sameEnvironment(BaseControlStructure stackEnv, BaseControlStructure controlEnv) {
        Environment e1 = ((EnvironmentControlStructure)stackEnv).getEnvironment();
        Environment e2 = ((EnvironmentControlStructure)controlEnv).getEnvironment();
        return e1.equals(e2);
    }


    /** Initializes the CSE machine before executing the control structures
     * @param controlStructures the control structures to be executed
     */
    private static void initialize(ArrayList<ArrayList<BaseControlStructure>> controlStructures) {
        currentEnv = new Environment();
        EnvironmentControlStructure envStructure = new EnvironmentControlStructure(currentEnv);

        // Define and add Print
        BoundValue print = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                System.out.print(operand.toPrintString());
                return new DummyType();
            }
        }));
        currentEnv.addValue("Print", print);
        currentEnv.addValue("print", print);

        // Define and add ItoS
        BoundValue iToS = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                if (! (operand instanceof IntegerType)){
                    System.out.println("Invalid ItoS use. Argument is not an integer");
                    System.exit(-1);
                }
                return new StringType(String.valueOf(((IntegerType) operand).getValue()));
            }
        }));
        currentEnv.addValue("ItoS", iToS);

        // Define and add Isinteger
        BoundValue isInteger = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof IntegerType);
            }
        }));
        currentEnv.addValue("Isinteger", isInteger);

        // Define and add isTruthvalue
        BoundValue isTruthValue = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof BooleanType);
            }
        }));
        currentEnv.addValue("Istruthvalue", isTruthValue);

        // Define and add Isstring
        BoundValue isString = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof StringType);
            }
        }));
        currentEnv.addValue("Isstring", isString);

        // Define and add Istuple
        BoundValue isTuple = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof TupleType);
            }
        }));
        currentEnv.addValue("Istuple", isTuple);

        // Define and add Isfunction
        BoundValue isFunction = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof LambdaType);
            }
        }));
        currentEnv.addValue("Isfunction", isFunction);

        // Define and add Isdummy
        BoundValue isDummy = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                return new BooleanType(operand instanceof DummyType);
            }
        }));
        currentEnv.addValue("Isdummy", isDummy);

        // Define and add Stem
        BoundValue stem = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                if (! (operand instanceof StringType)){
                    System.out.println("Invalid Stem use. Argument is not a string");
                    System.exit(-1);
                }

                String string = ((StringType) operand).getValue();
                return new StringType(String.valueOf(string.charAt(0)));
            }
        }));
        currentEnv.addValue("Stem", stem);

        // Define and add Stern
        BoundValue stern = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                if (! (operand instanceof StringType)){
                    System.out.println("Invalid Stem use. Argument is not a string");
                    System.exit(-1);
                }
                String string = ((StringType) operand).getValue();
                return new StringType(string.substring(1));
            }
        }));
        currentEnv.addValue("Stern", stern);

        // Define and add Conc
        BoundValue conc = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            private String first = null;
            @Override
            public BaseType apply(BaseType operand) {
                if (!(operand instanceof StringType)){
                    System.out.println("Non-strings used in conc call");
                    System.exit(-1);
                }

                if (this.first == null){
                    this.first =((StringType) operand).getValue();
                    return new BuiltinFunctionType(this);
                } else {
                    String tmpFirst = first;
                    String second = ((StringType) operand).getValue();
                    this.first = null;
                    return new StringType(tmpFirst + second);
                }
            }
        }));
        currentEnv.addValue("Conc", conc);

        // Define and add Null
        BoundValue nullT = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                if (!(operand instanceof TupleType)){
                    return new BooleanType(false);
                }

                TupleType rand = (TupleType)operand;
                return new BooleanType(rand.getValue().size() == 0);
            }
        }));
        currentEnv.addValue("Null", nullT);

        // Define and Order
        BoundValue orderT = new BoundValue(new BuiltinFunctionType(new RatorControlStructure() {
            @Override
            public BaseType apply(BaseType operand) {
                if (! (operand instanceof TupleType)){
                    System.out.println("Attempt to find the order of a non-tuple");
                    System.exit(-1);
                }
                TupleType rand = (TupleType)operand;
                return new IntegerType(rand.getValue().size());
            }
        }));
        currentEnv.addValue("Order", orderT);

        control.add(envStructure);
        control.addAll(controlStructures.get(0));
        stack.add(envStructure);
    }
}
