package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that stores segments and builds a merged representation of them.
 */
public class Class1 {

    Class3[] segments;
    int segmentCount;

    /**
     * Adds a new segment defined by three integer values.
     */
    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Class3(start, middle, end);
    }

    /**
     * Recursively builds a list of merged intervals from the stored segments
     * between indices {@code left} and {@code right} (inclusive).
     */
    public List<Class2> buildMergedIntervals(int left, int right) {
        if (left == right) {
            List<Class2> result = new ArrayList<>();
            result.add(new Class2(segments[left].start, segments[left].middle));
            result.add(new Class2(segments[right].end, 0));
            return result;
        }

        int mid = (left + right) / 2;

        List<Class2> leftIntervals = this.buildMergedIntervals(left, mid);
        List<Class2> rightIntervals = this.buildMergedIntervals(mid + 1, right);
        return this.mergeIntervals(leftIntervals, rightIntervals);
    }

    /**
     * Merges two sorted lists of intervals into a single list, resolving overlaps.
     */
    public List<Class2> mergeIntervals(List<Class2> leftIntervals, List<Class2> rightIntervals) {
        int leftHeight = 0;
        int rightHeight = 0;
        List<Class2> merged = new ArrayList<>();
        int currentHeight = 0;

        while (!leftIntervals.isEmpty() && !rightIntervals.isEmpty()) {
            if (leftIntervals.get(0).position < rightIntervals.get(0).position) {
                int position = leftIntervals.get(0).position;
                leftHeight = leftIntervals.get(0).height;

                if (leftHeight < rightHeight) {
                    leftIntervals.remove(0);
                    if (currentHeight != rightHeight) {
                        merged.add(new Class2(position, rightHeight));
                    }
                } else {
                    currentHeight = leftHeight;
                    leftIntervals.remove(0);
                    merged.add(new Class2(position, leftHeight));
                }
            } else {
                int position = rightIntervals.get(0).position;
                rightHeight = rightIntervals.get(0).height;

                if (rightHeight < leftHeight) {
                    rightIntervals.remove(0);
                    if (currentHeight != leftHeight) {
                        merged.add(new Class2(position, leftHeight));
                    }
                } else {
                    currentHeight = rightHeight;
                    rightIntervals.remove(0);
                    merged.add(new Class2(position, rightHeight));
                }
            }
        }

        while (!leftIntervals.isEmpty()) {
            merged.add(leftIntervals.get(0));
            leftIntervals.remove(0);
        }

        while (!rightIntervals.isEmpty()) {
            merged.add(rightIntervals.get(0));
            rightIntervals.remove(0);
        }

        return merged;
    }

    /**
     * Represents a point with a position and a height (e.g., for skyline problems).
     */
    public class Class2 {
        public int position;
        public int height;

        public Class2(int position, int height) {
            this.position = position;
            this.height = height;
        }
    }

    /**
     * Represents a segment with three integer attributes.
     */
    public class Class3 {
        public int start;
        public int middle;
        public int end;

        public Class3(int start, int middle, int end) {
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }
}