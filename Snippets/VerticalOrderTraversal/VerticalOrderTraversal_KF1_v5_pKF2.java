package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Utility class for tree-related algorithms.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the vertical order traversal of a binary tree.
     *
     * Columns are processed from leftmost to rightmost. Within each column,
     * nodes are listed in the order they are visited by a breadth‑first search.
     *
     * Example tree:
     *
     *            1
     *          /   \
     *         2     3
     *        / \     \
     *       4   5     6
     *        \       / \
     *         7     8  10
     *          \
     *           9
     *
     * Vertical order: 4 2 7 1 5 9 3 8 6 10
     *
     * @param root the root node of the binary tree
     * @return list of node values in vertical order
     */
    public static ArrayList<Integer> method1(BinaryTree.Node root) {
        ArrayList<Integer> verticalOrder = new ArrayList<>();
        if (root == null) {
            return verticalOrder;
        }

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, ArrayList<Integer>> columnToValues = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.poll();
            int currentColumn = columnQueue.poll();

            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumn - 1);
            }

            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumn + 1);
            }

            columnToValues
                .computeIfAbsent(currentColumn, k -> new ArrayList<>())
                .add(currentNode.data);

            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);
        }

        for (int col = minColumn; col <= maxColumn; col++) {
            ArrayList<Integer> valuesInColumn = columnToValues.get(col);
            if (valuesInColumn != null) {
                verticalOrder.addAll(valuesInColumn);
            }
        }

        return verticalOrder;
    }
}