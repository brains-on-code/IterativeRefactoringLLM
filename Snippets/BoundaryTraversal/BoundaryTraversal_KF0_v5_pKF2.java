package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Performs boundary traversal of a binary tree.
 *
 * Order:
 * 1. Root
 * 2. Left boundary (excluding leaves)
 * 3. All leaves (left to right)
 * 4. Right boundary (excluding leaves, added in reverse)
 */
public final class BoundaryTraversal {

    private BoundaryTraversal() {
        // Utility class; prevent instantiation.
    }

    /**
     * Recursive boundary traversal.
     *
     * @param root root of the binary tree
     * @return list of node values in boundary order
     */
    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        if (!isLeaf(root)) {
            result.add(root.data);
        }

        addLeftBoundary(root, result);
        addLeaves(root, result);
        addRightBoundary(root, result);

        return result;
    }

    /**
     * Adds the left boundary (excluding leaves) to {@code result}.
     * If a node has no left child but has a right child, that right child is
     * treated as part of the left boundary.
     */
    private static void addLeftBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node current = node.left != null ? node.left : node.right;

        while (current != null) {
            if (!isLeaf(current)) {
                result.add(current.data);
            }
            current = current.left != null ? current.left : current.right;
        }
    }

    /**
     * Adds all leaf nodes (left to right) to {@code result}.
     */
    private static void addLeaves(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            result.add(node.data);
            return;
        }
        addLeaves(node.left, result);
        addLeaves(node.right, result);
    }

    /**
     * Adds the right boundary (excluding leaves) to {@code result} in reverse order.
     */
    private static void addRightBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node current = node.right;
        List<Integer> rightBoundary = new ArrayList<>();

        // Avoid duplicating the left boundary when the tree is skewed to the right.
        if (current != null && node.left == null) {
            return;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundary.add(current.data);
            }
            current = current.right != null ? current.right : current.left;
        }

        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            result.add(rightBoundary.get(i));
        }
    }

    /**
     * Returns {@code true} if the node is a leaf (no children).
     */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    /**
     * Iterative boundary traversal.
     *
     * @param root root of the binary tree
     * @return list of node values in boundary order
     */
    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        if (!isLeaf(root)) {
            result.add(root.data);
        }

        // Left boundary (excluding leaves)
        BinaryTree.Node current = root.left != null ? root.left : root.right;
        while (current != null) {
            if (!isLeaf(current)) {
                result.add(current.data);
            }
            current = current.left != null ? current.left : current.right;
        }

        // Leaves
        addLeaves(root, result);

        // Right boundary (excluding leaves, added in reverse using a stack)
        current = root.right;
        Deque<Integer> stack = new LinkedList<>();

        // Avoid duplicating the left boundary when the tree is skewed to the right.
        if (current != null && root.left == null) {
            return result;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }
            current = current.right != null ? current.right : current.left;
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }
}