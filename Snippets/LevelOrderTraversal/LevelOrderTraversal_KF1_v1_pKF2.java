package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a level-order (breadth-first) traversal of the binary tree and
     * returns a list of levels, where each level is a list of node values.
     *
     * @param root the root node of the binary tree
     * @return a list of levels with node values; empty list if root is null
     */
    public static List<List<Integer>> method1(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node current = queue.poll();
                currentLevel.add(current.data);

                if (current.left != null) {
                    queue.add(current.left);
                }

                if (current.right != null) {
                    queue.add(current.right);
                }
            }

            levels.add(currentLevel);
        }

        return levels;
    }

    /**
     * Prints all nodes at a given level in the binary tree.
     *
     * @param node  the current node
     * @param level the level to print (1-based)
     */
    public static void method2(BinaryTree.Node node, int level) {
        if (node == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level == 1) {
            System.out.print(node.data + " ");
        } else if (level > 1) {
            method2(node.left, level - 1);
            method2(node.right, level - 1);
        }
    }
}