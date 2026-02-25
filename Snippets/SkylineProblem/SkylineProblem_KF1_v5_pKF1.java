package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SegmentIntervalBuilder {

    private Segment[] segments;
    private int nextSegmentIndex;

    public void addSegment(int startX, int height, int endX) {
        segments[nextSegmentIndex++] = new Segment(startX, height, endX);
    }

    public List<IntervalPoint> buildIntervals(int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            List<IntervalPoint> intervalPoints = new ArrayList<>();
            Segment segment = segments[leftIndex];
            intervalPoints.add(new IntervalPoint(segment.startX, segment.height));
            intervalPoints.add(new IntervalPoint(segment.endX, 0));
            return intervalPoints;
        }

        int midIndex = (leftIndex + rightIndex) / 2;

        List<IntervalPoint> leftIntervals = buildIntervals(leftIndex, midIndex);
        List<IntervalPoint> rightIntervals = buildIntervals(midIndex + 1, rightIndex);
        return mergeIntervals(leftIntervals, rightIntervals);
    }

    public List<IntervalPoint> mergeIntervals(List<IntervalPoint> leftIntervals,
                                              List<IntervalPoint> rightIntervals) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int lastMergedHeight = 0;

        List<IntervalPoint> mergedIntervals = new ArrayList<>();

        while (!leftIntervals.isEmpty() && !rightIntervals.isEmpty()) {
            IntervalPoint leftPoint = leftIntervals.get(0);
            IntervalPoint rightPoint = rightIntervals.get(0);

            if (leftPoint.x < rightPoint.x) {
                int currentX = leftPoint.x;
                currentLeftHeight = leftPoint.height;

                if (currentLeftHeight < currentRightHeight) {
                    leftIntervals.remove(0);
                    if (lastMergedHeight != currentRightHeight) {
                        mergedIntervals.add(new IntervalPoint(currentX, currentRightHeight));
                    }
                } else {
                    lastMergedHeight = currentLeftHeight;
                    leftIntervals.remove(0);
                    mergedIntervals.add(new IntervalPoint(currentX, currentLeftHeight));
                }
            } else {
                int currentX = rightPoint.x;
                currentRightHeight = rightPoint.height;

                if (currentRightHeight < currentLeftHeight) {
                    rightIntervals.remove(0);
                    if (lastMergedHeight != currentLeftHeight) {
                        mergedIntervals.add(new IntervalPoint(currentX, currentLeftHeight));
                    }
                } else {
                    lastMergedHeight = currentRightHeight;
                    rightIntervals.remove(0);
                    mergedIntervals.add(new IntervalPoint(currentX, currentRightHeight));
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
        public int x;
        public int height;

        public IntervalPoint(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }

    public class Segment {
        public int startX;
        public int height;
        public int endX;

        public Segment(int startX, int height, int endX) {
            this.startX = startX;
            this.height = height;
            this.endX = endX;
        }
    }
}