package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * BoundaryTraversal
 * <p>
 * Start with the Root:
 * Add the root node to the boundary list.
 * Traverse the Left Boundary (Excluding Leaf Nodes):
 * Move down the left side of the tree, adding each non-leaf node to the boundary list.
 * If a node has a left child, go left; otherwise, go right.
 * Visit All Leaf Nodes:
 * Traverse the tree and add all leaf nodes to the boundary list, from left to right.
 * Traverse the Right Boundary (Excluding Leaf Nodes) in Reverse Order:
 * Move up the right side of the tree, adding each non-leaf node to a temporary list.
 * If a node has a right child, go right; otherwise, go left.
 * Reverse the temporary list and add it to the boundary list.
 * Combine and Output:
 * The final boundary list contains the root, left boundary, leaf nodes, and reversed right boundary in that order.
 */
public final class BoundaryTraversal {

    private BoundaryTraversal() {
    }

    // Main function for boundary traversal, returns a list of boundary nodes in order
    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundaryNodes = new ArrayList<>();
        if (root == null) {
            return boundaryNodes;
        }

        // Add root node if it's not a leaf node
        if (!isLeaf(root)) {
            boundaryNodes.add(root.data);
        }

        // Add left boundary
        addLeftBoundary(root, boundaryNodes);

        // Add leaf nodes
        addLeafNodes(root, boundaryNodes);

        // Add right boundary
        addRightBoundary(root, boundaryNodes);

        return boundaryNodes;
    }

    // Adds the left boundary, including nodes that have no left child but have a right child
    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundaryNodes) {
        BinaryTree.Node current = root.left;

        // If there is no left child but there is a right child, treat the right child as part of the left boundary
        if (current == null && root.right != null) {
            current = root.right;
        }

        while (current != null) {
            if (!isLeaf(current)) {
                boundaryNodes.add(current.data); // Add non-leaf nodes to result
            }
            if (current.left != null) {
                current = current.left; // Move to the left child
            } else if (current.right != null) {
                current = current.right; // If left child is null, move to the right child
            } else {
                break; // Stop if there are no children
            }
        }
    }

    // Adds leaf nodes (nodes without children)
    private static void addLeafNodes(BinaryTree.Node node, List<Integer> boundaryNodes) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            boundaryNodes.add(node.data); // Add leaf node
        } else {
            addLeafNodes(node.left, boundaryNodes); // Recur for left subtree
            addLeafNodes(node.right, boundaryNodes); // Recur for right subtree
        }
    }

    // Adds the right boundary, excluding leaf nodes
    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundaryNodes) {
        BinaryTree.Node current = root.right;
        List<Integer> rightBoundaryNodes = new ArrayList<>();

        // If no right boundary is present and there is no left subtree, skip
        if (current != null && root.left == null) {
            return;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundaryNodes.add(current.data); // Store non-leaf nodes temporarily
            }
            if (current.right != null) {
                current = current.right; // Move to the right child
            } else if (current.left != null) {
                current = current.left; // If right child is null, move to the left child
            } else {
                break; // Stop if there are no children
            }
        }

        // Add the right boundary nodes in reverse order
        for (int i = rightBoundaryNodes.size() - 1; i >= 0; i--) {
            boundaryNodes.add(rightBoundaryNodes.get(i));
        }
    }

    // Checks if a node is a leaf node
    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    // Iterative boundary traversal
    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundaryNodes = new ArrayList<>();
        if (root == null) {
            return boundaryNodes;
        }

        // Add root node if it's not a leaf node
        if (!isLeaf(root)) {
            boundaryNodes.add(root.data);
        }

        // Handle the left boundary
        BinaryTree.Node current = root.left;
        if (current == null && root.right != null) {
            current = root.right;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                boundaryNodes.add(current.data); // Add non-leaf nodes to result
            }
            current = (current.left != null) ? current.left : current.right; // Prioritize left child, move to right if left is null
        }

        // Add leaf nodes
        addLeafNodes(root, boundaryNodes);

        // Handle the right boundary using a stack (reverse order)
        current = root.right;
        Deque<Integer> rightBoundaryStack = new LinkedList<>();
        if (current != null && root.left == null) {
            return boundaryNodes;
        }
        while (current != null) {
            if (!isLeaf(current)) {
                rightBoundaryStack.push(current.data); // Temporarily store right boundary nodes in a stack
            }
            current = (current.right != null) ? current.right : current.left; // Prioritize right child, move to left if right is null
        }

        // Add the right boundary nodes from the stack to maintain the correct order
        while (!rightBoundaryStack.isEmpty()) {
            boundaryNodes.add(rightBoundaryStack.pop());
        }
        return boundaryNodes;
    }
}