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
     * Performs a level-order (breadth-first) traversal of a binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of levels, where each level is a list of node values
     */
    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node currentNode = queue.poll();
                if (currentNode == null) {
                    continue;
                }

                currentLevel.add(currentNode.data);

                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }
            }

            levels.add(currentLevel);
        }

        return levels;
    }

    /**
     * Prints all nodes at the specified level in the binary tree.
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
            return;
        }

        printGivenLevel(root.left, level - 1);
        printGivenLevel(root.right, level - 1);
    }
}