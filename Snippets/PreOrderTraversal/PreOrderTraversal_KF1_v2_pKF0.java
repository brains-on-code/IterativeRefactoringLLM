package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for preorder traversal of a binary tree.
 *
 * Provides both recursive and iterative implementations.
 */
public final class PreorderTraversal {

    private PreorderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive preorder traversal.
     *
     * @param root the root node of the binary tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> recursivePreorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        performRecursivePreorder(root, traversalResult);
        return traversalResult;
    }

    /**
     * Iterative preorder traversal using a stack.
     *
     * @param root the root node of the binary tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> iterativePreorder(BinaryTree.Node root) {
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

    private static void performRecursivePreorder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }

        traversalResult.add(node.data);
        performRecursivePreorder(node.left, traversalResult);
        performRecursivePreorder(node.right, traversalResult);
    }
}