package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Class1 {

    private final List<Class2> elements;

    public Class1() {
        this.elements = new ArrayList<>();
    }

    public List<Class2> getElements() {
        return elements;
    }

    public List<Class2> processList(List<Class2> points) {
        int size = points.size();
        if (size <= 1) {
            return points;
        }

        if (size == 2) {
            Class2 first = points.get(0);
            Class2 second = points.get(1);

            if (first.dominates(second)) {
                points.remove(1);
            } else if (second.dominates(first)) {
                points.remove(0);
            }
            return points;
        }

        int mid = size / 2;
        List<Class2> left = new ArrayList<>(points.subList(0, mid));
        List<Class2> right = new ArrayList<>(points.subList(mid, size));

        List<Class2> processedLeft = processList(left);
        List<Class2> processedRight = processList(right);

        return merge(processedLeft, processedRight);
    }

    public List<Class2> merge(List<Class2> left, List<Class2> right) {
        removeDominatedWithSameX(left);
        int minY = findMinY(left);
        filterRightByMinY(right, minY);

        left.addAll(right);
        return left;
    }

    private void removeDominatedWithSameX(List<Class2> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            Class2 current = points.get(i);
            Class2 next = points.get(i + 1);

            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                points.remove(i);
                i--;
            }
        }
    }

    private int findMinY(List<Class2> points) {
        int minY = points.get(0).getY();
        for (int i = 1; i < points.size(); i++) {
            int currentY = points.get(i).getY();
            if (currentY < minY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }
        return minY;
    }

    private void filterRightByMinY(List<Class2> points, int minY) {
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).getY() >= minY) {
                points.remove(i);
                i--;
            }
        }
    }

    public static class Class2 {

        private final int x;
        private final int y;

        public Class2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean dominates(Class2 other) {
            boolean strictlySmallerX = this.x < other.x && this.y <= other.y;
            boolean strictlySmallerY = this.x <= other.x && this.y < other.y;
            return strictlySmallerX || strictlySmallerY;
        }
    }

    class Class3 implements Comparator<Class2> {

        @Override
        public int compare(Class2 first, Class2 second) {
            return Integer.compare(first.getX(), second.getX());
        }
    }
}