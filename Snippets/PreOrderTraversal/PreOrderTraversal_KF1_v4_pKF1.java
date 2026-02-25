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
        List<Integer> preorderValues = new ArrayList<>();
        recursivePreorder(root, preorderValues);
        return preorderValues;
    }

    /**
     * Iterative preorder traversal using a stack.
     *
     * @param root the root node of the tree
     * @return list of node values in preorder (root, left, right)
     */
    public static List<Integer> iterativePreorder(BinaryTree.Node root) {
        List<Integer> preorderValues = new ArrayList<>();
        if (root == null) {
            return preorderValues;
        }

        Deque<BinaryTree.Node> nodeStack = new LinkedList<>();
        nodeStack.push(root);

        while (!nodeStack.isEmpty()) {
            BinaryTree.Node currentNode = nodeStack.pop();
            preorderValues.add(currentNode.data);

            if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
            }
            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
            }
        }

        return preorderValues;
    }

    private static void recursivePreorder(BinaryTree.Node currentNode, List<Integer> preorderValues) {
        if (currentNode == null) {
            return;
        }
        preorderValues.add(currentNode.data);
        recursivePreorder(currentNode.left, preorderValues);
        recursivePreorder(currentNode.right, preorderValues);
    }
}