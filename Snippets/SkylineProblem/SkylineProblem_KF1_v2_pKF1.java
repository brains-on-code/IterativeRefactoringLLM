package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SegmentIntervalBuilder {

    private Segment[] segments;
    private int segmentCount;

    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Segment(start, middle, end);
    }

    public List<IntervalPoint> buildIntervals(int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            List<IntervalPoint> intervalPoints = new ArrayList<>();
            intervalPoints.add(new IntervalPoint(segments[leftIndex].start, segments[leftIndex].middle));
            intervalPoints.add(new IntervalPoint(segments[rightIndex].end, 0));
            return intervalPoints;
        }

        int midIndex = (leftIndex + rightIndex) / 2;

        List<IntervalPoint> leftIntervalPoints = buildIntervals(leftIndex, midIndex);
        List<IntervalPoint> rightIntervalPoints = buildIntervals(midIndex + 1, rightIndex);
        return mergeIntervals(leftIntervalPoints, rightIntervalPoints);
    }

    public List<IntervalPoint> mergeIntervals(List<IntervalPoint> leftIntervalPoints,
                                              List<IntervalPoint> rightIntervalPoints) {
        int leftValue = 0;
        int rightValue = 0;
        List<IntervalPoint> mergedIntervalPoints = new ArrayList<>();
        int lastValue = 0;

        while (!leftIntervalPoints.isEmpty() && !rightIntervalPoints.isEmpty()) {
            if (leftIntervalPoints.get(0).position < rightIntervalPoints.get(0).position) {
                int currentPosition = leftIntervalPoints.get(0).position;
                leftValue = leftIntervalPoints.get(0).value;

                if (leftValue < rightValue) {
                    leftIntervalPoints.remove(0);
                    if (lastValue != rightValue) {
                        mergedIntervalPoints.add(new IntervalPoint(currentPosition, rightValue));
                    }
                } else {
                    lastValue = leftValue;
                    leftIntervalPoints.remove(0);
                    mergedIntervalPoints.add(new IntervalPoint(currentPosition, leftValue));
                }
            } else {
                int currentPosition = rightIntervalPoints.get(0).position;
                rightValue = rightIntervalPoints.get(0).value;

                if (rightValue < leftValue) {
                    rightIntervalPoints.remove(0);
                    if (lastValue != leftValue) {
                        mergedIntervalPoints.add(new IntervalPoint(currentPosition, leftValue));
                    }
                } else {
                    lastValue = rightValue;
                    rightIntervalPoints.remove(0);
                    mergedIntervalPoints.add(new IntervalPoint(currentPosition, rightValue));
                }
            }
        }

        while (!leftIntervalPoints.isEmpty()) {
            mergedIntervalPoints.add(leftIntervalPoints.remove(0));
        }

        while (!rightIntervalPoints.isEmpty()) {
            mergedIntervalPoints.add(rightIntervalPoints.remove(0));
        }

        return mergedIntervalPoints;
    }

    public class IntervalPoint {
        public int position;
        public int value;

        public IntervalPoint(int position, int value) {
            this.position = position;
            this.value = value;
        }
    }

    public class Segment {
        public int start;
        public int middle;
        public int end;

        public Segment(int start, int middle, int end) {
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }
}