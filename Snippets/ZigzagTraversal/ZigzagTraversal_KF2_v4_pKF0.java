package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean isLeftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node currentNode = queue.poll();
                if (currentNode == null) {
                    continue;
                }

                if (isLeftToRight) {
                    currentLevel.add(currentNode.data);
                } else {
                    currentLevel.add(0, currentNode.data);
                }

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            result.add(currentLevel);
            isLeftToRight = !isLeftToRight;
        }

        return result;
    }
}