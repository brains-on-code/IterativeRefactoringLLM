package com.thealgorithms.others;

import java.util.ArrayList;

public class Class1 {

    Class3[] segments;
    int segmentCount;

    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Class3(start, middle, end);
    }

    public ArrayList<Class2> buildIntervals(int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            ArrayList<Class2> intervals = new ArrayList<>();
            intervals.add(new Class2(segments[leftIndex].start, segments[leftIndex].middle));
            intervals.add(new Class2(segments[rightIndex].end, 0));
            return intervals;
        }

        int midIndex = (leftIndex + rightIndex) / 2;

        ArrayList<Class2> leftIntervals = this.buildIntervals(leftIndex, midIndex);
        ArrayList<Class2> rightIntervals = this.buildIntervals(midIndex + 1, rightIndex);
        return this.mergeIntervals(leftIntervals, rightIntervals);
    }

    public ArrayList<Class2> mergeIntervals(ArrayList<Class2> leftIntervals, ArrayList<Class2> rightIntervals) {
        int leftValue = 0;
        int rightValue = 0;
        ArrayList<Class2> mergedIntervals = new ArrayList<>();
        int lastValue = 0;

        while (!leftIntervals.isEmpty() && !rightIntervals.isEmpty()) {
            if (leftIntervals.get(0).position < rightIntervals.get(0).position) {
                int currentPosition = leftIntervals.get(0).position;
                leftValue = leftIntervals.get(0).value;

                if (leftValue < rightValue) {
                    leftIntervals.remove(0);
                    if (lastValue != rightValue) {
                        mergedIntervals.add(new Class2(currentPosition, rightValue));
                    }
                } else {
                    lastValue = leftValue;
                    leftIntervals.remove(0);
                    mergedIntervals.add(new Class2(currentPosition, leftValue));
                }
            } else {
                int currentPosition = rightIntervals.get(0).position;
                rightValue = rightIntervals.get(0).value;

                if (rightValue < leftValue) {
                    rightIntervals.remove(0);
                    if (lastValue != leftValue) {
                        mergedIntervals.add(new Class2(currentPosition, leftValue));
                    }
                } else {
                    lastValue = rightValue;
                    rightIntervals.remove(0);
                    mergedIntervals.add(new Class2(currentPosition, rightValue));
                }
            }
        }

        while (!leftIntervals.isEmpty()) {
            mergedIntervals.add(leftIntervals.get(0));
            leftIntervals.remove(0);
        }

        while (!rightIntervals.isEmpty()) {
            mergedIntervals.add(rightIntervals.get(0));
            rightIntervals.remove(0);
        }

        return mergedIntervals;
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