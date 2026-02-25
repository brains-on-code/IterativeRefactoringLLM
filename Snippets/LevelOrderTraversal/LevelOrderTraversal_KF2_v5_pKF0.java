package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            levels.add(extractNextLevel(queue));
        }

        return levels;
    }

    private static List<Integer> extractNextLevel(Queue<BinaryTree.Node> queue) {
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

        return currentLevelValues;
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

        printLevel(root, level);
    }

    private static void printLevel(BinaryTree.Node node, int level) {
        if (node == null) {
            return;
        }

        if (level == 1) {
            System.out.print(node.data + " ");
            return;
        }

        printLevel(node.left, level - 1);
        printLevel(node.right, level - 1);
    }
}