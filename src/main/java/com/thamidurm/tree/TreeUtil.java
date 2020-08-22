package com.thamidurm.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TreeUtil {

//    public  static  void  printTree(TreeNode node){
//        printTreeHelper(node, 0);
//    }

//    private static void printTreeHelper(TreeNode node, int depth){
//        if (node == null){
//            return;
//        }
//        for(int i = 0; i < depth; i++){
//            System.out.print('.');
//        }
//        System.out.println(node.getToken());
//
//        for(TreeNode child: node.getChildren()){
//            printTreeHelper(child, depth + 1);
//        }
//    }

    /**
     * Reads in the ast in text form and convert it to an object representation
     * @param fileName path to the text file containing ast
     * @return the root node of the generated tree
     * @throws IOException when the file reader fails
     */
    public static TreeNode readTree(String fileName) throws IOException{
        TreeNode root = new TreeNode();
        TreeNode prev = null;
        int dots;
        int lastDots = 0;

        BufferedReader reader = new BufferedReader(new FileReader(
                fileName));
        String line = reader.readLine();

        while (line != null) {
            dots = dotCount(line);
            if (dots == 0){
                root.setToken(line.trim());
                prev = root;
            } else {
                TreeNode current = new TreeNode(line.substring(dots).trim());
                TreeNode parent;
                if (lastDots == dots){
                    parent = prev.getParent();
                }else  if (lastDots > dots){
                    int diff = lastDots - dots;
                    parent = prev;
                    for(int i = 0; i <= diff; i++){
                        parent = parent.getParent();
                    }
                }else{
                    parent = prev;
                }
                parent.addChild(current);
                current.setParent(parent);
                prev = current;
            }
            lastDots = dots;

            line = reader.readLine();
        }
        reader.close();

        return root;
    }

    /**
     * Returns the number of consecutive dots at the beginning of the string
     * @param line string of which dots will be counted
     * @return the number of dots
     */
    private static int dotCount(String line) {
        int dots = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != '.')
                break;
            dots++;
        }
        return dots;
    }
}
