package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Post-order traversal of a binary tree: LEFT -> RIGHT -> ROOT.
 *
 * <p>Implementations:
 * <ul>
 *   <li>Recursive: O(n) time, O(n) space (call stack), where n is the number of nodes.</li>
 *   <li>Iterative: O(n) time, O(h) space, where h is the height of the tree.
 *       In the worst case (completely unbalanced tree), h can be O(n).</li>
 * </ul>
 */
public final class PostOrderTraversal {

    private PostOrderTraversal() {
        // Utility class; prevent instantiation.
    }

    /**
     * Performs a recursive post-order traversal.
     *
     * @param root the root node of the tree
     * @return list of node values in post-order
     */
    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePostOrder(root, result);
        return result;
    }

    /**
     * Performs an iterative post-order traversal.
     *
     * @param root the root node of the tree
     * @return list of node values in post-order
     */
    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            result.addFirst(current.data);

            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }

        return result;
    }

    private static void recursivePostOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        recursivePostOrder(node.left, result);
        recursivePostOrder(node.right, result);
        result.add(node.data);
    }
}