package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SkylineProblem {

    private final Building[] buildings;
    private int buildingCount;

    public SkylineProblem(int capacity) {
        this.buildings = new Building[capacity];
        this.buildingCount = 0;
    }

    public void addBuilding(int left, int height, int right) {
        buildings[buildingCount++] = new Building(left, height, right);
    }

    /**
     * Computes the skyline for buildings in the range [start, end] using
     * a divide-and-conquer approach.
     */
    public List<SkylinePoint> findSkyline(int start, int end) {
        if (start == end) {
            Building building = buildings[start];
            List<SkylinePoint> skyline = new ArrayList<>(2);
            skyline.add(new SkylinePoint(building.left, building.height));
            skyline.add(new SkylinePoint(building.right, 0));
            return skyline;
        }

        int mid = (start + end) / 2;

        List<SkylinePoint> leftSkyline = findSkyline(start, mid);
        List<SkylinePoint> rightSkyline = findSkyline(mid + 1, end);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    /**
     * Merges two skylines into a single skyline.
     */
    public List<SkylinePoint> mergeSkylines(List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {
        Queue<SkylinePoint> leftQueue = new LinkedList<>(leftSkyline);
        Queue<SkylinePoint> rightQueue = new LinkedList<>(rightSkyline);
        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        while (!leftQueue.isEmpty() && !rightQueue.isEmpty()) {
            SkylinePoint leftPoint = leftQueue.peek();
            SkylinePoint rightPoint = rightQueue.peek();

            int currentX;
            if (leftPoint.x < rightPoint.x) {
                currentX = leftPoint.x;
                currentLeftHeight = leftPoint.height;
                leftQueue.poll();
            } else if (rightPoint.x < leftPoint.x) {
                currentX = rightPoint.x;
                currentRightHeight = rightPoint.height;
                rightQueue.poll();
            } else {
                currentX = leftPoint.x;
                currentLeftHeight = leftPoint.height;
                currentRightHeight = rightPoint.height;
                leftQueue.poll();
                rightQueue.poll();
            }

            int newMaxHeight = Math.max(currentLeftHeight, currentRightHeight);
            if (newMaxHeight != currentMaxHeight) {
                mergedSkyline.add(new SkylinePoint(currentX, newMaxHeight));
                currentMaxHeight = newMaxHeight;
            }
        }

        appendRemainingPoints(mergedSkyline, leftQueue, currentMaxHeight);
        appendRemainingPoints(mergedSkyline, rightQueue, currentMaxHeight);

        return mergedSkyline;
    }

    /**
     * Appends remaining points from a queue to the merged skyline,
     * avoiding redundant points with the same height.
     */
    private void appendRemainingPoints(
            List<SkylinePoint> mergedSkyline,
            Queue<SkylinePoint> queue,
            int currentMaxHeight
    ) {
        int lastHeight = currentMaxHeight;
        while (!queue.isEmpty()) {
            SkylinePoint point = queue.poll();
            if (mergedSkyline.isEmpty() || point.height != lastHeight) {
                mergedSkyline.add(point);
                lastHeight = point.height;
            }
        }
    }

    public static class SkylinePoint {
        public final int x;
        public final int height;

        public SkylinePoint(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }

    public static class Building {
        public final int left;
        public final int height;
        public final int right;

        public Building(int left, int height, int right) {
            this.left = left;
            this.height = height;
            this.right = right;
        }
    }
}