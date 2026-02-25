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
            levels.add(collectNextLevel(queue));
        }

        return levels;
    }

    private static List<Integer> collectNextLevel(Deque<BinaryTree.Node> queue) {
        int nodesInLevel = queue.size();
        List<Integer> levelValues = new ArrayList<>(nodesInLevel);

        for (int i = 0; i < nodesInLevel; i++) {
            BinaryTree.Node node = queue.poll();
            if (node == null) {
                continue;
            }

            levelValues.add(node.data);

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return levelValues;
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