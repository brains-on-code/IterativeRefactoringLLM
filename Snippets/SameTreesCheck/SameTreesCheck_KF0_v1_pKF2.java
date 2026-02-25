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
        // Utility class; prevent instantiation.
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

        Deque<BinaryTree.Node> queue1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> queue2 = new ArrayDeque<>();
        queue1.add(p);
        queue2.add(q);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            BinaryTree.Node first = queue1.poll();
            BinaryTree.Node second = queue2.poll();

            if (!equalNodes(first, second)) {
                return false;
            }

            if (first != null) {
                if (!equalNodes(first.left, second.left)) {
                    return false;
                }
                if (first.left != null) {
                    queue1.add(first.left);
                    queue2.add(second.left);
                }

                if (!equalNodes(first.right, second.right)) {
                    return false;
                }
                if (first.right != null) {
                    queue1.add(first.right);
                    queue2.add(second.right);
                }
            }
        }

        return true;
    }

    /**
     * Returns {@code true} if both nodes are null, or both are non-null and contain equal data.
     */
    private static boolean equalNodes(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.data == q.data;
    }
}