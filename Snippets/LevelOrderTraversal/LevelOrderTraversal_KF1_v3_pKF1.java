package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
    }

    public static List<List<Integer>> getLevelOrderValues(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levels = new ArrayList<>();
        Queue<BinaryTree.Node> nodesToVisit = new LinkedList<>();
        nodesToVisit.add(root);

        while (!nodesToVisit.isEmpty()) {
            int levelSize = nodesToVisit.size();
            List<Integer> currentLevel = new LinkedList<>();

            for (int i = 0; i < levelSize; i++) {
                BinaryTree.Node node = nodesToVisit.poll();
                currentLevel.add(node.data);

                if (node.left != null) {
                    nodesToVisit.add(node.left);
                }

                if (node.right != null) {
                    nodesToVisit.add(node.right);
                }
            }

            levels.add(currentLevel);
        }

        return levels;
    }

    public static void printNodesAtLevel(BinaryTree.Node root, int level) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printNodesAtLevel(root.left, level - 1);
            printNodesAtLevel(root.right, level - 1);
        }
    }
}