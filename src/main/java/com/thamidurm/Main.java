package com.thamidurm;

import com.thamidurm.cse_machine.CSEMachine;
import com.thamidurm.tree.TreeNode;
import com.thamidurm.tree.Standardizer;
import com.thamidurm.tree.TreeUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Accept the path to ast from the first cmd argument & generate tree
        TreeNode root = null;
        if (args.length < 1) {
            System.out.println("Please provide a path to a file");
            System.exit(-1);
        }
        // Try to read in an AST in the text form from a file
        try {
            root = TreeUtil.readTree(args[0]);
        } catch (IOException e) {
            System.out.println("Error reading the file");
            System.exit(-1);
        }

        // Standardize tree
        Standardizer.standardize(root);

        // Pass the standardized tree for execution
        CSEMachine.run(root);

        // Print a \n just as the rpal interpreter is doing
        System.out.print('\n');
    }
}
