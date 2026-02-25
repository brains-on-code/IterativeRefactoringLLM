package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SegmentIntervalBuilder {

    private Segment[] segments;
    private int segmentIndex;

    public void addSegment(int startPosition, int middlePosition, int endPosition) {
        segments[segmentIndex++] = new Segment(startPosition, middlePosition, endPosition);
    }

    public List<IntervalPoint> buildIntervals(int leftSegmentIndex, int rightSegmentIndex) {
        if (leftSegmentIndex == rightSegmentIndex) {
            List<IntervalPoint> intervalPoints = new ArrayList<>();
            Segment segment = segments[leftSegmentIndex];
            intervalPoints.add(new IntervalPoint(segment.startPosition, segment.middlePosition));
            intervalPoints.add(new IntervalPoint(segment.endPosition, 0));
            return intervalPoints;
        }

        int middleSegmentIndex = (leftSegmentIndex + rightSegmentIndex) / 2;

        List<IntervalPoint> leftIntervalPoints = buildIntervals(leftSegmentIndex, middleSegmentIndex);
        List<IntervalPoint> rightIntervalPoints = buildIntervals(middleSegmentIndex + 1, rightSegmentIndex);
        return mergeIntervals(leftIntervalPoints, rightIntervalPoints);
    }

    public List<IntervalPoint> mergeIntervals(List<IntervalPoint> leftIntervalPoints,
                                              List<IntervalPoint> rightIntervalPoints) {
        int leftCurrentHeight = 0;
        int rightCurrentHeight = 0;
        int lastMergedHeight = 0;

        List<IntervalPoint> mergedIntervalPoints = new ArrayList<>();

        while (!leftIntervalPoints.isEmpty() && !rightIntervalPoints.isEmpty()) {
            IntervalPoint leftPoint = leftIntervalPoints.get(0);
            IntervalPoint rightPoint = rightIntervalPoints.get(0);

            if (leftPoint.position < rightPoint.position) {
                int currentPosition = leftPoint.position;
                leftCurrentHeight = leftPoint.height;

                if (leftCurrentHeight < rightCurrentHeight) {
                    leftIntervalPoints.remove(0);
                    if (lastMergedHeight != rightCurrentHeight) {
                        mergedIntervalPoints.add(new IntervalPoint(currentPosition, rightCurrentHeight));
                    }
                } else {
                    lastMergedHeight = leftCurrentHeight;
                    leftIntervalPoints.remove(0);
                    mergedIntervalPoints.add(new IntervalPoint(currentPosition, leftCurrentHeight));
                }
            } else {
                int currentPosition = rightPoint.position;
                rightCurrentHeight = rightPoint.height;

                if (rightCurrentHeight < leftCurrentHeight) {
                    rightIntervalPoints.remove(0);
                    if (lastMergedHeight != leftCurrentHeight) {
                        mergedIntervalPoints.add(new IntervalPoint(currentPosition, leftCurrentHeight));
                    }
                } else {
                    lastMergedHeight = rightCurrentHeight;
                    rightIntervalPoints.remove(0);
                    mergedIntervalPoints.add(new IntervalPoint(currentPosition, rightCurrentHeight));
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