package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class BoundaryTraversal {

    private BoundaryTraversal() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the boundary traversal of a binary tree in anti-clockwise order:
     * root, left boundary (excluding leaves), all leaves, right boundary (excluding leaves, in reverse).
     */
    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
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
     * Adds the left boundary nodes (excluding leaves) to {@code boundary}.
     */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
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
    }

    /**
     * Adds all leaf nodes (in DFS order) to {@code boundary}.
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
     * Adds the right boundary nodes (excluding leaves) to {@code boundary} in reverse order.
     */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        List<Integer> rightBoundary = new ArrayList<>();

        if (current != null && root.left == null) {
            return;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundary.add(current.data);
            }
            current = (current.right != null) ? current.right : current.left;
        }

        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            boundary.add(rightBoundary.get(i));
        }
    }

    /**
     * Returns {@code true} if {@code node} is a leaf node.
     */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    /**
     * Iterative version of boundary traversal in anti-clockwise order.
     */
    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        // Left boundary (excluding leaves)
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

        // All leaves
        addLeaves(root, boundary);

        // Right boundary (excluding leaves) in reverse order
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