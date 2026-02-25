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
        // Utility class
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
     * Adds the left boundary (excluding leaves) to result.
     * If a node has no left child but has a right child, that right child is treated
     * as part of the left boundary.
     */
    private static void addLeftBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node cur = node.left;

        if (cur == null && node.right != null) {
            cur = node.right;
        }

        while (cur != null) {
            if (!isLeaf(cur)) {
                result.add(cur.data);
            }
            if (cur.left != null) {
                cur = cur.left;
            } else if (cur.right != null) {
                cur = cur.right;
            } else {
                break;
            }
        }
    }

    /**
     * Adds all leaf nodes (left to right) to result.
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
     * Adds the right boundary (excluding leaves) to result in reverse order.
     */
    private static void addRightBoundary(BinaryTree.Node node, List<Integer> result) {
        BinaryTree.Node cur = node.right;
        List<Integer> temp = new ArrayList<>();

        if (cur != null && node.left == null) {
            return;
        }

        while (cur != null) {
            if (!isLeaf(cur)) {
                temp.add(cur.data);
            }
            if (cur.right != null) {
                cur = cur.right;
            } else if (cur.left != null) {
                cur = cur.left;
            } else {
                break;
            }
        }

        for (int i = temp.size() - 1; i >= 0; i--) {
            result.add(temp.get(i));
        }
    }

    /**
     * Returns true if the node is a leaf (no children).
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
        BinaryTree.Node cur = root.left;
        if (cur == null && root.right != null) {
            cur = root.right;
        }
        while (cur != null) {
            if (!isLeaf(cur)) {
                result.add(cur.data);
            }
            cur = (cur.left != null) ? cur.left : cur.right;
        }

        // Leaves
        addLeaves(root, result);

        // Right boundary (excluding leaves, added in reverse using a stack)
        cur = root.right;
        Deque<Integer> stack = new LinkedList<>();
        if (cur != null && root.left == null) {
            return result;
        }
        while (cur != null) {
            if (!isLeaf(cur)) {
                stack.push(cur.data);
            }
            cur = (cur.right != null) ? cur.right : cur.left;
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        return result;
    }
}