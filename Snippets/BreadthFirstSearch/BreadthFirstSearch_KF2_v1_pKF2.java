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
            final Node<T> current = queue.poll();
            final T currentValue = current.getValue();

            if (visitedSet.contains(currentValue)) {
                continue;
            }

            visited.add(currentValue);
            visitedSet.add(currentValue);

            if ((targetValue == null && currentValue == null)
                    || (targetValue != null && targetValue.equals(currentValue))) {
                return Optional.of(current);
            }

            queue.addAll(current.getChildren());
        }

        return Optional.empty();
    }

    public List<T> getVisited() {
        return visited;
    }
}