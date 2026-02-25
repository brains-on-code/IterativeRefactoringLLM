package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch<T> {
    private final List<T> visitedValues = new ArrayList<>();
    private final Set<T> visitedValueSet = new HashSet<>();

    public Optional<Node<T>> search(final Node<T> rootNode, final T targetValue) {
        if (rootNode == null) {
            return Optional.empty();
        }

        T rootNodeValue = rootNode.getValue();
        visitedValues.add(rootNodeValue);
        visitedValueSet.add(rootNodeValue);

        if (valuesAreEqual(targetValue, rootNodeValue)) {
            return Optional.of(rootNode);
        }

        Queue<Node<T>> nodesToVisit = new ArrayDeque<>(rootNode.getChildren());
        while (!nodesToVisit.isEmpty()) {
            final Node<T> currentNode = nodesToVisit.poll();
            T currentNodeValue = currentNode.getValue();

            if (visitedValueSet.contains(currentNodeValue)) {
                continue;
            }

            visitedValues.add(currentNodeValue);
            visitedValueSet.add(currentNodeValue);

            if (valuesAreEqual(targetValue, currentNodeValue)) {
                return Optional.of(currentNode);
            }

            nodesToVisit.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private boolean valuesAreEqual(T firstValue, T secondValue) {
        return (firstValue == null && secondValue == null)
            || (firstValue != null && firstValue.equals(secondValue));
    }

    public List<T> getVisited() {
        return visitedValues;
    }
}