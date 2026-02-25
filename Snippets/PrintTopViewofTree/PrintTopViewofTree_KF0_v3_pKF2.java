package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {

    int key;
    TreeNode left;
    TreeNode right;

    TreeNode(int key) {
        this.key = key;
    }
}

/**
 * Represents an item in the BFS queue, containing a tree node and its
 * horizontal distance from the root.
 */
class QueueItem {

    TreeNode node;
    int horizontalDistance;

    QueueItem(TreeNode node, int horizontalDistance) {
        this.node = node;
        this.horizontalDistance = horizontalDistance;
    }
}

class Tree {

    TreeNode root;

    Tree() {}

    Tree(TreeNode root) {
        this.root = root;
    }

    /**
     * Prints the nodes visible from the top of the binary tree.
     *
     * This method performs a breadth-first search (level-order traversal).
     * For each horizontal distance from the root, only the first node
     * encountered is printed.
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
            int horizontalDistance = current.horizontalDistance;
            TreeNode node = current.node;

            if (seenHorizontalDistances.add(horizontalDistance)) {
                System.out.print(node.key + " ");
            }

            if (node.left != null) {
                queue.add(new QueueItem(node.left, horizontalDistance - 1));
            }
            if (node.right != null) {
                queue.add(new QueueItem(node.right, horizontalDistance + 1));
            }
        }
    }
}

public final class PrintTopViewofTree {

    private PrintTopViewofTree() {}

    public static void main(String[] args) {
        /*
         * Example tree:
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
        System.out.println("Following are nodes in top view of Binary Tree");
        tree.printTopView();
    }
}