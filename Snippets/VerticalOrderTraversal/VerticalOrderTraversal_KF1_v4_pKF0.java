package com.thealgorithms.datastructures.trees;

import java.util.*;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static List<Integer> method1(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, List<Integer>> columnValues = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node node = nodes.poll();
            int column = columns.poll();

            columnValues
                .computeIfAbsent(column, k -> new ArrayList<>())
                .add(node.data);

            if (node.left != null) {
                nodes.offer(node.left);
                columns.offer(column - 1);
            }

            if (node.right != null) {
                nodes.offer(node.right);
                columns.offer(column + 1);
            }

            minColumn = Math.min(minColumn, column);
            maxColumn = Math.max(maxColumn, column);
        }

        List<Integer> result = new ArrayList<>();
        for (int col = minColumn; col <= maxColumn; col++) {
            List<Integer> values = columnValues.get(col);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}