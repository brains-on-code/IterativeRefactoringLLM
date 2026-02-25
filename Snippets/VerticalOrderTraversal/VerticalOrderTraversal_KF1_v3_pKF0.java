package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> method1(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
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
        for (int col = minColumn; col <= maxColumn; col++) {
            List<Integer> values = columnToValues.get(col);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}