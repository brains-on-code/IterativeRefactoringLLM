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

    public static boolean check(BinaryTree.Node firstRoot, BinaryTree.Node secondRoot) {
        if (firstRoot == null && secondRoot == null) {
            return true;
        }
        if (firstRoot == null || secondRoot == null) {
            return false;
        }

        Deque<BinaryTree.Node> firstTreeQueue = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeQueue = new ArrayDeque<>();
        firstTreeQueue.add(firstRoot);
        secondTreeQueue.add(secondRoot);

        while (!firstTreeQueue.isEmpty() && !secondTreeQueue.isEmpty()) {
            BinaryTree.Node firstCurrentNode = firstTreeQueue.poll();
            BinaryTree.Node secondCurrentNode = secondTreeQueue.poll();

            if (!haveSameValue(firstCurrentNode, secondCurrentNode)) {
                return false;
            }

            if (firstCurrentNode != null) {
                if (!haveSameValue(firstCurrentNode.left, secondCurrentNode.left)) {
                    return false;
                }
                if (firstCurrentNode.left != null) {
                    firstTreeQueue.add(firstCurrentNode.left);
                    secondTreeQueue.add(secondCurrentNode.left);
                }

                if (!haveSameValue(firstCurrentNode.right, secondCurrentNode.right)) {
                    return false;
                }
                if (firstCurrentNode.right != null) {
                    firstTreeQueue.add(firstCurrentNode.right);
                    secondTreeQueue.add(secondCurrentNode.right);
                }
            }
        }
        return true;
    }

    private static boolean haveSameValue(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}