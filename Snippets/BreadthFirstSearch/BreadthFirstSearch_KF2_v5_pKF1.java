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
    private final List<T> visitedNodes = new ArrayList<>();
    private final Set<T> visitedNodeSet = new HashSet<>();

    public Optional<Node<T>> search(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        T rootValue = root.getValue();
        visitedNodes.add(rootValue);
        visitedNodeSet.add(rootValue);

        if (areEqual(target, rootValue)) {
            return Optional.of(root);
        }

        Queue<Node<T>> queue = new ArrayDeque<>(root.getChildren());
        while (!queue.isEmpty()) {
            final Node<T> current = queue.poll();
            T currentValue = current.getValue();

            if (visitedNodeSet.contains(currentValue)) {
                continue;
            }

            visitedNodes.add(currentValue);
            visitedNodeSet.add(currentValue);

            if (areEqual(target, currentValue)) {
                return Optional.of(current);
            }

            queue.addAll(current.getChildren());
        }

        return Optional.empty();
    }

    private boolean areEqual(T first, T second) {
        return (first == null && second == null)
            || (first != null && first.equals(second));
    }

    public List<T> getVisitedValues() {
        return visitedNodes;
    }
}