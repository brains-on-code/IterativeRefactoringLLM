package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Performs zigzag (spiral) level order traversal of a binary tree.
 *
 * Example tree:
 *                               7
 *                   /                         \
 *                 6                           3
 *         /                \             /             \
 *       2                  4          10               19
 *
 * Zigzag traversal:
 * [[7], [3, 6], [2, 4, 10, 19]]
 *
 * Uses breadth-first search (BFS) with a queue.
 *
 * Time complexity: O(N), where N is the number of nodes.
 * Space complexity: O(N), where N is the number of nodes.
 */
public final class ZigzagTraversal {

    private ZigzagTraversal() {
        // Prevent instantiation
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        // Current traversal direction: false = left-to-right, true = right-to-left
        boolean rightToLeft = false;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node node = queue.poll();

                if (rightToLeft) {
                    level.add(0, node.data);
                } else {
                    level.add(node.data);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            rightToLeft = !rightToLeft;
            result.add(level);
        }

        return result;
    }
}