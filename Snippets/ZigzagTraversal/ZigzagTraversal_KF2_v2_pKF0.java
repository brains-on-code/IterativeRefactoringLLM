package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class ZigzagTraversal {

    private ZigzagTraversal() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        boolean isLeftToRight = true;

        while (!nodeQueue.isEmpty()) {
            int nodesInCurrentLevel = nodeQueue.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();
                if (currentNode == null) {
                    continue;
                }

                if (isLeftToRight) {
                    currentLevelValues.add(currentNode.data);
                } else {
                    currentLevelValues.add(0, currentNode.data);
                }

                if (currentNode.left != null) {
                    nodeQueue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodeQueue.offer(currentNode.right);
                }
            }

            result.add(currentLevelValues);
            isLeftToRight = !isLeftToRight;
        }

        return result;
    }
}