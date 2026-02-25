package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for boundary traversal of a binary tree.
 *
 * Boundary traversal order:
 * 1. Root (if it is not a leaf)
 * 2. Left boundary (excluding leaves)
 * 3. All leaf nodes (from left to right)
 * 4. Right boundary (excluding leaves, in bottom‑up order)
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the boundary traversal of the binary tree rooted at {@code root}.
     */
    public static List<Integer> method1(BinaryTree.Node root) {
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

    /**
     * Adds the left boundary of the tree (excluding leaf nodes) to {@code boundary}.
     */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.left;

        // If there is no left subtree, start from the right subtree
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

    /**
     * Adds all leaf nodes of the tree rooted at {@code node} to {@code boundary}
     * in left‑to‑right order.
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
     * Adds the right boundary of the tree (excluding leaf nodes) to {@code boundary}
     * in bottom‑up order.
     */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        List<Integer> temp = new ArrayList<>();

        // If there is only a right subtree and no left subtree, there is no right boundary
        if (current != null && root.left == null) {
            return;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                temp.add(current.data);
            }

            if (current.right != null) {
                current = current.right;
            } else if (current.left != null) {
                current = current.left;
            } else {
                break;
            }
        }

        // Add right boundary in reverse (bottom‑up) order
        for (int i = temp.size() - 1; i >= 0; i--) {
            boundary.add(temp.get(i));
        }
    }

    /**
     * Returns {@code true} if {@code node} is a leaf node.
     */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    /**
     * Alternative implementation of boundary traversal using a stack
     * for the right boundary.
     */
    public static List<Integer> method6(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        // Add left boundary (excluding leaves)
        BinaryTree.Node current = root.left;
        if (current == null && root.right != null) {
            current = root.right;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = (current.left != null) ? current.left : current.right;
        }

        // Add all leaves
        addLeaves(root, boundary);

        // Add right boundary (excluding leaves) using a stack to reverse order
        current = root.right;
        Deque<Integer> stack = new LinkedList<>();
        if (current != null && root.left == null) {
            return boundary;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }
            current = (current.right != null) ? current.right : current.left;
        }

        while (!stack.isEmpty()) {
            boundary.add(stack.pop());
        }

        return boundary;
    }
}