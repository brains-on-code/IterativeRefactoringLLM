package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for checking whether two binary trees are identical.
 *
 * <p>Two trees are considered the same if:
 * <ul>
 *   <li>They have the same structure, and</li>
 *   <li>All corresponding nodes contain equal values.</li>
 * </ul>
 *
 * <p>The algorithm uses breadth-first search (BFS) and compares nodes level by level.
 *
 * <p>Time complexity: O(N), where N is the number of nodes.
 * Space complexity: O(N), for the queues used in BFS.
 */
public final class SameTreesCheck {

    private SameTreesCheck() {
        // Prevent instantiation of utility class.
    }

    /**
     * Checks whether two binary trees are identical.
     *
     * @param p root of the first tree
     * @param q root of the second tree
     * @return {@code true} if the trees are identical, {@code false} otherwise
     */
    public static boolean check(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondQueue = new ArrayDeque<>();
        firstQueue.add(p);
        secondQueue.add(q);

        while (!firstQueue.isEmpty() && !secondQueue.isEmpty()) {
            BinaryTree.Node firstNode = firstQueue.poll();
            BinaryTree.Node secondNode = secondQueue.poll();

            if (!nodesEqual(firstNode, secondNode)) {
                return false;
            }

            if (!nodesEqual(firstNode.left, secondNode.left)) {
                return false;
            }
            if (firstNode.left != null) {
                firstQueue.add(firstNode.left);
                secondQueue.add(secondNode.left);
            }

            if (!nodesEqual(firstNode.right, secondNode.right)) {
                return false;
            }
            if (firstNode.right != null) {
                firstQueue.add(firstNode.right);
                secondQueue.add(secondNode.right);
            }
        }

        return firstQueue.isEmpty() && secondQueue.isEmpty();
    }

    /**
     * Returns {@code true} if both nodes are {@code null}, or both are non-{@code null}
     * and contain equal data.
     */
    private static boolean nodesEqual(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}