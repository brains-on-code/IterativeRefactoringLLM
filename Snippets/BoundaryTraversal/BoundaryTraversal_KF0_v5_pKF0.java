package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * BoundaryTraversal
 *
 * <p>Algorithm:
 * <ul>
 *   <li>Add the root node (if not a leaf).</li>
 *   <li>Traverse and add the left boundary (excluding leaves).</li>
 *   <li>Add all leaf nodes from left to right.</li>
 *   <li>Traverse and add the right boundary (excluding leaves) in reverse order.</li>
 * </ul>
 */
public final class BoundaryTraversal {

    private BoundaryTraversal() {
        // Utility class
    }

    /** Returns the boundary traversal of the binary tree. */
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

    /** Adds the left boundary (excluding leaf nodes) to the result. */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.left != null ? root.left : root.right;

        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = current.left != null ? current.left : current.right;
        }
    }

    /** Adds all leaf nodes to the result. */
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

    /** Adds the right boundary (excluding leaf nodes) to the result in reverse order. */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        List<Integer> rightBoundary = collectRightBoundary(root);
        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            boundary.add(rightBoundary.get(i));
        }
    }

    /** Collects the right boundary nodes (excluding leaves) in top-down order. */
    private static List<Integer> collectRightBoundary(BinaryTree.Node root) {
        List<Integer> rightBoundary = new ArrayList<>();
        if (root.right == null) {
            return rightBoundary;
        }

        BinaryTree.Node current = root.right;
        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundary.add(current.data);
            }
            current = current.right != null ? current.right : current.left;
        }
        return rightBoundary;
    }

    /** Returns true if the given node is a leaf node. */
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    /** Iterative boundary traversal of the binary tree. */
    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundary = new ArrayList<>();
        if (root == null) {
            return boundary;
        }

        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        addLeftBoundaryIterative(root, boundary);
        addLeaves(root, boundary);
        addRightBoundaryIterative(root, boundary);

        return boundary;
    }

    /** Adds the left boundary iteratively (excluding leaf nodes) to the result. */
    private static void addLeftBoundaryIterative(BinaryTree.Node root, List<Integer> boundary) {
        addLeftBoundary(root, boundary);
    }

    /** Adds the right boundary iteratively (excluding leaf nodes) to the result in reverse order. */
    private static void addRightBoundaryIterative(BinaryTree.Node root, List<Integer> boundary) {
        if (root.right == null) {
            return;
        }

        BinaryTree.Node current = root.right;
        Deque<Integer> stack = new LinkedList<>();
        while (current != null) {
            if (!isLeaf(current)) {
                stack.push(current.data);
            }
            current = current.right != null ? current.right : current.left;
        }

        while (!stack.isEmpty()) {
            boundary.add(stack.pop());
        }
    }
}