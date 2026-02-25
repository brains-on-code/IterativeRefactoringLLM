package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class ZigzagLevelOrderTraversal {

    private ZigzagLevelOrderTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> zigzagLevels = new ArrayList<>();
        Deque<BinaryTree.Node> nodesToVisit = new ArrayDeque<>();
        nodesToVisit.offer(root);
        boolean isLeftToRight = true;

        while (!nodesToVisit.isEmpty()) {
            int levelNodeCount = nodesToVisit.size();
            List<Integer> levelValues = new LinkedList<>();

            for (int i = 0; i < levelNodeCount; i++) {
                BinaryTree.Node currentNode = nodesToVisit.poll();

                if (isLeftToRight) {
                    levelValues.add(currentNode.data);
                } else {
                    levelValues.add(0, currentNode.data);
                }

                if (currentNode.left != null) {
                    nodesToVisit.offer(currentNode.left);
                }
                if (currentNode.right != null) {
                    nodesToVisit.offer(currentNode.right);
                }
            }

            isLeftToRight = !isLeftToRight;
            zigzagLevels.add(levelValues);
        }

        return zigzagLevels;
    }
}