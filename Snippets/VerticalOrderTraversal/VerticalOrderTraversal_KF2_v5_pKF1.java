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

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToNodesMap = new HashMap<>();

        int maxColumnIndex = 0;
        int minColumnIndex = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.peek();
            int currentColumnIndex = columnQueue.peek();

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumnIndex - 1);
            }

            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumnIndex + 1);
            }

            columnToNodesMap
                .computeIfAbsent(currentColumnIndex, key -> new ArrayList<>())
                .add(currentNode.data);

            maxColumnIndex = Math.max(maxColumnIndex, currentColumnIndex);
            minColumnIndex = Math.min(minColumnIndex, currentColumnIndex);

            nodeQueue.poll();
            columnQueue.poll();
        }

        ArrayList<Integer> verticalOrderTraversalResult = new ArrayList<>();
        for (int columnIndex = minColumnIndex; columnIndex <= maxColumnIndex; columnIndex++) {
            verticalOrderTraversalResult.addAll(columnToNodesMap.get(columnIndex));
        }

        return verticalOrderTraversalResult;
    }
}