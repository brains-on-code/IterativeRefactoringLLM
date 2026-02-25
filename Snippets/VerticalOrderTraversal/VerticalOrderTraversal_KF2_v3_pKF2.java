package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Utility class; prevent instantiation
    }

    public static ArrayList<Integer> verticalTraversal(BinaryTree.Node root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToValues = new HashMap<>();

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

        for (int col = minColumn; col <= maxColumn; col++) {
            ArrayList<Integer> values = columnToValues.get(col);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}