package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for boundary traversal of a binary tree.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the boundary traversal of the binary tree rooted at {@code root}.
     * This is a more readable wrapper around {@link #traverseBoundary(BinaryTree.Node)}.
     */
    public static List<Integer> method1(BinaryTree.Node root) {
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

        addLeftBoundary(root, boundary);
        addLeaves(root, boundary);
        addRightBoundary(root, boundary);

        return boundary;
    }

    /** Adds the left boundary of the tree (excluding leaf nodes) to {@code boundary}. */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.left != null ? root.left : root.right;

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }

            if (current.left != null) {
                current = current.left;
            } else if (current.right != null) {
                current = current.right;
            } else {
                current = null;
            }
        }
    }

    /** Adds all leaf nodes of the tree to {@code boundary}. */
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

    /** Adds the right boundary of the tree (excluding leaf nodes) to {@code boundary} in reverse order. */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        if (current == null || root.left == null && isLeaf(current)) {
            return;
        }

        Deque<Integer> stack = new LinkedList<>();

        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }

            if (current.right != null) {
                current = current.right;
            } else if (current.left != null) {
                current = current.left;
            } else {
                current = null;
            }
        }

        while (!stack.isEmpty()) {
            boundary.add(stack.pop());
        }
    }

    /** Returns {@code true} if the given node is a leaf node. */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }
}