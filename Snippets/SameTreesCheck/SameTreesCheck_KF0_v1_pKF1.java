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

    private SameTreesCheck() {}

    public static boolean check(BinaryTree.Node root1, BinaryTree.Node root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        Deque<BinaryTree.Node> queue1 = new ArrayDeque<>();
        Deque<BinaryTree.Node> queue2 = new ArrayDeque<>();
        queue1.add(root1);
        queue2.add(root2);

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            BinaryTree.Node currentNode1 = queue1.poll();
            BinaryTree.Node currentNode2 = queue2.poll();

            if (!nodesAreEqual(currentNode1, currentNode2)) {
                return false;
            }

            if (currentNode1 != null) {
                if (!nodesAreEqual(currentNode1.left, currentNode2.left)) {
                    return false;
                }
                if (currentNode1.left != null) {
                    queue1.add(currentNode1.left);
                    queue2.add(currentNode2.left);
                }

                if (!nodesAreEqual(currentNode1.right, currentNode2.right)) {
                    return false;
                }
                if (currentNode1.right != null) {
                    queue1.add(currentNode1.right);
                    queue2.add(currentNode2.right);
                }
            }
        }
        return true;
    }

    private static boolean nodesAreEqual(BinaryTree.Node node1, BinaryTree.Node node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return node1.data == node2.data;
    }
}