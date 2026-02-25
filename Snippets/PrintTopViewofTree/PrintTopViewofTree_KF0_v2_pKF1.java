package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {

    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

class HorizontalDistanceNode {

    TreeNode node;
    int horizontalDistance;

    HorizontalDistanceNode(TreeNode node, int horizontalDistance) {
        this.node = node;
        this.horizontalDistance = horizontalDistance;
    }
}

class BinaryTree {

    TreeNode root;

    BinaryTree() {
        this.root = null;
    }

    BinaryTree(TreeNode root) {
        this.root = root;
    }

    public void printTopView() {
        if (root == null) {
            return;
        }

        HashSet<Integer> seenHorizontalDistances = new HashSet<>();
        Queue<HorizontalDistanceNode> queue = new LinkedList<>();
        queue.add(new HorizontalDistanceNode(root, 0));

        while (!queue.isEmpty()) {
            HorizontalDistanceNode current = queue.remove();
            int currentHorizontalDistance = current.horizontalDistance;
            TreeNode currentNode = current.node;

            if (!seenHorizontalDistances.contains(currentHorizontalDistance)) {
                seenHorizontalDistances.add(currentHorizontalDistance);
                System.out.print(currentNode.value + " ");
            }

            if (currentNode.left != null) {
                queue.add(new HorizontalDistanceNode(currentNode.left, currentHorizontalDistance - 1));
            }
            if (currentNode.right != null) {
                queue.add(new HorizontalDistanceNode(currentNode.right, currentHorizontalDistance + 1));
            }
        }
    }
}

public final class PrintTopViewOfTree {
    private PrintTopViewOfTree() {
    }

    public static void main(String[] args) {
        /*
         * Create following Binary Tree
         *        1
         *      /   \
         *     2     3
         *      \
         *       4
         *        \
         *         5
         *          \
         *           6
         */
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.left.right.right = new TreeNode(5);
        root.left.right.right.right = new TreeNode(6);

        BinaryTree binaryTree = new BinaryTree(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        binaryTree.printTopView();
    }
}