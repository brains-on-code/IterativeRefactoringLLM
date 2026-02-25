package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;

import java.util.*;

/**
 * Breadth-first search implementation.
 */
public class BreadthFirstSearch<T> {

    private final List<T> visited = new ArrayList<>();
    private final Set<T> visitedSet = new HashSet<>();

    public Optional<Node<T>> search(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        final Queue<Node<T>> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            final T currentValue = currentNode.getValue();

            if (!markVisited(currentValue)) {
                continue;
            }

            if (Objects.equals(currentValue, target)) {
                return Optional.of(currentNode);
            }

            enqueueUnvisitedChildren(queue, currentNode);
        }

        return Optional.empty();
    }

    private boolean markVisited(final T value) {
        if (!visitedSet.add(value)) {
            return false;
        }
        visited.add(value);
        return true;
    }

    private void enqueueUnvisitedChildren(final Queue<Node<T>> queue, final Node<T> currentNode) {
        for (Node<T> child : currentNode.getChildren()) {
            final T childValue = child.getValue();
            if (!visitedSet.contains(childValue)) {
                queue.offer(child);
            }
        }
    }

    public List<T> getVisited() {
        return Collections.unmodifiableList(visited);
    }
}