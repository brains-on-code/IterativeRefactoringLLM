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

        // Add root if it is not a leaf
        if (!isLeaf(root)) {
            boundary.add(root.data);
        }

        addLeftBoundary(root, boundary);
        addLeaves(root, boundary);
        addRightBoundary(root, boundary);

        return boundary;
    }

    /**
     * Adds the left boundary nodes (excluding leaves) to the result list.
     */
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.left;

        // If there is no left child, but there is a right child, start from the right child
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
     * Adds all leaf nodes to the result list using DFS.
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
     * Adds the right boundary nodes (excluding leaves) to the result list in reverse order.
     */
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundary) {
        BinaryTree.Node current = root.right;
        List<Integer> temp = new ArrayList<>();

        // If there is a right child but no left child, there is no distinct right boundary to add
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

        // Add right boundary in reverse order
        for (int i = temp.size() - 1; i >= 0; i--) {
            boundary.add(temp.get(i));
        }
    }

    /**
     * Returns true if the given node is a leaf node.
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

        // Add root if it is not a leaf
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

        // If there is a right child but no left child, there is no distinct right boundary to add
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