package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given tree is traversed in a 'pre-order' way: ROOT -> LEFT -> RIGHT.
 * Below are given the recursive and iterative implementations.
 *
 * Complexities:
 * Recursive: O(n) - time, O(n) - space, where 'n' is the number of nodes in a tree.
 *
 * Iterative: O(n) - time, O(h) - space, where 'n' is the number of nodes in a tree
 * and 'h' is the height of a binary tree.
 * In the worst case 'h' can be O(n) if tree is completely unbalanced, for instance:
 * 5
 *  \
 *   6
 *    \
 *     7
 *      \
 *       8
 *
 * @author Albina Gimaletdinova on 17/02/2023
 */
public final class PreOrderTraversal {

    private PreOrderTraversal() {}

    public static List<Integer> recursivePreOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        recursivePreOrder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativePreOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new LinkedList<>();
        nodeStack.push(root);

        while (!nodeStack.isEmpty()) {
            BinaryTree.Node currentNode = nodeStack.pop();
            traversalResult.add(currentNode.data);

            if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
            }
            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
            }
        }

        return traversalResult;
    }

    private static void recursivePreOrder(BinaryTree.Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        traversalResult.add(currentNode.data);
        recursivePreOrder(currentNode.left, traversalResult);
        recursivePreOrder(currentNode.right, traversalResult);
    }
}