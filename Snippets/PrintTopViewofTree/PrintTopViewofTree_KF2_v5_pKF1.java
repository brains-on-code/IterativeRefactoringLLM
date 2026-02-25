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

class HorizontalDistanceNode {

    TreeNode treeNode;
    int horizontalDistance;

    HorizontalDistanceNode(TreeNode treeNode, int horizontalDistance) {
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
        Queue<HorizontalDistanceNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(new HorizontalDistanceNode(rootNode, 0));

        while (!nodeQueue.isEmpty()) {
            HorizontalDistanceNode currentQueueEntry = nodeQueue.remove();
            int currentHorizontalDistance = currentQueueEntry.horizontalDistance;
            TreeNode currentTreeNode = currentQueueEntry.treeNode;

            if (!visitedHorizontalDistances.contains(currentHorizontalDistance)) {
                visitedHorizontalDistances.add(currentHorizontalDistance);
                System.out.print(currentTreeNode.value + " ");
            }

            if (currentTreeNode.leftChild != null) {
                nodeQueue.add(
                    new HorizontalDistanceNode(
                        currentTreeNode.leftChild,
                        currentHorizontalDistance - 1
                    )
                );
            }
            if (currentTreeNode.rightChild != null) {
                nodeQueue.add(
                    new HorizontalDistanceNode(
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