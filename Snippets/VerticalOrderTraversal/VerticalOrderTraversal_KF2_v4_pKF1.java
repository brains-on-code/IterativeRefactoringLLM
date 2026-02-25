package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {}

    public static ArrayList<Integer> verticalTraversal(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToValues = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node currentNode = nodes.peek();
            int currentColumn = columns.peek();

            if (currentNode.left != null) {
                nodes.offer(currentNode.left);
                columns.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodes.offer(currentNode.right);
                columns.offer(currentColumn + 1);
            }

            columnToValues
                .computeIfAbsent(currentColumn, key -> new ArrayList<>())
                .add(currentNode.data);

            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);

            nodes.poll();
            columns.poll();
        }

        ArrayList<Integer> verticalOrder = new ArrayList<>();
        for (int column = minColumn; column <= maxColumn; column++) {
            verticalOrder.addAll(columnToValues.get(column));
        }

        return verticalOrder;
    }
}