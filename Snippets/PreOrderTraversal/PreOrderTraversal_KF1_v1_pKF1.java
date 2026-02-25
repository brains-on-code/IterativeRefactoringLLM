package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Preorder traversal utilities for {@link BinaryTree}.
 */
public final class PreorderTraversal {

    private PreorderTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive preorder traversal.
     *
     * @param root the root node of the tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> recursivePreorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        recursivePreorder(root, traversalResult);
        return traversalResult;
    }

    /**
     * Iterative preorder traversal using a stack.
     *
     * @param root the root node of the tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> iterativePreorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node currentNode = stack.pop();
            traversalResult.add(currentNode.data);

            if (currentNode.right != null) {
                stack.push(currentNode.right);
            }
            if (currentNode.left != null) {
                stack.push(currentNode.left);
            }
        }

        return traversalResult;
    }

    private static void recursivePreorder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        traversalResult.add(node.data);
        recursivePreorder(node.left, traversalResult);
        recursivePreorder(node.right, traversalResult);
    }
}