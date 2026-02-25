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
    private final List<T> visitedInOrder = new ArrayList<>();
    private final Set<T> visitedValues = new HashSet<>();

    /**
     * Performs a breadth-first search starting from the given root node, looking
     * for the specified target value.
     *
     * @param rootNode    the root node to start the search from
     * @param targetValue the value to search for (may be {@code null})
     * @return an {@link Optional} containing the node with the target value if found,
     *         or {@link Optional#empty()} if not found or if rootNode is {@code null}
     */
    public Optional<Node<T>> search(final Node<T> rootNode, final T targetValue) {
        if (rootNode == null) {
            return Optional.empty();
        }

        T rootValue = rootNode.getValue();
        visitedInOrder.add(rootValue);
        visitedValues.add(rootValue);

        if (areValuesEqual(targetValue, rootValue)) {
            return Optional.of(rootNode);
        }

        Queue<Node<T>> nodesToVisit = new ArrayDeque<>(rootNode.getChildren());
        while (!nodesToVisit.isEmpty()) {
            final Node<T> currentNode = nodesToVisit.poll();
            T currentValue = currentNode.getValue();

            if (visitedValues.contains(currentValue)) {
                continue;
            }

            visitedInOrder.add(currentValue);
            visitedValues.add(currentValue);

            if (areValuesEqual(targetValue, currentValue)) {
                return Optional.of(currentNode);
            }

            nodesToVisit.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private boolean areValuesEqual(T firstValue, T secondValue) {
        return (firstValue == null && secondValue == null)
                || (firstValue != null && firstValue.equals(secondValue));
    }

    /**
     * Returns the order in which node values were visited during the last search.
     *
     * @return a list of visited values in traversal order
     */
    public List<T> getTraversalOrder() {
        return visitedInOrder;
    }
}