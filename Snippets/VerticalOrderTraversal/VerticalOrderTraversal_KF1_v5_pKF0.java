package com.thealgorithms.datastructures.trees;

import java.util.*;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> method1(BinaryTree.Node root) {
        if (root == null) {
            return Collections.emptyList();
        }

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, List<Integer>> columnToValues = new HashMap<>();

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

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumn + 1);
            }

            minColumn = Math.min(minColumn, currentColumn);
            maxColumn = Math.max(maxColumn, currentColumn);
        }

        List<Integer> result = new ArrayList<>();
        for (int column = minColumn; column <= maxColumn; column++) {
            List<Integer> valuesInColumn = columnToValues.get(column);
            if (valuesInColumn != null) {
                result.addAll(valuesInColumn);
            }
        }

        return result;
    }
}