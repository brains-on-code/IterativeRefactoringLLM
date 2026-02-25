package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();

        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            int currentLevelSize = nodeQueue.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < currentLevelSize; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();
                currentLevelValues.add(currentNode.data);

                if (currentNode.left != null) {
                    nodeQueue.add(currentNode.left);
                }

                if (currentNode.right != null) {
                    nodeQueue.add(currentNode.right);
                }
            }

            levels.add(currentLevelValues);
        }

        return levels;
    }

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