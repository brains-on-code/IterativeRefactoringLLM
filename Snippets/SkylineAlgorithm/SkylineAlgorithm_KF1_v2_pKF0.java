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

    public List<Class2> merge(List<Class2> left, List<Class2> right) {
        // Remove dominated points with same x in left
        for (int i = 0; i < left.size() - 1; i++) {
            Class2 current = left.get(i);
            Class2 next = left.get(i + 1);

            if (current.getX() == next.getX() && current.getY() > next.getY()) {
                left.remove(i);
                i--;
            }
        }

        // Find minimum y in left
        int minY = left.get(0).getY();
        for (int i = 1; i < left.size(); i++) {
            int currentY = left.get(i).getY();
            if (currentY < minY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        // Remove points in right that are not better than minY
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).getY() >= minY) {
                right.remove(i);
                i--;
            }
        }

        left.addAll(right);
        return left;
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