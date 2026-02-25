package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Class1 {

    private Class3[] segments;
    private int segmentCount;

    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Class3(start, middle, end);
    }

    public List<Class2> buildIntervals(int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            List<Class2> intervals = new ArrayList<>();
            Class3 segment = segments[leftIndex];

            intervals.add(new Class2(segment.start, segment.middle));
            intervals.add(new Class2(segment.end, 0));

            return intervals;
        }

        int midIndex = (leftIndex + rightIndex) / 2;

        List<Class2> leftIntervals = buildIntervals(leftIndex, midIndex);
        List<Class2> rightIntervals = buildIntervals(midIndex + 1, rightIndex);

        return mergeIntervals(leftIntervals, rightIntervals);
    }

    public List<Class2> mergeIntervals(List<Class2> leftIntervals, List<Class2> rightIntervals) {
        Queue<Class2> leftQueue = new LinkedList<>(leftIntervals);
        Queue<Class2> rightQueue = new LinkedList<>(rightIntervals);

        int leftValue = 0;
        int rightValue = 0;
        int lastOutputValue = 0;

        List<Class2> merged = new ArrayList<>();

        while (!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
            Class2 left = leftQueue.peek();
            Class2 right = rightQueue.peek();

            if (left.position < right.position) {
                int currentPosition = left.position;
                leftValue = left.value;

                if (leftValue < rightValue) {
                    leftQueue.poll();
                    if (lastOutputValue != rightValue) {
                        merged.add(new Class2(currentPosition, rightValue));
                    }
                } else {
                    lastOutputValue = leftValue;
                    leftQueue.poll();
                    merged.add(new Class2(currentPosition, leftValue));
                }
            } else {
                int currentPosition = right.position;
                rightValue = right.value;

                if (rightValue < leftValue) {
                    rightQueue.poll();
                    if (lastOutputValue != leftValue) {
                        merged.add(new Class2(currentPosition, leftValue));
                    }
                } else {
                    lastOutputValue = rightValue;
                    rightQueue.poll();
                    merged.add(new Class2(currentPosition, rightValue));
                }
            }
        }

        merged.addAll(leftQueue);
        merged.addAll(rightQueue);

        return merged;
    }

    public static class Class2 {
        public final int position;
        public final int value;

        public Class2(int position, int value) {
            this.position = position;
            this.value = value;
        }
    }

    public static class Class3 {
        public final int start;
        public final int middle;
        public final int end;

        public Class3(int start, int middle, int end) {
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }
}