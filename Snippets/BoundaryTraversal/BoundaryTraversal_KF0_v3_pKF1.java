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
        BinaryTree.Node currentNode = root.left;

        // If there is no left child but there is a right child, treat the right child as part of the left boundary
        if (currentNode == null && root.right != null) {
            currentNode = root.right;
        }

        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                boundaryNodes.add(currentNode.data); // Add non-leaf nodes to result
            }
            if (currentNode.left != null) {
                currentNode = currentNode.left; // Move to the left child
            } else if (currentNode.right != null) {
                currentNode = currentNode.right; // If left child is null, move to the right child
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
        BinaryTree.Node currentNode = root.right;
        List<Integer> rightBoundaryNodes = new ArrayList<>();

        // If no right boundary is present and there is no left subtree, skip
        if (currentNode != null && root.left == null) {
            return;
        }
        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                rightBoundaryNodes.add(currentNode.data); // Store non-leaf nodes temporarily
            }
            if (currentNode.right != null) {
                currentNode = currentNode.right; // Move to the right child
            } else if (currentNode.left != null) {
                currentNode = currentNode.left; // If right child is null, move to the left child
            } else {
                break; // Stop if there are no children
            }
        }

        // Add the right boundary nodes in reverse order
        for (int index = rightBoundaryNodes.size() - 1; index >= 0; index--) {
            boundaryNodes.add(rightBoundaryNodes.get(index));
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
        BinaryTree.Node currentNode = root.left;
        if (currentNode == null && root.right != null) {
            currentNode = root.right;
        }
        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                boundaryNodes.add(currentNode.data); // Add non-leaf nodes to result
            }
            currentNode = (currentNode.left != null) ? currentNode.left : currentNode.right; // Prioritize left child, move to right if left is null
        }

        // Add leaf nodes
        addLeafNodes(root, boundaryNodes);

        // Handle the right boundary using a stack (reverse order)
        currentNode = root.right;
        Deque<Integer> rightBoundaryStack = new LinkedList<>();
        if (currentNode != null && root.left == null) {
            return boundaryNodes;
        }
        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                rightBoundaryStack.push(currentNode.data); // Temporarily store right boundary nodes in a stack
            }
            currentNode = (currentNode.right != null) ? currentNode.right : currentNode.left; // Prioritize right child, move to left if right is null
        }

        // Add the right boundary nodes from the stack to maintain the correct order
        while (!rightBoundaryStack.isEmpty()) {
            boundaryNodes.add(rightBoundaryStack.pop());
        }
        return boundaryNodes;
    }
}