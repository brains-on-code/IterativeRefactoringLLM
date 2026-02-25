package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


public final class ZigzagTraversal {
    private ZigzagTraversal() {
    }
    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> result = new ArrayList<>();

        Deque<BinaryTree.Node> q = new ArrayDeque<>();
        q.offer(root);
        boolean prevLevelFromLeftToRight = false;

        while (!q.isEmpty()) {
            int nodesOnLevel = q.size();
            List<Integer> level = new LinkedList<>();
            for (int i = 0; i < nodesOnLevel; i++) {
                BinaryTree.Node node = q.poll();
                if (prevLevelFromLeftToRight) {
                    level.add(0, node.data);
                } else {
                    level.add(node.data);
                }
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            prevLevelFromLeftToRight = !prevLevelFromLeftToRight;
            result.add(level);
        }
        return result;
    }
}
