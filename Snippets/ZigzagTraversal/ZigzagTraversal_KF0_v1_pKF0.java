package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
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
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelValues = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node current = queue.poll();

                if (leftToRight) {
                    levelValues.add(current.data);
                } else {
                    levelValues.add(0, current.data);
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            result.add(levelValues);
            leftToRight = !leftToRight;
        }

        return result;
    }
}