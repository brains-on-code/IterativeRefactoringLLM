package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class BoundaryTraversal {

    private BoundaryTraversal() {
    }

    public static List<Integer> boundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundaryNodes = new ArrayList<>();
        if (root == null) {
            return boundaryNodes;
        }

        if (!isLeaf(root)) {
            boundaryNodes.add(root.data);
        }

        addLeftBoundary(root, boundaryNodes);
        addLeaves(root, boundaryNodes);
        addRightBoundary(root, boundaryNodes);

        return boundaryNodes;
    }

    private static void addLeftBoundary(BinaryTree.Node root, List<Integer> boundaryNodes) {
        BinaryTree.Node currentNode = root.left;

        if (currentNode == null && root.right != null) {
            currentNode = root.right;
        }

        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                boundaryNodes.add(currentNode.data);
            }
            if (currentNode.left != null) {
                currentNode = currentNode.left;
            } else if (currentNode.right != null) {
                currentNode = currentNode.right;
            } else {
                break;
            }
        }
    }

    private static void addLeaves(BinaryTree.Node node, List<Integer> leafNodes) {
        if (node == null) {
            return;
        }
        if (isLeaf(node)) {
            leafNodes.add(node.data);
        } else {
            addLeaves(node.left, leafNodes);
            addLeaves(node.right, leafNodes);
        }
    }

    private static void addRightBoundary(BinaryTree.Node root, List<Integer> boundaryNodes) {
        BinaryTree.Node currentNode = root.right;
        List<Integer> rightBoundaryNodes = new ArrayList<>();

        if (currentNode != null && root.left == null) {
            return;
        }

        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                rightBoundaryNodes.add(currentNode.data);
            }
            if (currentNode.right != null) {
                currentNode = currentNode.right;
            } else if (currentNode.left != null) {
                currentNode = currentNode.left;
            } else {
                break;
            }
        }

        for (int index = rightBoundaryNodes.size() - 1; index >= 0; index--) {
            boundaryNodes.add(rightBoundaryNodes.get(index));
        }
    }

    private static boolean isLeaf(BinaryTree.Node node) {
        return node.left == null && node.right == null;
    }

    public static List<Integer> iterativeBoundaryTraversal(BinaryTree.Node root) {
        List<Integer> boundaryNodes = new ArrayList<>();
        if (root == null) {
            return boundaryNodes;
        }

        if (!isLeaf(root)) {
            boundaryNodes.add(root.data);
        }

        BinaryTree.Node currentNode = root.left;
        if (currentNode == null && root.right != null) {
            currentNode = root.right;
        }
        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                boundaryNodes.add(currentNode.data);
            }
            currentNode = (currentNode.left != null) ? currentNode.left : currentNode.right;
        }

        addLeaves(root, boundaryNodes);

        currentNode = root.right;
        Deque<Integer> rightBoundaryStack = new LinkedList<>();
        if (currentNode != null && root.left == null) {
            return boundaryNodes;
        }
        while (currentNode != null) {
            if (!isLeaf(currentNode)) {
                rightBoundaryStack.push(currentNode.data);
            }
            currentNode = (currentNode.right != null) ? currentNode.right : currentNode.left;
        }

        while (!rightBoundaryStack.isEmpty()) {
            boundaryNodes.add(rightBoundaryStack.pop());
        }
        return boundaryNodes;
    }
}