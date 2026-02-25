package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
    }

    public static ArrayList<Integer> verticalTraversal(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnValues = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node node = nodes.peek();
            int column = columns.peek();

            if (node.left != null) {
                nodes.offer(node.left);
                columns.offer(column - 1);
            }

            if (node.right != null) {
                nodes.offer(node.right);
                columns.offer(column + 1);
            }

            columnValues
                .computeIfAbsent(column, key -> new ArrayList<>())
                .add(node.data);

            maxColumn = Math.max(maxColumn, column);
            minColumn = Math.min(minColumn, column);

            nodes.poll();
            columns.poll();
        }

        ArrayList<Integer> verticalOrder = new ArrayList<>();
        for (int column = minColumn; column <= maxColumn; column++) {
            verticalOrder.addAll(columnValues.get(column));
        }
        return verticalOrder;
    }
}