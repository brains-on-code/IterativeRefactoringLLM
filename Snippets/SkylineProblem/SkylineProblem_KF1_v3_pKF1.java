package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SegmentIntervalBuilder {

    private Segment[] segments;
    private int segmentCount;

    public void addSegment(int start, int middle, int end) {
        segments[segmentCount++] = new Segment(start, middle, end);
    }

    public List<IntervalPoint> buildIntervals(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            List<IntervalPoint> intervalPoints = new ArrayList<>();
            Segment segment = segments[startIndex];
            intervalPoints.add(new IntervalPoint(segment.startPosition, segment.middlePosition));
            intervalPoints.add(new IntervalPoint(segment.endPosition, 0));
            return intervalPoints;
        }

        int midIndex = (startIndex + endIndex) / 2;

        List<IntervalPoint> leftIntervals = buildIntervals(startIndex, midIndex);
        List<IntervalPoint> rightIntervals = buildIntervals(midIndex + 1, endIndex);
        return mergeIntervals(leftIntervals, rightIntervals);
    }

    public List<IntervalPoint> mergeIntervals(List<IntervalPoint> leftIntervals,
                                              List<IntervalPoint> rightIntervals) {
        int leftHeight = 0;
        int rightHeight = 0;
        int lastHeight = 0;

        List<IntervalPoint> mergedIntervals = new ArrayList<>();

        while (!leftIntervals.isEmpty() && !rightIntervals.isEmpty()) {
            IntervalPoint leftPoint = leftIntervals.get(0);
            IntervalPoint rightPoint = rightIntervals.get(0);

            if (leftPoint.position < rightPoint.position) {
                int currentPosition = leftPoint.position;
                leftHeight = leftPoint.height;

                if (leftHeight < rightHeight) {
                    leftIntervals.remove(0);
                    if (lastHeight != rightHeight) {
                        mergedIntervals.add(new IntervalPoint(currentPosition, rightHeight));
                    }
                } else {
                    lastHeight = leftHeight;
                    leftIntervals.remove(0);
                    mergedIntervals.add(new IntervalPoint(currentPosition, leftHeight));
                }
            } else {
                int currentPosition = rightPoint.position;
                rightHeight = rightPoint.height;

                if (rightHeight < leftHeight) {
                    rightIntervals.remove(0);
                    if (lastHeight != leftHeight) {
                        mergedIntervals.add(new IntervalPoint(currentPosition, leftHeight));
                    }
                } else {
                    lastHeight = rightHeight;
                    rightIntervals.remove(0);
                    mergedIntervals.add(new IntervalPoint(currentPosition, rightHeight));
                }
            }
        }

        while (!leftIntervals.isEmpty()) {
            mergedIntervals.add(leftIntervals.remove(0));
        }

        while (!rightIntervals.isEmpty()) {
            mergedIntervals.add(rightIntervals.remove(0));
        }

        return mergedIntervals;
    }

    public class IntervalPoint {
        public int position;
        public int height;

        public IntervalPoint(int position, int height) {
            this.position = position;
            this.height = height;
        }
    }

    public class Segment {
        public int startPosition;
        public int middlePosition;
        public int endPosition;

        public Segment(int startPosition, int middlePosition, int endPosition) {
            this.startPosition = startPosition;
            this.middlePosition = middlePosition;
            this.endPosition = endPosition;
        }
    }
}