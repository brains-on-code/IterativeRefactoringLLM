package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-First Search implementation for tree/graph traversal.
 * @author caos321
 * @co-author @manishraj27
 * @see <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search</a>
 */
public class BreadthFirstSearch<T> {
    private final List<T> visitedValuesInOrder = new ArrayList<>();
    private final Set<T> visitedValuesSet = new HashSet<>();

    /**
     * Performs a breadth-first search to find a node with the given value.
     *
     * @param root The root node to start the search from
     * @param targetValue The value to search for
     * @return Optional containing the found node, or empty if not found
     */
    public Optional<Node<T>> search(final Node<T> root, final T targetValue) {
        if (root == null) {
            return Optional.empty();
        }

        T rootValue = root.getValue();
        visitedValuesInOrder.add(rootValue);
        visitedValuesSet.add(rootValue);

        if (valuesAreEqual(rootValue, targetValue)) {
            return Optional.of(root);
        }

        Queue<Node<T>> nodesToVisit = new ArrayDeque<>(root.getChildren());
        while (!nodesToVisit.isEmpty()) {
            final Node<T> currentNode = nodesToVisit.poll();
            T currentValue = currentNode.getValue();

            if (visitedValuesSet.contains(currentValue)) {
                continue;
            }

            visitedValuesInOrder.add(currentValue);
            visitedValuesSet.add(currentValue);

            if (valuesAreEqual(currentValue, targetValue)) {
                return Optional.of(currentNode);
            }

            nodesToVisit.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private boolean valuesAreEqual(T firstValue, T secondValue) {
        return firstValue == secondValue || (secondValue != null && secondValue.equals(firstValue));
    }

    /**
     * Returns the list of node values in the order they were visited.
     *
     * @return List containing the visited node values
     */
    public List<T> getVisited() {
        return visitedValuesInOrder;
    }
}