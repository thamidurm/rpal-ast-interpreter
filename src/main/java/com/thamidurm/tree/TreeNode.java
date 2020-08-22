package com.thamidurm.tree;

import java.util.ArrayList;

public class TreeNode {
    private TreeNode parent;
    private ArrayList<TreeNode> children;
    private String token;

    public TreeNode(){
        this.parent = null;
        this.children = new ArrayList<>();
    }
    public TreeNode(String token){
        this();
        this.token = token;
    }

//    public TreeNode(String token, TreeNode parent) {
//        this(token);
//        this.parent = parent;
//    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addChild(TreeNode node){
        this.children.add(node);
    }

//    public void removeChild(TreeNode node){
//        this.children.remove(node);
//    }

    public void replaceChild(int index, TreeNode node){
        this.children.set(index, node);
    }

}
