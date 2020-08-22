package com.thamidurm.tree;

import java.util.ArrayList;

public class Standardizer {

    /** Standardize the tree with the given node as the root node
     * @param node root node of the tree to be standardized
     */
    public static void standardize(TreeNode node){

        ArrayList<TreeNode> children = node.getChildren();

        // Children are standardized first
        for(TreeNode child : children){
            standardize(child);
        }

        String type = node.getToken();

        switch (type) {
            case "let" :
                standardizeLet(node, children);
                break;
            case "where" :
                standardizeWhere(node, children);
                break;
            case "function_form" :
                standardizeFunctionForm(node, children);
                break;
            case "lambda" :
                standardizeMultiParamFunction(node, children);
                break;
            case "within" :
                standardizeWithin(node, children);
                break;
            case "@" :
                standardizeInfix(node, children);
                break;
            case "and" :
                standardizeAnd(node, children);
                break;
            case "rec" :
                standardizeRec(node, children);
                break;
        }
    }

    /** Standardizes a multi parameter lambda node
     * @param node the lambda node to be standardized
     * @param children the children of the lambda node
     */
    private static void standardizeMultiParamFunction(TreeNode node, ArrayList<TreeNode> children) {
        ArrayList<TreeNode> newChildren = new ArrayList<>();
        newChildren.add(children.get(0));
        node.setChildren(newChildren);
        int vCount = children.size() - 2;
        TreeNode prev = node;
        for (int i = 0; i < vCount; i++) {
            TreeNode cur = children.get(i + 1);
            TreeNode lambda = new TreeNode("lambda");
            lambda.addChild(cur);
            cur.setParent(lambda);
            prev.addChild(lambda);
            lambda.setParent(prev);
            prev = lambda;
        }
        prev.addChild(children.get(children.size() - 1));
    }

    /** Standardizes a rec node
     * @param node the rec node to be standardized
     * @param children the children of the rec node
     */
    private static void standardizeRec(TreeNode node, ArrayList<TreeNode> children) {
        node.setToken("=");
        TreeNode X = children.get(0).getChildren().get(0);
        TreeNode E =   children.get(0).getChildren().get(1) ;

        TreeNode gamma = new TreeNode("gamma");
        TreeNode lambda = new TreeNode("lambda");
        TreeNode Ystar = new TreeNode("<Y*>");

        lambda.addChild(X);
        X.setParent(lambda);
        lambda.addChild(E);
        E.setParent(lambda);
        gamma.addChild(Ystar);
        Ystar.setParent(E);
        gamma.addChild(lambda);
        lambda.setParent(gamma);

        ArrayList<TreeNode> newChildren = new ArrayList<>();
        newChildren.add(X);
        newChildren.add(gamma);

        node.setChildren(newChildren);
        X.setParent(node);
        gamma.setParent(node);
    }

    /** Standardizes an and node
     * @param node the and node to be standardized
     * @param children the children of the and node
     */
    private static void standardizeAnd(TreeNode node, ArrayList<TreeNode> children) {
        node.setToken("=");

        TreeNode comma = new TreeNode(",");
        TreeNode tau = new TreeNode("tau");

        for (TreeNode child : children) {
            comma.addChild(child.getChildren().get(0));
            child.getChildren().get(0).setParent(comma);
            tau.addChild(child.getChildren().get(1));
            child.getChildren().get(1).setParent(tau);
        }

        ArrayList<TreeNode> newChildren = new ArrayList<>();
        newChildren.add(comma);
        newChildren.add(tau);
        node.setChildren(newChildren);
        comma.setParent(node);
        tau.setParent(node);
    }

    /** Standardizes an @ node
     * @param node the @ node to be standardized
     * @param children children of the @ node
     */
    private static void standardizeInfix(TreeNode node, ArrayList<TreeNode> children) {
        TreeNode E1 = children.get(0);
        TreeNode N = children.get(1);
        TreeNode E2 = children.get(2);

        node.setToken("gamma");
        TreeNode gamma = new TreeNode("gamma");
        gamma.addChild(N);
        gamma.addChild(E1);
        N.setParent(gamma);
        E1.setParent(gamma);

        ArrayList<TreeNode> newChildren = new ArrayList<>();
        newChildren.add(gamma);
        newChildren.add(E2);

        node.setChildren(newChildren);
        gamma.setParent(node);
        E2.setParent(node);
    }

    /** Standardizes a within node
     * @param node the within node to be standardized
     * @param children the children of the within node
     */
    private static void standardizeWithin(TreeNode node, ArrayList<TreeNode> children) {
        node.setToken("=");
        TreeNode X1 = children.get(0).getChildren().get(0);
        TreeNode X2 = children.get(1).getChildren().get(0);
        TreeNode E1 = children.get(0).getChildren().get(1);
        TreeNode E2 = children.get(1).getChildren().get(1);

        TreeNode gamma = new TreeNode("gamma");
        TreeNode lambda = new TreeNode("lambda");
        gamma.addChild(lambda);
        lambda.setParent(gamma);
        gamma.addChild(E1);
        E1.setParent(gamma);

        lambda.addChild(X1);
        lambda.addChild(E2);
        X1.setParent(lambda);
        E2.setParent(lambda);

        ArrayList<TreeNode> newChildren = new ArrayList<>();
        newChildren.add(X2);
        newChildren.add(gamma);

        node.setChildren(newChildren);
        X2.setParent(node);
        gamma.setParent(node);
    }

    /** Standardize a function_form node
     * @param node the function_form node to be standardized
     * @param children the children of the function_form node
     */
    private static void standardizeFunctionForm(TreeNode node, ArrayList<TreeNode> children) {
        node.setToken("=");
        ArrayList<TreeNode> newChildren =  new ArrayList<>();

        node.setChildren(newChildren);
        newChildren.add(children.get(0));
        int vCount = children.size() - 2;
        TreeNode prev = node;
        for (int i = 0; i < vCount; i++) {
            TreeNode cur = children.get(i + 1);
            TreeNode lambda = new TreeNode("lambda");
            lambda.addChild(cur);
            cur.setParent(lambda);
            prev.addChild(lambda);
            lambda.setParent(prev);
            prev = lambda;
        }
        prev.addChild(children.get(children.size() - 1));
    }

    /** Standardize a where node
     * @param node the where node
     * @param children the children of the where node
     */
    private static void standardizeWhere(TreeNode node, ArrayList<TreeNode> children) {
        TreeNode P = children.get(0);
        TreeNode eq = children.get(1);
        TreeNode E = eq.getChildren().get(1);

        node.setToken("gamma");
        eq.setToken("lambda");
        eq.replaceChild(1, P);
        P.setParent(eq);
        node.replaceChild(0, eq);
        node.replaceChild(1, E);
        E.setParent(node);
    }

    /** Standardize a let node
     * @param node the let node to be standardize
     * @param children the child elements of node
     */
    private static void standardizeLet(TreeNode node, ArrayList<TreeNode> children) {
        TreeNode P = children.get(1);
        TreeNode eq = children.get(0);
        TreeNode E = eq.getChildren().get(1);

        node.setToken("gamma");
        eq.setToken("lambda");
        eq.replaceChild(1, P);
        P.setParent(eq);
        node.replaceChild(1, E);
        E.setParent(node);
    }
}
