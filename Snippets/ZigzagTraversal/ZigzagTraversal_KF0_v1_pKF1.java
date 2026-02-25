package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree.
 * This code returns the zigzag level order traversal of its nodes' values.
 * Binary tree:
 *                               7
 *                   /                         \
 *                6                           3
 *         /                \             /             \
 *      2                    4         10                19
 * Zigzag traversal:
 * [[7], [3, 6], [2, 4, 10, 19]]
 * <p>
 * This solution implements the breadth-first search (BFS) algorithm using a queue.
 * 1. The algorithm starts with a root node. This node is added to a queue.
 * 2. While the queue is not empty:
 *  - each time we enter the while-loop we get queue size. Queue size refers to the number of nodes
 * at the current level.
 *  - we traverse all the level nodes in 2 ways: from left to right OR from right to left
 *    (this state is stored on `isLeftToRight` variable)
 *  - if the current node has children we add them to a queue
 *  - add level with nodes to a result.
 * <p>
 * Complexities:
 * O(N) - time, where N is the number of nodes in a binary tree
 * O(N) - space, where N is the number of nodes in a binary tree
 *
 * @author Albina Gimaletdinova on 11/01/2023
 */
public final class ZigzagTraversal {

    private ZigzagTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> zigzagLevels = new ArrayList<>();
        Deque<BinaryTree.Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);

        boolean isLeftToRight = false;

        while (!nodeQueue.isEmpty()) {
            int currentLevelSize = nodeQueue.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < currentLevelSize; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();

                if (isLeftToRight) {
                    currentLevelValues.add(0, currentNode.data);
                } else {
                    currentLevelValues.add(currentNode.data);
                }

                if (currentNode.left != null) {
                    nodeQueue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodeQueue.offer(currentNode.right);
                }
            }

            isLeftToRight = !isLeftToRight;
            zigzagLevels.add(currentLevelValues);
        }

        return zigzagLevels;
    }
}