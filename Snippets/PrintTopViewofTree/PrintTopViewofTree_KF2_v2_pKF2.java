package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/** Represents a node in a binary tree. */
class TreeNode {

    int key;
    TreeNode left;
    TreeNode right;

    TreeNode(int key) {
        this.key = key;
    }
}

/**
 * Stores a tree node along with its horizontal distance from the root.
 * Used during level-order traversal when computing the top view.
 */
class QueueItem {

    TreeNode node;
    int horizontalDistance;

    QueueItem(TreeNode node, int horizontalDistance) {
        this.node = node;
        this.horizontalDistance = horizontalDistance;
    }
}

/** Binary tree with a method to print its top view. */
class Tree {

    TreeNode root;

    Tree() {}

    Tree(TreeNode root) {
        this.root = root;
    }

    /**
     * Prints the top view of the binary tree.
     *
     * The top view contains the first node encountered at each horizontal
     * distance when the tree is viewed from above. This method performs a
     * breadth-first search (level-order traversal) while tracking horizontal
     * distances and prints the first node seen for each distance.
     */
    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> seenHorizontalDistances = new HashSet<>();
        Queue<QueueItem> queue = new LinkedList<>();
        queue.add(new QueueItem(root, 0));

        while (!queue.isEmpty()) {
            QueueItem current = queue.remove();
            int hd = current.horizontalDistance;
            TreeNode currentNode = current.node;

            if (!seenHorizontalDistances.contains(hd)) {
                seenHorizontalDistances.add(hd);
                System.out.print(currentNode.key + " ");
            }

            if (currentNode.left != null) {
                queue.add(new QueueItem(currentNode.left, hd - 1));
            }

            if (currentNode.right != null) {
                queue.add(new QueueItem(currentNode.right, hd + 1));
            }
        }
    }
}

/** Demonstrates printing the top view of a binary tree. */
public final class PrintTopViewofTree {

    private PrintTopViewofTree() {}

    public static void main(String[] args) {
        /*
         * Construct the following binary tree:
         *
         *          1
         *        /   \
         *       2     3
         *        \
         *         4
         *          \
         *           5
         *            \
         *             6
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);

        Tree tree = new Tree(root);
        System.out.println("Following are nodes in top view of Binary Tree:");
        tree.printTopView();
    }
}