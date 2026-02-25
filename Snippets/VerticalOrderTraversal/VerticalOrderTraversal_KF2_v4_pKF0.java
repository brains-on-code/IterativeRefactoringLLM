package com.thealgorithms.datastructures.trees;

import java.util.*;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Utility class
    }

    public static List<Integer> verticalTraversal(BinaryTree.Node root) {
        if (root == null) {
            return Collections.emptyList();
        }

        Map<Integer, List<Integer>> columnToValues = new HashMap<>();
        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node node = nodes.poll();
            int column = columns.poll();

            columnToValues
                .computeIfAbsent(column, k -> new ArrayList<>())
                .add(node.data);

            minColumn = Math.min(minColumn, column);
            maxColumn = Math.max(maxColumn, column);

            if (node.left != null) {
                nodes.offer(node.left);
                columns.offer(column - 1);
            }

            if (node.right != null) {
                nodes.offer(node.right);
                columns.offer(column + 1);
            }
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