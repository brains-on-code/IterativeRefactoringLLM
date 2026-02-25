package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Utility class
    }

    public static ArrayList<Integer> verticalTraversal(BinaryTree.Node root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnMap = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.poll();
            int currentColumn = columnQueue.poll();

            columnMap.computeIfAbsent(currentColumn, k -> new ArrayList<>())
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

        for (int col = minColumn; col <= maxColumn; col++) {
            result.addAll(columnMap.get(col));
        }

        return result;
    }
}