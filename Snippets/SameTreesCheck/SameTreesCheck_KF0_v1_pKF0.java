package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given 2 binary trees.
 * This code checks whether they are the same (structurally identical and have the same values) or
 * not. <p> Example:
 * 1. Binary trees:
 *      1                 1
 *     / \               / \
 *    2   3             2   3
 *   /\   /\           /\   /\
 *  4  5 6  7         4  5 6  7
 * These trees are the same, so the code returns 'true'.
 * <p>
 * 2. Binary trees:
 *      1   1
 *     /     \
 *    2       2
 * These trees are NOT the same (the structure differs), so the code returns 'false'.
 * <p>
 * This solution implements the breadth-first search (BFS) algorithm.
 * For each tree we create a queue and iterate the trees using these queues.
 * On each step we check the nodes for equality, and if the nodes are not the same, return false.
 * Otherwise, add children nodes to the queues and continue traversing the trees.
 * <p>
 * Complexities:
 * O(N) - time, where N is the number of nodes in a binary tree,
 * O(N) - space, where N is the number of nodes in a binary tree.
 *
 * @author Albina Gimaletdinova on 13/01/2023
 */
public final class SameTreesCheck {

    private SameTreesCheck() {
        // Utility class
    }

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
            BinaryTree.Node node1 = queue1.poll();
            BinaryTree.Node node2 = queue2.poll();

            if (!nodesEqual(node1, node2)) {
                return false;
            }

            if (node1 == null) {
                continue;
            }

            if (!nodesEqual(node1.left, node2.left)) {
                return false;
            }
            if (node1.left != null) {
                queue1.add(node1.left);
                queue2.add(node2.left);
            }

            if (!nodesEqual(node1.right, node2.right)) {
                return false;
            }
            if (node1.right != null) {
                queue1.add(node1.right);
                queue2.add(node2.right);
            }
        }

        return queue1.isEmpty() && queue2.isEmpty();
    }

    private static boolean nodesEqual(BinaryTree.Node p, BinaryTree.Node q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.data == q.data;
    }
}