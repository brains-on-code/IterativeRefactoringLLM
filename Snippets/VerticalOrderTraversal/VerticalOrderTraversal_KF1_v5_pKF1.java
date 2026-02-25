package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
    }

    public static ArrayList<Integer> getVerticalOrder(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToNodesMap = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.peek();
            int currentColumn = columnQueue.peek();

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumn - 1);
            }
            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumn + 1);
            }

            columnToNodesMap
                .computeIfAbsent(currentColumn, key -> new ArrayList<>())
                .add(currentNode.data);

            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);

            columnQueue.poll();
            nodeQueue.poll();
        }

        ArrayList<Integer> verticalOrder = new ArrayList<>();
        for (int column = minColumn; column <= maxColumn; column++) {
            verticalOrder.addAll(columnToNodesMap.get(column));
        }
        return verticalOrder;
    }
}