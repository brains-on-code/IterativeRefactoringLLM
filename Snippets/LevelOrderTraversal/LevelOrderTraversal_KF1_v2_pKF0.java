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
        queue.add(root);

        while (!queue.isEmpty()) {
            int nodesInCurrentLevel = queue.size();
            List<Integer> currentLevel = new ArrayList<>(nodesInCurrentLevel);

            for (int i = 0; i < nodesInCurrentLevel; i++) {
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
            return;
        }

        if (level > 1) {
            method2(node.left, level - 1);
            method2(node.right, level - 1);
        }
    }
}