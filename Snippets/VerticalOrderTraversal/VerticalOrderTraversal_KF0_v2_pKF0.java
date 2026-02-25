package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Utility class
    }

    public static List<Integer> verticalTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, List<Integer>> columnToValues = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node node = nodes.poll();
            int column = columns.poll();

            columnToValues
                .computeIfAbsent(column, key -> new ArrayList<>())
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

        for (int column = minColumn; column <= maxColumn; column++) {
            List<Integer> values = columnToValues.get(column);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}