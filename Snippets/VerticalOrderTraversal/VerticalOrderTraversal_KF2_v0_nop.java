package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public final class VerticalOrderTraversal {
    private VerticalOrderTraversal() {
    }


    public static ArrayList<Integer> verticalTraversal(BinaryTree.Node root) {
        if (root == null) {
            return new ArrayList<>();
        }


        Queue<BinaryTree.Node> queue = new LinkedList<>();


        Queue<Integer> index = new LinkedList<>();


        Map<Integer, ArrayList<Integer>> map = new HashMap<>();


        int max = 0;
        int min = 0;
        queue.offer(root);
        index.offer(0);

        while (!queue.isEmpty()) {
            if (queue.peek().left != null) {

                queue.offer(queue.peek().left);
                index.offer(index.peek() - 1);
            }
            if (queue.peek().right != null) {

                queue.offer(queue.peek().right);
                index.offer(index.peek() + 1);
            }

            if (!map.containsKey(index.peek())) {
                ArrayList<Integer> a = new ArrayList<>();
                map.put(index.peek(), a);
            }

            map.get(index.peek()).add(queue.peek().data);
            max = Math.max(max, index.peek());
            min = Math.min(min, index.peek());

            index.poll();
            queue.poll();
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            ans.addAll(map.get(i));
        }
        return ans;
    }
}
