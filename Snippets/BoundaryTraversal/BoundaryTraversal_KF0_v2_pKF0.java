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
        if (root.right == null) {
            return;
        }

        BinaryTree.Node current = root.right;
        List<Integer> temp = new ArrayList<>();

        while (current != null) {
            if (!isLeaf(current)) {
                temp.add(current.data);
            }
            current = current.right != null ? current.right : current.left;
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            boundary.add(temp.get(i));
        }
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

        // Left boundary
        BinaryTree.Node current = root.left != null ? root.left : root.right;
        while (current != null) {
            if (!isLeaf(current)) {
                boundary.add(current.data);
            }
            current = current.left != null ? current.left : current.right;
        }

        // Leaves
        addLeaves(root, boundary);

        // Right boundary (using stack for reverse order)
        if (root.right == null) {
            return boundary;
        }

        current = root.right;
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

        return boundary;
    }
}