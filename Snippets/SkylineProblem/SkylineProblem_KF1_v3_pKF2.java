package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores segments and builds a merged representation of them.
 */
public class Class1 {

    private Class3[] segments;
    private int segmentCount;

    /**
     * Adds a new segment defined by three integer values.
     */
    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Class3(start, middle, end);
    }

    /**
     * Builds a list of merged intervals from the stored segments
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

        List<Class2> leftIntervals = buildMergedIntervals(left, mid);
        List<Class2> rightIntervals = buildMergedIntervals(mid + 1, right);
        return mergeIntervals(leftIntervals, rightIntervals);
    }

    /**
     * Merges two sorted lists of intervals into a single list, resolving overlaps.
     */
    public List<Class2> mergeIntervals(List<Class2> leftIntervals, List<Class2> rightIntervals) {
        int leftHeight = 0;
        int rightHeight = 0;
        int currentHeight = 0;

        List<Class2> merged = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftIntervals.size() && rightIndex < rightIntervals.size()) {
            Class2 leftPoint = leftIntervals.get(leftIndex);
            Class2 rightPoint = rightIntervals.get(rightIndex);

            if (leftPoint.position < rightPoint.position) {
                int position = leftPoint.position;
                leftHeight = leftPoint.height;

                if (leftHeight < rightHeight) {
                    leftIndex++;
                    if (currentHeight != rightHeight) {
                        merged.add(new Class2(position, rightHeight));
                        currentHeight = rightHeight;
                    }
                } else {
                    currentHeight = leftHeight;
                    leftIndex++;
                    merged.add(new Class2(position, leftHeight));
                }
            } else {
                int position = rightPoint.position;
                rightHeight = rightPoint.height;

                if (rightHeight < leftHeight) {
                    rightIndex++;
                    if (currentHeight != leftHeight) {
                        merged.add(new Class2(position, leftHeight));
                        currentHeight = leftHeight;
                    }
                } else {
                    currentHeight = rightHeight;
                    rightIndex++;
                    merged.add(new Class2(position, rightHeight));
                }
            }
        }

        while (leftIndex < leftIntervals.size()) {
            merged.add(leftIntervals.get(leftIndex++));
        }

        while (rightIndex < rightIntervals.size()) {
            merged.add(rightIntervals.get(rightIndex++));
        }

        return merged;
    }

    /**
     * Represents a point with a position and a height.
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