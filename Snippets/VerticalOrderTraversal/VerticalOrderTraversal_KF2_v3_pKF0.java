package com.thealgorithms.datastructures.trees;

import java.util.*;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Utility class
    }

    public static List<Integer> verticalTraversal(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Map<Integer, List<Integer>> columnToValues = new HashMap<>();
        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.poll();
            int currentColumn = columnQueue.poll();

            columnToValues
                .computeIfAbsent(currentColumn, k -> new ArrayList<>())
                .add(currentNode.data);

            minColumn = Math.min(minColumn, currentColumn);
            maxColumn = Math.max(maxColumn, currentColumn);

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumn + 1);
            }
        }

        List<Integer> result = new ArrayList<>();
        for (int col = minColumn; col <= maxColumn; col++) {
            List<Integer> columnValues = columnToValues.get(col);
            if (columnValues != null) {
                result.addAll(columnValues);
            }
        }

        return result;
    }
}