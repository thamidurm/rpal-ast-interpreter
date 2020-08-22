package com.thamidurm.cse_machine.control_structures;

import com.thamidurm.cse_machine.types.*;
import com.thamidurm.tree.TreeNode;

import java.util.ArrayList;

/**
 * Class containing the logic related to control structure generation
 */
public class CSUtil {

    /**
     * Generate an array list of control structure list corresponding to a standardized tree while
     * traversing the tree in-order
     * @param node the root node of the standardized tree
     * @param controlStructures array list with already generated control structure lists
     * @param current index of the current control structure list in the controlStructures array list
     */
    private static void generateControlStructuresHelper
            (TreeNode node, ArrayList<ArrayList<BaseControlStructure>>controlStructures, int current){
        String type = node.getToken();

        if (type.equals("gamma")) {
            addGamma(controlStructures, current);
        }
        else if (type.equals("lambda")) {
            addLambda(node, controlStructures, current);
            return;
        }else if (type.equals("<Y*>")){
            controlStructures.get(current).add(new YControlStructure());
        }else if (type.equals("->")){
            addCond(node, controlStructures, current);
            return;
        }
        else if (type.equals("tau")){
            controlStructures.get(current).add(new TauControlStructure(node.getChildren().size()));
        }
        else if (type.equals("<nil>")){
            controlStructures.get(current).add(
                    new ValueControlStructure(
                            new TupleType(
                                    new ArrayList<>()
                            )
                    )
            );
        }
        else if (isBinOp(type)){
            controlStructures.get(current).add(new BinOpControlStructure(type));
        }
        else if (isUnOp(type)){
            controlStructures.get(current).add(new UnOpControlStructure(type));
        }
        else if (type.startsWith("<ID:")){
            controlStructures.get(current).add(
                    new NameControlStructure(
                            type.substring(4, type.length() -1)
                    ));
        }else if (type.startsWith("<INT:")){
            controlStructures.get(current).add(
                    new ValueControlStructure(
                            new IntegerType(
                                    Integer.parseInt(type.substring(5, type.length()-1))
                            )
                    )
            );
        }else if (type.startsWith("<STR:'")){
            String string = getRaw(type.substring(6, type.length() - 2));
                            
            controlStructures.get(current).add(
                    new ValueControlStructure(
                            new  StringType(string)
                    )
            );
        } else if(type.equals("<dummy>")){
            controlStructures.get(current).add(
                    new ValueControlStructure(
                            new DummyType()
                    )
            );

        } else if(type.equals("<true>") || type.equals("<false>")){
            controlStructures.get(current).add(
                    new ValueControlStructure(
                            new BooleanType(type.equals("<true>"))
                    )
            );
        }

        for (TreeNode child : node.getChildren()) {
            generateControlStructuresHelper(child, controlStructures, current);
        }
    }

    /**
     * Returns the unescaped string of given string
     * @param string string to be processed
     * @return unescaped version of the string
     */
    private static String  getRaw(String string) {
        char cur;
        boolean backslash = false;
        StringBuilder raw = new StringBuilder();
        for(int i = 0; i < string.length(); i++){
            cur = string.charAt(i);

            if(backslash){
                switch (cur){
                    case '\\':
                        raw.append("\\");
                        break;
                    case 'n':
                        raw.append("\n");
                        break;
                    case 't':
                        raw.append("\t");
                        break;
                    case 'r':
                        raw.append("\r");
                        break;
                    case 'f':
                        raw.append("\f");
                        break;
                    case '\'':
                        raw.append("'");
                        break;
                    default:
                        raw.append("\\").append(cur);
                }
                backslash = false;
            } else {
                if (cur == '\\'){
                    backslash = true;
                }else{
                    raw.append(cur);
                }
            }
        }
        return raw.toString();
//      return StringEscapeUtils.unescapeJava(string);
    }

