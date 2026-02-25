package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Performs a vertical order traversal of a binary tree from top to bottom
 * and left to right.
 *
 * <pre>
 * Example tree:
 *            1
 *           / \
 *          2   3
 *         / \   \
 *        4   5   6
 *         \     / \
 *          7   8   10
 *           \
 *            9
 *
 * Vertical order traversal:
 * 4 2 7 1 5 9 3 8 6 10
 * </pre>
 */
public final class VerticalOrderTraversal {

    private VerticalOrderTraversal() {
        // Prevent instantiation
    }

    /**
     * Computes the vertical order traversal of the given binary tree.
     *
     * The root is at column 0. For any node at column c:
     * - its left child is at column c - 1
     * - its right child is at column c + 1
     *
     * @param root the root node of the binary tree
     * @return list of node values in vertical order
     */
    public static List<Integer> verticalTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<BinaryTree.Node> nodes = new LinkedList<>();
        Queue<Integer> columns = new LinkedList<>();
        Map<Integer, List<Integer>> columnValues = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodes.offer(root);
        columns.offer(0);

        while (!nodes.isEmpty()) {
            BinaryTree.Node node = nodes.poll();
            int column = columns.poll();

            columnValues
                .computeIfAbsent(column, k -> new ArrayList<>())
                .add(node.data);

            minColumn = Math.min(minColumn, column);
            maxColumn = Math.max(maxColumn, column);

            if (node.left != null) {
                nodes.offer(node.left);
                columns.offer(column - 1);
            }

            if (node.right != null) {
                nodes.offer(node.right);
                columns.offer(column + 1);
            }
        }

        for (int col = minColumn; col <= maxColumn; col++) {
            List<Integer> values = columnValues.get(col);
            if (values != null) {
                result.addAll(values);
            }
        }

        return result;
    }
}