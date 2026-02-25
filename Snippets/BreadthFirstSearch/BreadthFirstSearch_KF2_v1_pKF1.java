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

    public Optional<Node<T>> search(final Node<T> rootNode, final T targetValue) {
        if (rootNode == null) {
            return Optional.empty();
        }

        T rootValue = rootNode.getValue();
        visitedNodes.add(rootValue);
        visitedNodeSet.add(rootValue);

        if ((targetValue == null && rootValue == null) || (targetValue != null && targetValue.equals(rootValue))) {
            return Optional.of(rootNode);
        }

        Queue<Node<T>> nodeQueue = new ArrayDeque<>(rootNode.getChildren());
        while (!nodeQueue.isEmpty()) {
            final Node<T> currentNode = nodeQueue.poll();
            T currentValue = currentNode.getValue();

            if (visitedNodeSet.contains(currentValue)) {
                continue;
            }

            visitedNodes.add(currentValue);
            visitedNodeSet.add(currentValue);

            if ((targetValue == null && currentValue == null) || (targetValue != null && targetValue.equals(currentValue))) {
                return Optional.of(currentNode);
            }

            nodeQueue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    public List<T> getVisited() {
        return visitedNodes;
    }
}