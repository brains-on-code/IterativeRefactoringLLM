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
 * Breadth-first search implementation over a tree of {@link Node} elements.
 */
public class BreadthFirstSearch<T> {
    private final List<T> traversalOrder = new ArrayList<>();
    private final Set<T> visitedValues = new HashSet<>();

    /**
     * Performs a breadth-first search starting from the given root node, looking
     * for the specified target value.
     *
     * @param root        the root node to start the search from
     * @param targetValue the value to search for (may be {@code null})
     * @return an {@link Optional} containing the node with the target value if found,
     *         or {@link Optional#empty()} if not found or if root is {@code null}
     */
    public Optional<Node<T>> search(final Node<T> root, final T targetValue) {
        if (root == null) {
            return Optional.empty();
        }

        T rootValue = root.getValue();
        traversalOrder.add(rootValue);
        visitedValues.add(rootValue);

        if (valuesAreEqual(targetValue, rootValue)) {
            return Optional.of(root);
        }

        Queue<Node<T>> nodeQueue = new ArrayDeque<>(root.getChildren());
        while (!nodeQueue.isEmpty()) {
            final Node<T> currentNode = nodeQueue.poll();
            T currentValue = currentNode.getValue();

            if (visitedValues.contains(currentValue)) {
                continue;
            }

            traversalOrder.add(currentValue);
            visitedValues.add(currentValue);

            if (valuesAreEqual(targetValue, currentValue)) {
                return Optional.of(currentNode);
            }

            nodeQueue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private boolean valuesAreEqual(T firstValue, T secondValue) {
        return (firstValue == null && secondValue == null)
                || (firstValue != null && firstValue.equals(secondValue));
    }

    /**
     * Returns the order in which node values were visited during the last search.
     *
     * @return a list of visited values in traversal order
     */
    public List<T> getTraversalOrder() {
        return traversalOrder;
    }
}