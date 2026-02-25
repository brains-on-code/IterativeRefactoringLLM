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
        Queue<Integer> columnIndices = new LinkedList<>();
        Map<Integer, List<Integer>> columns = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodes.offer(root);
        columnIndices.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node currentNode = nodes.poll();
            int currentColumn = columnIndices.poll();

            columns.computeIfAbsent(currentColumn, k -> new ArrayList<>()).add(currentNode.data);

            minColumn = Math.min(minColumn, currentColumn);
            maxColumn = Math.max(maxColumn, currentColumn);

            if (currentNode.left != null) {
                nodes.offer(currentNode.left);
                columnIndices.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodes.offer(currentNode.right);
                columnIndices.offer(currentColumn + 1);
            }
        }

        for (int col = minColumn; col <= maxColumn; col++) {
            List<Integer> columnValues = columns.get(col);
            if (columnValues != null) {
                result.addAll(columnValues);
            }
        }

        return result;
    }
}