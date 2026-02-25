package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A divide-and-conquer utility operating on {@link Class2} objects.
 */
public class Class1 {

    private final List<Class2> items;

    /**
     * Creates an empty {@code Class1} instance.
     */
    public Class1() {
        this.items = new ArrayList<>();
    }

    /**
     * Returns the internal list of {@link Class2} items.
     */
    public List<Class2> getItems() {
        return items;
    }

    /**
     * Recursively processes a list of {@link Class2} items using a
     * divide-and-conquer strategy and returns the processed list.
     */
    public List<Class2> processList(List<Class2> list) {
        int size = list.size();

        if (size <= 1) {
            return list;
        }

        if (size == 2) {
            Class2 first = list.get(0);
            Class2 second = list.get(1);

            if (first.dominates(second)) {
                list.remove(1);
            } else if (second.dominates(first)) {
                list.remove(0);
            }
            return list;
        }

        int mid = size / 2;
        List<Class2> left = new ArrayList<>(list.subList(0, mid));
        List<Class2> right = new ArrayList<>(list.subList(mid, size));

        List<Class2> processedLeft = processList(left);
        List<Class2> processedRight = processList(right);

        return merge(processedLeft, processedRight);
    }

    /**
     * Merges two processed lists of {@link Class2} items into a single list.
     */
    public List<Class2> merge(List<Class2> left, List<Class2> right) {
        removeDominatedNeighbors(left);
        int minY = findMinY(left);
        filterRightByMinY(right, minY);

        left.addAll(right);
        return left;
    }

    /**
     * Removes dominated neighboring elements in the given list.
     * Two neighbors are compared by x; if x is equal, the one with larger y is removed.
     */
    private void removeDominatedNeighbors(List<Class2> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            Class2 current = list.get(i);
            Class2 next = list.get(i + 1);

            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                list.remove(i);
                i--;
            }
        }
    }

    /**
     * Returns the minimum y value in the given list.
     */
    private int findMinY(List<Class2> list) {
        int minY = list.get(0).getY();

        for (int i = 1; i < list.size(); i++) {
            int currentY = list.get(i).getY();
            if (currentY < minY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        return minY;
    }

    /**
     * Removes elements from the right list whose y value is not better than {@code minY}.
     */
    private void filterRightByMinY(List<Class2> right, int minY) {
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).getY() >= minY) {
                right.remove(i);
                i--;
            }
        }
    }

    public static class Class2 {

        private final int x;
        private final int y;

        /**
         * Creates a {@code Class2} instance with the given coordinates.
         */
        public Class2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Returns the x value.
         */
        public int getX() {
            return x;
        }

        /**
         * Returns the y value.
         */
        public int getY() {
            return y;
        }

        /**
         * Returns {@code true} if this instance dominates the given instance
         * according to the defined dominance relation.
         */
        public boolean dominates(Class2 other) {
            boolean strictlySmallerX = this.x < other.x && this.y <= other.y;
            boolean strictlySmallerY = this.x <= other.x && this.y < other.y;
            return strictlySmallerX || strictlySmallerY;
        }
    }

    /**
     * Comparator for {@link Class2} based on the x value.
     */
    class Class3 implements Comparator<Class2> {

        @Override
        public int compare(Class2 a, Class2 b) {
            return Integer.compare(a.getX(), b.getX());
        }
    }
}