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

    private final List<T> visited = new ArrayList<>();
    private final Set<T> visitedSet = new HashSet<>();

    public Optional<Node<T>> search(final Node<T> root, final T targetValue) {
        if (root == null) {
            return Optional.empty();
        }

        Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            final T currentValue = currentNode.getValue();

            if (visitedSet.contains(currentValue)) {
                continue;
            }

            markAsVisited(currentValue);

            if (areEqual(targetValue, currentValue)) {
                return Optional.of(currentNode);
            }

            queue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private void markAsVisited(T value) {
        visited.add(value);
        visitedSet.add(value);
    }

    private boolean areEqual(T first, T second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    public List<T> getVisited() {
        return visited;
    }
}