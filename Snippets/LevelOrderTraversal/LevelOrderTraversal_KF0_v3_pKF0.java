package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

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
                    queue.offer(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            levels.add(currentLevel);
        }

        return levels;
    }

    public static void printGivenLevel(BinaryTree.Node root, int level) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level <= 0) {
            System.out.println("Level must be greater than 0! Exiting.");
            return;
        }

        printLevelRecursive(root, level);
    }

    private static void printLevelRecursive(BinaryTree.Node node, int level) {
        if (node == null) {
            return;
        }

        if (level == 1) {
            System.out.print(node.data + " ");
            return;
        }

        printLevelRecursive(node.left, level - 1);
        printLevelRecursive(node.right, level - 1);
    }
}