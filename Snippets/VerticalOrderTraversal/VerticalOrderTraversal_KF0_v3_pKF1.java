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

        Queue<BinaryTree.Node> nodesToVisit = new LinkedList<>();
        Queue<Integer> columnIndices = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToNodeValues = new HashMap<>();

        int maxColumnIndex = 0;
        int minColumnIndex = 0;

        nodesToVisit.offer(root);
        columnIndices.offer(0);

        while (!nodesToVisit.isEmpty()) {
            BinaryTree.Node currentNode = nodesToVisit.peek();
            int currentColumnIndex = columnIndices.peek();

            if (currentNode.left != null) {
                nodesToVisit.offer(currentNode.left);
                columnIndices.offer(currentColumnIndex - 1);
            }

            if (currentNode.right != null) {
                nodesToVisit.offer(currentNode.right);
                columnIndices.offer(currentColumnIndex + 1);
            }

            columnToNodeValues
                .computeIfAbsent(currentColumnIndex, key -> new ArrayList<>())
                .add(currentNode.data);

            maxColumnIndex = Math.max(maxColumnIndex, currentColumnIndex);
            minColumnIndex = Math.min(minColumnIndex, currentColumnIndex);

            nodesToVisit.poll();
            columnIndices.poll();
        }

        ArrayList<Integer> verticalOrder = new ArrayList<>();
        for (int columnIndex = minColumnIndex; columnIndex <= maxColumnIndex; columnIndex++) {
            verticalOrder.addAll(columnToNodeValues.get(columnIndex));
        }
        return verticalOrder;
    }
}