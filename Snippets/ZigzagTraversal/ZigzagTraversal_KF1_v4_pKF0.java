package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> method1(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Deque<BinaryTree.Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        boolean traverseRightToLeft = false;

        while (!nodeQueue.isEmpty()) {
            int currentLevelSize = nodeQueue.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < currentLevelSize; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();

                if (traverseRightToLeft) {
                    currentLevelValues.add(0, currentNode.data);
                } else {
                    currentLevelValues.add(currentNode.data);
                }

                if (currentNode.left != null) {
                    nodeQueue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodeQueue.offer(currentNode.right);
                }
            }

            traverseRightToLeft = !traverseRightToLeft;
            levels.add(currentLevelValues);
        }

        return levels;
    }
}