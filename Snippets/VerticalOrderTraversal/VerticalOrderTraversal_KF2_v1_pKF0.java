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
        Queue<Integer> indexQueue = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnMap = new HashMap<>();

        int minIndex = 0;
        int maxIndex = 0;

        nodeQueue.offer(root);
        indexQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.poll();
            int currentIndex = indexQueue.poll();

            columnMap.computeIfAbsent(currentIndex, k -> new ArrayList<>()).add(currentNode.data);

            minIndex = Math.min(minIndex, currentIndex);
            maxIndex = Math.max(maxIndex, currentIndex);

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                indexQueue.offer(currentIndex - 1);
            }

            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                indexQueue.offer(currentIndex + 1);
            }
        }

        for (int i = minIndex; i <= maxIndex; i++) {
            ArrayList<Integer> column = columnMap.get(i);
            if (column != null) {
                result.addAll(column);
            }
        }

        return result;
    }
}