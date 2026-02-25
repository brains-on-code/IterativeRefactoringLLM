package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a level-order (breadth-first) traversal of the binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of levels, where each level is a list of node values
     */
    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<BinaryTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int nodesOnLevel = queue.size();
            List<Integer> level = new ArrayList<>(nodesOnLevel);

            for (int i = 0; i < nodesOnLevel; i++) {
                BinaryTree.Node current = queue.poll();
                level.add(current.data);

                if (current.left != null) {
                    queue.add(current.left);
                }

                if (current.right != null) {
                    queue.add(current.right);
                }
            }

            result.add(level);
        }

        return result;
    }

    /**
     * Prints all nodes at the specified level of the binary tree.
     *
     * @param root  the root node of the binary tree
     * @param level the level to print (1-based)
     */
    public static void printGivenLevel(BinaryTree.Node root, int level) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printGivenLevel(root.left, level - 1);
            printGivenLevel(root.right, level - 1);
        }
    }
}