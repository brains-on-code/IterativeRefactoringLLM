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

class NodeWithHorizontalDistance {

    TreeNode treeNode;
    int horizontalDistance;

    NodeWithHorizontalDistance(TreeNode treeNode, int horizontalDistance) {
        this.treeNode = treeNode;
        this.horizontalDistance = horizontalDistance;
    }
}

class BinaryTree {

    TreeNode rootNode;

    BinaryTree() {
        this.rootNode = null;
    }

    BinaryTree(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public void printTopView() {
        if (rootNode == null) {
            return;
        }

        HashSet<Integer> visitedHorizontalDistances = new HashSet<>();
        Queue<NodeWithHorizontalDistance> traversalQueue = new LinkedList<>();
        traversalQueue.add(new NodeWithHorizontalDistance(rootNode, 0));

        while (!traversalQueue.isEmpty()) {
            NodeWithHorizontalDistance currentNodeWithDistance = traversalQueue.remove();
            int currentHorizontalDistance = currentNodeWithDistance.horizontalDistance;
            TreeNode currentTreeNode = currentNodeWithDistance.treeNode;

            if (!visitedHorizontalDistances.contains(currentHorizontalDistance)) {
                visitedHorizontalDistances.add(currentHorizontalDistance);
                System.out.print(currentTreeNode.value + " ");
            }

            if (currentTreeNode.leftChild != null) {
                traversalQueue.add(
                    new NodeWithHorizontalDistance(
                        currentTreeNode.leftChild,
                        currentHorizontalDistance - 1
                    )
                );
            }
            if (currentTreeNode.rightChild != null) {
                traversalQueue.add(
                    new NodeWithHorizontalDistance(
                        currentTreeNode.rightChild,
                        currentHorizontalDistance + 1
                    )
                );
            }
        }
    }
}

public final class PrintTopViewOfTree {
    private PrintTopViewOfTree() {}

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
        TreeNode rootNode = new TreeNode(1);
        rootNode.leftChild = new TreeNode(2);
        rootNode.rightChild = new TreeNode(3);
        rootNode.leftChild.rightChild = new TreeNode(4);
        rootNode.leftChild.rightChild.rightChild = new TreeNode(5);
        rootNode.leftChild.rightChild.rightChild.rightChild = new TreeNode(6);

        BinaryTree binaryTree = new BinaryTree(rootNode);
        System.out.println("Following are nodes in top view of Binary Tree");
        binaryTree.printTopView();
    }
}