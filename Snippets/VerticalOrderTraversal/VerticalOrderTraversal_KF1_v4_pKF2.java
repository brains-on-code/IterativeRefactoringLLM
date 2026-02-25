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
        // Prevent instantiation
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

            columnValues
                .computeIfAbsent(currentColumn, k -> new ArrayList<>())
                .add(currentNode.data);

            maxColumn = Math.max(maxColumn, currentColumn);
            minColumn = Math.min(minColumn, currentColumn);

            nodes.poll();
            columns.poll();
        }

        ArrayList<Integer> result = new ArrayList<>();
        for (int col = minColumn; col <= maxColumn; col++) {
            result.addAll(columnValues.get(col));
        }

        return result;
    }
}