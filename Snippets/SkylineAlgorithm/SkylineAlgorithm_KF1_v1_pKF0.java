package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

public class Class1 {

    private final ArrayList<Class2> elements;

    public Class1() {
        this.elements = new ArrayList<>();
    }

    public ArrayList<Class2> getElements() {
        return elements;
    }

    public ArrayList<Class2> processList(ArrayList<Class2> list) {
        int size = list.size();
        if (size == 1) {
            return list;
        } else if (size == 2) {
            if (list.get(0).dominates(list.get(1))) {
                list.remove(1);
            } else if (list.get(1).dominates(list.get(0))) {
                list.remove(0);
            }
            return list;
        }

        ArrayList<Class2> left = new ArrayList<>();
        ArrayList<Class2> right = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() / 2) {
                left.add(list.get(i));
            } else {
                right.add(list.get(i));
            }
        }

        ArrayList<Class2> processedLeft = processList(left);
        ArrayList<Class2> processedRight = processList(right);

        return merge(processedLeft, processedRight);
    }

    public ArrayList<Class2> merge(ArrayList<Class2> left, ArrayList<Class2> right) {
        for (int i = 0; i < left.size() - 1; i++) {
            Class2 current = left.get(i);
            Class2 next = left.get(i + 1);
            if (current.x == next.x && current.y > next.y) {
                left.remove(i);
                i--;
            }
        }

        int minY = left.get(0).y;
        for (int i = 1; i < left.size(); i++) {
            int currentY = left.get(i).y;
            if (minY > currentY) {
                minY = currentY;
                if (minY == 1) {
                    break;
                }
            }
        }

        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).y >= minY) {
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
            return (this.x < other.x && this.y <= other.y)
                || (this.x <= other.x && this.y < other.y);
        }
    }

    class Class3 implements Comparator<Class2> {

        @Override
        public int compare(Class2 first, Class2 second) {
            return Integer.compare(first.x, second.x);
        }
    }
}