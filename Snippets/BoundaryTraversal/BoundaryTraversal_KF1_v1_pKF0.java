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
     * This is a more readable wrapper around {@link #method6(BinaryTree.Node)}.
     */
    public static List<Integer> method1(BinaryTree.Node root) {
        return method6(root);
    }

    /**
     * Performs a boundary traversal of the binary tree:
     * root -> left boundary (excluding leaves) -> leaves -> right boundary (excluding leaves, reversed).
     */
    public static List<Integer> method6(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        // Add root if it is not a leaf
        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        // Add left boundary (excluding leaves)
        addLeftBoundary(root, boundary);

        // Add all leaf nodes
        addLeaves(root, boundary);

        // Add right boundary (excluding leaves) in reverse order
        addRightBoundary(root, boundary);

        return boundary;
    }

    /** Adds the left boundary of the tree (excluding leaf nodes) to {@code boundary}. */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.left;

        if (current == null && root.right != null) {
            current = root.right;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }

            if (current.left != null) {
                current = current.left;
            } else if (current.right != null) {
                current = current.right;
            } else {
                break;
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
        } else {
            addLeaves(node.left, boundary);
            addLeaves(node.right, boundary);
        }
    }

    /** Adds the right boundary of the tree (excluding leaf nodes) to {@code boundary} in reverse order. */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        Deque<Integer> stack = new LinkedList<>();

        if (current != null && root.left == null) {
            return;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }

            if (current.right != null) {
                current = current.right;
            } else if (current.left != null) {
                current = current.left;
            } else {
                break;
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