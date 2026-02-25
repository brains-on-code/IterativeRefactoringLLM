package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Given tree is traversed in a 'post-order' way: LEFT -> RIGHT -> ROOT.
 * Below are given the recursive and iterative implementations.
 * <p>
 * Complexities:
 * Recursive: O(n) - time, O(n) - space, where 'n' is the number of nodes in a tree.
 * <p>
 * Iterative: O(n) - time, O(h) - space, where 'n' is the number of nodes in a tree
 * and 'h' is the height of a binary tree.
 * In the worst case 'h' can be O(n) if tree is completely unbalanced, for instance:
 * 5
 *  \
 *   6
 *    \
 *     7
 *      \
 *       8
 *
 * @author Albina Gimaletdinova on 21/02/2023
 */
public final class PostOrderTraversal {

    private PostOrderTraversal() {
    }

    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        recursivePostOrder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
        LinkedList<Integer> traversalResult = new LinkedList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);
        while (!nodeStack.isEmpty()) {
            BinaryTree.Node currentNode = nodeStack.pop();
            traversalResult.addFirst(currentNode.data);
            if (currentNode.left != null) {
                nodeStack.push(currentNode.left);
            }
            if (currentNode.right != null) {
                nodeStack.push(currentNode.right);
            }
        }

        return traversalResult;
    }

    private static void recursivePostOrder(BinaryTree.Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        recursivePostOrder(currentNode.left, traversalResult);
        recursivePostOrder(currentNode.right, traversalResult);
        traversalResult.add(currentNode.data);
    }
}