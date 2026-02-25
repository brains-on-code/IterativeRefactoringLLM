package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for boundary traversal of a binary tree.
 */
public final class BoundaryTraversal {

    private BoundaryTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the boundary traversal of the binary tree rooted at {@code root}.
     */
    public static List<Integer> getBoundary(BinaryTree.Node root) {
        return traverseBoundary(root);
    }

    /**
     * Performs a boundary traversal of the binary tree:
     * root -> left boundary (excluding leaves) -> leaves -> right boundary (excluding leaves, reversed).
     */
    public static List<Integer> traverseBoundary(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        addLeftBoundary(root.left, boundary);
        addLeaves(root, boundary);
        addRightBoundary(root.right, boundary);

        return boundary;
    }

    /**
     * Adds the left boundary of the tree (excluding leaf nodes) to {@code boundary}.
     */
    private static void addLeftBoundary(BinaryTree.Node node, List<Integer> boundary) {
        BinaryTree.Node current = node;

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = (current.left != null) ? current.left : current.right;
        }
    }

    /**
     * Adds all leaf nodes of the tree to {@code boundary}.
     */
    private static void addLeaves(BinaryTree.Node node, List<Integer> boundary) {
        if (node == null) {
            return;
        }

        if (isLeaf(node)) {
            boundary.add(node.data);
            return;
        }

        addLeaves(node.left, boundary);
        addLeaves(node.right, boundary);
    }

    /**
     * Adds the right boundary of the tree (excluding leaf nodes) to {@code boundary} in reverse order.
     */
    private static void addRightBoundary(BinaryTree.Node node, List<Integer> boundary) {
        if (node == null) {
            return;
        }

        Deque<Integer> stack = new LinkedList<>();
        BinaryTree.Node current = node;

        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }
            current = (current.right != null) ? current.right : current.left;
        }

        while (!stack.isEmpty()) {
            boundary.add(stack.pop());
        }
    }

    /**
     * Returns {@code true} if the given node is a leaf node.
     */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node != null && node.left == null && node.right == null;
    }
}