package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class ZigzagTraversal {

    private ZigzagTraversal() {
        // Prevent instantiation
    }

    /**
     * Returns the zigzag (spiral) level-order traversal of a binary tree.
     *
     * @param root the root node of the binary tree
     * @return a list of levels, where each level is a list of node values
     */
    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        // true: traverse current level left-to-right; false: right-to-left
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node node = queue.poll();

                if (leftToRight) {
                    level.add(node.data);
                } else {
                    level.add(0, node.data);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            result.add(level);
            leftToRight = !leftToRight;
        }

        return result;
    }
}