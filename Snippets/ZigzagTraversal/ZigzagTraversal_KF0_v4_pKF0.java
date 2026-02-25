package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given a binary tree, returns the zigzag level order traversal of its nodes' values.
 *
 * Binary tree:
 *                               7
 *                   /                         \
 *                6                           3
 *         /                \             /             \
 *      2                    4         10                19
 *
 * Zigzag traversal:
 * [[7], [3, 6], [2, 4, 10, 19]]
 *
 * This solution implements the breadth-first search (BFS) algorithm using a queue.
 *
 * Complexities:
 * O(N) - time, where N is the number of nodes in a binary tree
 * O(N) - space, where N is the number of nodes in a binary tree
 *
 * @author Albina Gimaletdinova on 11/01/2023
 */
public final class ZigzagTraversal {

    private ZigzagTraversal() {
        // Utility class
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        boolean isLeftToRight = true;

        while (!nodeQueue.isEmpty()) {
            int levelSize = nodeQueue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();
                if (currentNode == null) {
                    continue;
                }

                int value = currentNode.data;
                if (isLeftToRight) {
                    currentLevel.add(value);
                } else {
                    currentLevel.add(0, value);
                }

                if (currentNode.left != null) {
                    nodeQueue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodeQueue.offer(currentNode.right);
                }
            }

            result.add(currentLevel);
            isLeftToRight = !isLeftToRight;
        }

        return result;
    }
}