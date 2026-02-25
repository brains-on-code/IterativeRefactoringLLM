package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

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

    public List<SkylinePoint> findSkyline(int start, int end) {
        if (start == end) {
            return createSkylineForSingleBuilding(buildings[start]);
        }

        int mid = (start + end) / 2;
        List<SkylinePoint> leftSkyline = findSkyline(start, mid);
        List<SkylinePoint> rightSkyline = findSkyline(mid + 1, end);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    private List<SkylinePoint> createSkylineForSingleBuilding(Building building) {
        List<SkylinePoint> skyline = new ArrayList<>();
        skyline.add(new SkylinePoint(building.left, building.height));
        skyline.add(new SkylinePoint(building.right, 0));
        return skyline;
    }

    public List<SkylinePoint> mergeSkylines(List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {
        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        int leftHeight = 0;
        int rightHeight = 0;
        int currentMaxHeight = 0;

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftSkyline.size() && rightIndex < rightSkyline.size()) {
            SkylinePoint leftPoint = leftSkyline.get(leftIndex);
            SkylinePoint rightPoint = rightSkyline.get(rightIndex);

            int x;
            if (leftPoint.x < rightPoint.x) {
                x = leftPoint.x;
                leftHeight = leftPoint.height;
                leftIndex++;
            } else if (rightPoint.x < leftPoint.x) {
                x = rightPoint.x;
                rightHeight = rightPoint.height;
                rightIndex++;
            } else {
                x = leftPoint.x;
                leftHeight = leftPoint.height;
                rightHeight = rightPoint.height;
                leftIndex++;
                rightIndex++;
            }

            int maxHeight = Math.max(leftHeight, rightHeight);
            if (currentMaxHeight != maxHeight) {
                mergedSkyline.add(new SkylinePoint(x, maxHeight));
                currentMaxHeight = maxHeight;
            }
        }

        currentMaxHeight = appendRemainingPoints(mergedSkyline, leftSkyline, leftIndex, currentMaxHeight);
        appendRemainingPoints(mergedSkyline, rightSkyline, rightIndex, currentMaxHeight);

        return mergedSkyline;
    }

    private int appendRemainingPoints(List<SkylinePoint> result,
                                      List<SkylinePoint> source,
                                      int startIndex,
                                      int currentMaxHeight) {
        int lastHeight = currentMaxHeight;
        for (int i = startIndex; i < source.size(); i++) {
            SkylinePoint point = source.get(i);
            if (point.height != lastHeight) {
                result.add(point);
                lastHeight = point.height;
            }
        }
        return lastHeight;
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