package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/** Node of a binary tree. */
class TreeNode {

    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.value = value;
    }
}

/**
 * Pair of a tree node and its horizontal distance from the root.
 *
 * Horizontal distance (HD) is defined as:
 * - root has HD = 0
 * - left child has HD = parent HD - 1
 * - right child has HD = parent HD + 1
 */
record NodeWithDistance(TreeNode node, int horizontalDistance) {}

/**
 * Utility for printing the top view of a binary tree.
 *
 * The top view is the set of nodes visible when the tree is viewed from above.
 * For each horizontal distance from the root, only the first node encountered
 * in a level-order (BFS) traversal is printed.
 */
class TopViewBinaryTree {

    TreeNode root;

    TopViewBinaryTree() {}

    TopViewBinaryTree(TreeNode root) {
        this.root = root;
    }

    /** Prints the top view of the binary tree to standard output. */
    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> seenHorizontalDistances = new HashSet<>();
        Queue<NodeWithDistance> queue = new LinkedList<>();

        queue.add(new NodeWithDistance(root, 0));

        while (!queue.isEmpty()) {
            NodeWithDistance current = queue.remove();
            int horizontalDistance = current.horizontalDistance();
            TreeNode node = current.node();

            // Print the node if this horizontal distance is seen for the first time.
            if (seenHorizontalDistances.add(horizontalDistance)) {
                System.out.print(node.value + " ");
            }

            if (node.left != null) {
                queue.add(new NodeWithDistance(node.left, horizontalDistance - 1));
            }
            if (node.right != null) {
                queue.add(new NodeWithDistance(node.right, horizontalDistance + 1));
            }
        }
    }
}

/** Demo class for printing the top view of a sample binary tree. */
public final class TopViewBinaryTreeDemo {

    private TopViewBinaryTreeDemo() {}

    public static void main(String[] args) {
        /*
               1
             /   \
            2     3
             \
              4
               \
                5
                 \
                  6
        */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);

        TopViewBinaryTree tree = new TopViewBinaryTree(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        tree.printTopView();
    }
}