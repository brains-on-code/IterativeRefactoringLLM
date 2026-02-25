package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class ZigzagTraversal {

    private ZigzagTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs a zigzag (spiral) level-order traversal of a binary tree.
     *
     * <p>Nodes at each level are traversed alternately from left-to-right and
     * right-to-left.</p>
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

        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node currentNode = queue.poll();

                if (leftToRight) {
                    currentLevel.add(currentNode.data);
                } else {
                    currentLevel.add(0, currentNode.data);
                }

                if (currentNode.left != null) {
                    queue.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    queue.offer(currentNode.right);
                }
            }

            result.add(currentLevel);
            leftToRight = !leftToRight;
        }

        return result;
    }
}