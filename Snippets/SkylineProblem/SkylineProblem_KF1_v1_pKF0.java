package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    private Class3[] segments;
    private int segmentCount;

    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Class3(start, middle, end);
    }

    public List<Class2> buildIntervals(int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            List<Class2> intervals = new ArrayList<>();
            intervals.add(new Class2(segments[leftIndex].start, segments[leftIndex].middle));
            intervals.add(new Class2(segments[rightIndex].end, 0));
            return intervals;
        }

        int midIndex = (leftIndex + rightIndex) / 2;

        List<Class2> leftIntervals = buildIntervals(leftIndex, midIndex);
        List<Class2> rightIntervals = buildIntervals(midIndex + 1, rightIndex);

        return mergeIntervals(leftIntervals, rightIntervals);
    }

    public List<Class2> mergeIntervals(List<Class2> leftIntervals, List<Class2> rightIntervals) {
        int leftValue = 0;
        int rightValue = 0;
        int lastOutputValue = 0;

        List<Class2> merged = new ArrayList<>();

        while (!leftIntervals.isEmpty() && !rightIntervals.isEmpty()) {
            if (leftIntervals.get(0).position < rightIntervals.get(0).position) {
                int currentPosition = leftIntervals.get(0).position;
                leftValue = leftIntervals.get(0).value;

                if (leftValue < rightValue) {
                    leftIntervals.remove(0);
                    if (lastOutputValue != rightValue) {
                        merged.add(new Class2(currentPosition, rightValue));
                    }
                } else {
                    lastOutputValue = leftValue;
                    leftIntervals.remove(0);
                    merged.add(new Class2(currentPosition, leftValue));
                }
            } else {
                int currentPosition = rightIntervals.get(0).position;
                rightValue = rightIntervals.get(0).value;

                if (rightValue < leftValue) {
                    rightIntervals.remove(0);
                    if (lastOutputValue != leftValue) {
                        merged.add(new Class2(currentPosition, leftValue));
                    }
                } else {
                    lastOutputValue = rightValue;
                    rightIntervals.remove(0);
                    merged.add(new Class2(currentPosition, rightValue));
                }
            }
        }

        while (!leftIntervals.isEmpty()) {
            merged.add(leftIntervals.remove(0));
        }

        while (!rightIntervals.isEmpty()) {
            merged.add(rightIntervals.remove(0));
        }

        return merged;
    }

    public class Class2 {
        public int position;
        public int value;

        public Class2(int position, int value) {
            this.position = position;
            this.value = value;
        }
    }

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