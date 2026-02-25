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

        Deque<BinaryTree.Node> firstTreeNodes = new ArrayDeque<>();
        Deque<BinaryTree.Node> secondTreeNodes = new ArrayDeque<>();
        firstTreeNodes.add(firstRoot);
        secondTreeNodes.add(secondRoot);

        while (!firstTreeNodes.isEmpty() && !secondTreeNodes.isEmpty()) {
            BinaryTree.Node firstCurrent = firstTreeNodes.poll();
            BinaryTree.Node secondCurrent = secondTreeNodes.poll();

            if (!nodesHaveSameValue(firstCurrent, secondCurrent)) {
                return false;
            }

            if (firstCurrent != null) {
                if (!nodesHaveSameValue(firstCurrent.left, secondCurrent.left)) {
                    return false;
                }
                if (firstCurrent.left != null) {
                    firstTreeNodes.add(firstCurrent.left);
                    secondTreeNodes.add(secondCurrent.left);
                }

                if (!nodesHaveSameValue(firstCurrent.right, secondCurrent.right)) {
                    return false;
                }
                if (firstCurrent.right != null) {
                    firstTreeNodes.add(firstCurrent.right);
                    secondTreeNodes.add(secondCurrent.right);
                }
            }
        }
        return true;
    }

    private static boolean nodesHaveSameValue(BinaryTree.Node firstNode, BinaryTree.Node secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }
        return firstNode.data == secondNode.data;
    }
}