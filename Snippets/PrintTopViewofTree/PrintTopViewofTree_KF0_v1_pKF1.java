package com.thealgorithms.datastructures.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class TreeNode {

    int value;
    TreeNode leftChild;
    TreeNode rightChild;

    TreeNode(int value) {
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }
}

class QueueItem {

    TreeNode node;
    int horizontalDistance;

    QueueItem(TreeNode node, int horizontalDistance) {
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

        HashSet<Integer> visitedHorizontalDistances = new HashSet<>();

        Queue<QueueItem> queue = new LinkedList<>();
        queue.add(new QueueItem(root, 0));

        while (!queue.isEmpty()) {
            QueueItem currentItem = queue.remove();
            int currentHorizontalDistance = currentItem.horizontalDistance;
            TreeNode currentNode = currentItem.node;

            if (!visitedHorizontalDistances.contains(currentHorizontalDistance)) {
                visitedHorizontalDistances.add(currentHorizontalDistance);
                System.out.print(currentNode.value + " ");
            }

            if (currentNode.leftChild != null) {
                queue.add(new QueueItem(currentNode.leftChild, currentHorizontalDistance - 1));
            }
            if (currentNode.rightChild != null) {
                queue.add(new QueueItem(currentNode.rightChild, currentHorizontalDistance + 1));
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
        root.leftChild = new TreeNode(2);
        root.rightChild = new TreeNode(3);
        root.leftChild.rightChild = new TreeNode(4);
        root.leftChild.rightChild.rightChild = new TreeNode(5);
        root.leftChild.rightChild.rightChild.rightChild = new TreeNode(6);

        BinaryTree binaryTree = new BinaryTree(root);
        System.out.println("Following are nodes in top view of Binary Tree");
        binaryTree.printTopView();
    }
}