package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for binary tree operations.
 *
 * <p>Provides a method to perform a zigzag (spiral) level-order traversal on a
 * binary tree.</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Performs a zigzag (spiral) level-order traversal of a binary tree.
     *
     * <p>Returns a list of levels, where each level is represented as a list of
     * node values. The traversal alternates between left-to-right and
     * right-to-left order at each level.</p>
     *
     * <pre>
     * Example tree:
     *         7
     *       /   \
     *      6     3
     *     / \   / \
     *    2   4 10 19
     *
     * Zigzag level order result:
     * [[7], [3, 6], [2, 4, 10, 19]]
     * </pre>
     *
     * @param root the root node of the binary tree
     * @return a list of lists of integers representing the zigzag level order
     *         traversal; an empty list if {@code root} is {@code null}
     */
    public static List<List<Integer>> method1(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean traverseRightToLeft = false;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node current = queue.poll();

                if (traverseRightToLeft) {
                    currentLevel.add(0, current.data);
                } else {
                    currentLevel.add(current.data);
                }

                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }

            traverseRightToLeft = !traverseRightToLeft;
            result.add(currentLevel);
        }

        return result;
    }
}