    /**
     * Add the control structures related to the conditional to the current control structure list
     * @param controlStructures array list with already generated control structure lists
     * @param current index of the current control structure list in the controlStructures array list
     */
    private static void addCond(TreeNode node, ArrayList<ArrayList<BaseControlStructure>> controlStructures,
                                int current) {
        ArrayList<BaseControlStructure> new_control_structure1 = new ArrayList<>();
        ArrayList<BaseControlStructure> new_control_structure2 = new ArrayList<>();
        controlStructures.add(new_control_structure1);
        controlStructures.add(new_control_structure2);
        BetaControlStructure beta = new BetaControlStructure();
        DeltaControlStructure delta1 = new DeltaControlStructure(new_control_structure1);
        DeltaControlStructure delta2 = new DeltaControlStructure(new_control_structure2);
        controlStructures.get(current).add(delta1);
        controlStructures.get(current).add(delta2);
        controlStructures.get(current).add(beta);
        int c1 = controlStructures.size() - 2;
        int c2 = c1 + 1;
        generateControlStructuresHelper(node.getChildren().get(1), controlStructures, c1);
        generateControlStructuresHelper(node.getChildren().get(2), controlStructures, c2);
        generateControlStructuresHelper(node.getChildren().get(0), controlStructures, current);
    }

    /**
     * Add a gamma control structure to the current control structure list
     * @param controlStructures array list with already generated control structure lists
     * @param current index of the current control structure list in the controlStructures array list
     */
    private static void addGamma(ArrayList<ArrayList<BaseControlStructure>> controlStructures, int current) {
        GammaControlStructure gamma = new GammaControlStructure();
        controlStructures.get(current).add(gamma);
    }

    /**
     * Generate control structures related to a lambda node
     * @param node the lambda node
     * @param controlStructures array list with already generated control structure lists
     * @param current index of the current control structure list in the controlStructures array list
     */
    private static void addLambda(TreeNode node, ArrayList<ArrayList<BaseControlStructure>> controlStructures,
                                  int current) {
        String leftToken = node.getChildren().get(0).getToken();
        LambdaControlStructure lambda;
        if (leftToken.equals(",")){
            ArrayList<String> names = new ArrayList<>();
            lambda = new LambdaControlStructure(
                names, controlStructures.size()
            );

            for (TreeNode item: node.getChildren().get(0).getChildren()){
                names.add(item.getToken().substring(4, item.getToken().length() -1));
            }
        }else{
            lambda = new LambdaControlStructure(
                    leftToken.substring(4, leftToken.length() -1), controlStructures.size());
        }
        ArrayList<BaseControlStructure> newControlStructure = new ArrayList<>();
        controlStructures.get(current).add(lambda);
        controlStructures.add(newControlStructure);
        generateControlStructuresHelper(node.getChildren().get(1), controlStructures,
                controlStructures.size() - 1);
    }

    /**
     * Check whether the provided string corresponds to a unary operator
     * @param type string related to a tree node
     * @return whether the string belongs to an unary operator
     */
    private static boolean isUnOp(String type) {
        return (type.equals("neg") || type.equals("not"));
    }

    /**
     * Check whether the provided string corresponds to a binary operator
     * @param type string related to a tree node
     * @return whether the string belongs to a binary operator
     */
    private static boolean isBinOp(String type) {
        switch (type) {
            case "+":
            case "-":
            case "/":
            case "*":
            case "**":
            case "eq":
            case "ne":
            case "gr":
            case "ge":
            case "le":
            case "ls":
            case "aug":
            case "or":
            case "&":
                return true;
            default:
                return false;
        }
    }

    /** Generates control structures using a standardized tree root by calling
     * the helper method
     * @param node standardized tree root
     * @return the generated control structures list
     */
    public static ArrayList<ArrayList<BaseControlStructure>> generateControlStructures(TreeNode node){
        ArrayList<ArrayList<BaseControlStructure>> csArrayList = new ArrayList<>();
        csArrayList.add(new ArrayList<>());
        generateControlStructuresHelper(node, csArrayList, 0);
        return csArrayList;
    }
}
