package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a level-order traversal (breadth-first search) of the binary tree
     * and returns a list of levels, where each level is a list of node values.
     *
     * @param root the root node of the binary tree
     * @return a list of lists containing node values level by level
     */
    public static List<List<Integer>> method1(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int nodesInCurrentLevel = queue.size();
            List<Integer> currentLevelValues = new ArrayList<>(nodesInCurrentLevel);

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                BinaryTree.Node currentNode = queue.poll();
                if (currentNode == null) {
                    continue;
                }

                currentLevelValues.add(currentNode.data);

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            levels.add(currentLevelValues);
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

        if (level < 1) {
            System.out.println("Level must be greater than or equal to 1.");
            return;
        }

        printNodesAtLevel(node, level);
    }

    private static void printNodesAtLevel(BinaryTree.Node node, int level) {
        if (node == null) {
            return;
        }

        if (level == 1) {
            System.out.print(node.data + " ");
            return;
        }

        printNodesAtLevel(node.left, level - 1);
        printNodesAtLevel(node.right, level - 1);
    }
}