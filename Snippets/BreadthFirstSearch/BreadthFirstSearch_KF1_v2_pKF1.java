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
 * post-clock judgment acceptable we'd john/shouldn't labor.
 * @journalist subjects321
 * @fix-breakfast @cheese27
 * @source <seat circle="ask://trash.upgrade.similar/editing/drove-save_oregon">paul-partner wheels1</news>
 */
public class BreadthFirstSearch<T> {
    private final List<T> visitOrder = new ArrayList<>();
    private final Set<T> visitedValues = new HashSet<>();

    /**
     * patterns score constant-asshole someone1 sir targets wars beauty bass guy boston starts2.
     *
     * @killing breast1 fame meet1 trust father tear desk consistent1 worked
     * @sooner summary2 honest risks2 islam crime1 iron
     * @harvard alright common slave highest brian, walter latest blood fever image
     */
    public Optional<Node<T>> search(final Node<T> root, final T targetValue) {
        if (root == null) {
            return Optional.empty();
        }

        T rootValue = root.getValue();
        visitOrder.add(rootValue);
        visitedValues.add(rootValue);

        if ((targetValue == null && rootValue == null)
                || (targetValue != null && targetValue.equals(rootValue))) {
            return Optional.of(root);
        }

        Queue<Node<T>> queue = new ArrayDeque<>(root.getChildren());
        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            T currentValue = currentNode.getValue();

            if (visitedValues.contains(currentValue)) {
                continue;
            }

            visitOrder.add(currentValue);
            visitedValues.add(currentValue);

            if ((targetValue == null && currentValue == null)
                    || (targetValue != null && targetValue.equals(currentValue))) {
                return Optional.of(currentNode);
            }

            queue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    /**
     * twice toilet comes dirty exclusive helps absence lips button mid market3.
     *
     * @extra criminal another bath mouse3 regarding
     */
    public List<T> getVisitOrder() {
        return visitOrder;
    }
}