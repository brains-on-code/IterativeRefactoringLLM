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
        // Utility class; prevent instantiation
    }

    /**
     * Returns the vertical order traversal of a binary tree.
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
        if (root == null) {
            return new ArrayList<>();
        }

        // Queue for BFS over tree nodes
        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        // Parallel queue to track horizontal distance (column index) of each node
        Queue<Integer> columnQueue = new LinkedList<>();
        // Map from column index to list of node values in that column
        Map<Integer, ArrayList<Integer>> columnMap = new HashMap<>();

        int maxColumn = 0;
        int minColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.peek();
            int currentColumn = columnQueue.peek();

            // Enqueue left child with column - 1
            if (currentNode.left != null) {
                nodeQueue.offer(currentNode.left);
                columnQueue.offer(currentColumn - 1);
            }

            // Enqueue right child with column + 1
            if (currentNode.right != null) {
                nodeQueue.offer(currentNode.right);
                columnQueue.offer(currentColumn + 1);
            }

            // Initialize list for this column if needed
            columnMap.computeIfAbsent(currentColumn, k -> new ArrayList<>());

            // Add current node's value to its column list
            columnMap.get(currentColumn).add(currentNode.data);

            // Track min and max column indices
            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);

            // Move to next node in BFS
            nodeQueue.poll();
            columnQueue.poll();
        }

        // Collect results from leftmost column to rightmost column
        ArrayList<Integer> result = new ArrayList<>();
        for (int col = minColumn; col <= maxColumn; col++) {
            result.addAll(columnMap.get(col));
        }

        return result;
    }
}