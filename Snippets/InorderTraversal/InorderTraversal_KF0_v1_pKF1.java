package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given tree is traversed in an 'inorder' way: LEFT -> ROOT -> RIGHT.
 * Below are given the recursive and iterative implementations.
 *
 * Complexities:
 * Recursive: O(n) - time, O(n) - space, where 'n' is the number of nodes in a tree.
 *
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
public final class InorderTraversal {

    private InorderTraversal() {}

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        recursiveInorder(root, traversalResult);
        return traversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> traversalResult = new ArrayList<>();
        if (root == null) {
            return traversalResult;
        }

        Deque<BinaryTree.Node> nodeStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (!nodeStack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = nodeStack.pop();
            traversalResult.add(currentNode.data);
            currentNode = currentNode.right;
        }
        return traversalResult;
    }

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> traversalResult) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, traversalResult);
        traversalResult.add(node.data);
        recursiveInorder(node.right, traversalResult);
    }
}