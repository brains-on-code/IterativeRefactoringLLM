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

        if (areEqual(targetValue, rootNodeValue)) {
            return Optional.of(rootNode);
        }

        Queue<Node<T>> nodeQueue = new ArrayDeque<>(rootNode.getChildren());
        while (!nodeQueue.isEmpty()) {
            final Node<T> currentNode = nodeQueue.poll();
            T currentNodeValue = currentNode.getValue();

            if (visitedValueSet.contains(currentNodeValue)) {
                continue;
            }

            visitedValues.add(currentNodeValue);
            visitedValueSet.add(currentNodeValue);

            if (areEqual(targetValue, currentNodeValue)) {
                return Optional.of(currentNode);
            }

            nodeQueue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private boolean areEqual(T firstValue, T secondValue) {
        return (firstValue == null && secondValue == null)
            || (firstValue != null && firstValue.equals(secondValue));
    }

    public List<T> getVisitedValues() {
        return visitedValues;
    }
}