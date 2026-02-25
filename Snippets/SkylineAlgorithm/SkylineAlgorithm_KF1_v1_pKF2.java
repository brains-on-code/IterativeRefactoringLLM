package com.thealgorithms.divideandconquer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * A divide-and-conquer utility operating on Class2 objects.
 */
public class Class1 {

    private ArrayList<Class2> items;

    /**
     * Creates an empty Class1 instance.
     */
    public Class1() {
        items = new ArrayList<>();
    }

    /**
     * Returns the internal list of Class2 items.
     */
    public ArrayList<Class2> getItems() {
        return items;
    }

    /**
     * Recursively processes a list of Class2 items using a divide-and-conquer
     * strategy and returns the processed list.
     */
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

    /**
     * Merges two processed lists of Class2 items into a single list.
     */
    public ArrayList<Class2> merge(ArrayList<Class2> left, ArrayList<Class2> right) {
        // Remove dominated neighbors in the left list
        for (int i = 0; i < left.size() - 1; i++) {
            if (left.get(i).x == left.get(i + 1).x && left.get(i).y > left.get(i + 1).y) {
                left.remove(i);
                i--;
            }
        }

        // Find minimum y in the left list
        int minY = left.get(0).y;
        for (int i = 1; i < left.size(); i++) {
            if (minY > left.get(i).y) {
                minY = left.get(i).y;
                if (minY == 1) {
                    i = left.size();
                }
            }
        }

        // Remove elements from right list that are not better than minY
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

        private int x;
        private int y;

        /**
         * Creates a Class2 instance with the given coordinates.
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
         * Returns true if this instance dominates the given instance according
         * to the defined dominance relation.
         */
        public boolean dominates(Class2 other) {
            return ((this.x < other.x && this.y <= other.y) || (this.x <= other.x && this.y < other.y));
        }
    }

    /**
     * Comparator for Class2 based on the x value.
     */
    class Class3 implements Comparator<Class2> {

        @Override
        public int compare(Class2 a, Class2 b) {
            return Integer.compare(a.x, b.x);
        }
    }
}