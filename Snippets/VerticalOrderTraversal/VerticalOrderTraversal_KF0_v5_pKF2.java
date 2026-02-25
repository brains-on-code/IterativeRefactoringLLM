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

    private VerticalOrderTraversal() {}

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

        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        Queue<Integer> columnQueue = new LinkedList<>();
        Map<Integer, List<Integer>> columnToValues = new HashMap<>();

        int minColumn = 0;
        int maxColumn = 0;

        nodeQueue.offer(root);
        columnQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            BinaryTree.Node currentNode = nodeQueue.poll();
            int currentColumn = columnQueue.poll();

            columnToValues
                .computeIfAbsent(currentColumn, k -> new ArrayList<>())
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
            List<Integer> valuesInColumn = columnToValues.get(col);
            if (valuesInColumn != null) {
                result.addAll(valuesInColumn);
            }
        }

        return result;
    }
}