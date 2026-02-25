package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SkylineProblem {

    private Building[] buildings;
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
            List<SkylinePoint> skyline = new ArrayList<>();
            skyline.add(new SkylinePoint(buildings[start].left, buildings[start].height));
            skyline.add(new SkylinePoint(buildings[end].right, 0));
            return skyline;
        }

        int mid = (start + end) / 2;

        List<SkylinePoint> leftSkyline = findSkyline(start, mid);
        List<SkylinePoint> rightSkyline = findSkyline(mid + 1, end);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public List<SkylinePoint> mergeSkylines(List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            SkylinePoint leftPoint = leftSkyline.get(0);
            SkylinePoint rightPoint = rightSkyline.get(0);

            if (leftPoint.x < rightPoint.x) {
                int currentX = leftPoint.x;
                currentLeftHeight = leftPoint.height;

                if (currentLeftHeight < currentRightHeight) {
                    leftSkyline.remove(0);
                    if (currentMaxHeight != currentRightHeight) {
                        mergedSkyline.add(new SkylinePoint(currentX, currentRightHeight));
                        currentMaxHeight = currentRightHeight;
                    }
                } else {
                    currentMaxHeight = currentLeftHeight;
                    leftSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentX, currentLeftHeight));
                }
            } else {
                int currentX = rightPoint.x;
                currentRightHeight = rightPoint.height;

                if (currentRightHeight < currentLeftHeight) {
                    rightSkyline.remove(0);
                    if (currentMaxHeight != currentLeftHeight) {
                        mergedSkyline.add(new SkylinePoint(currentX, currentLeftHeight));
                        currentMaxHeight = currentLeftHeight;
                    }
                } else {
                    currentMaxHeight = currentRightHeight;
                    rightSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentX, currentRightHeight));
                }
            }
        }

        while (!leftSkyline.isEmpty()) {
            mergedSkyline.add(leftSkyline.remove(0));
        }

        while (!rightSkyline.isEmpty()) {
            mergedSkyline.add(rightSkyline.remove(0));
        }

        return mergedSkyline;
    }

    public static class SkylinePoint {
        public int x;
        public int height;

        public SkylinePoint(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }

    public static class Building {
        public int left;
        public int height;
        public int right;

        public Building(int left, int height, int right) {
            this.left = left;
            this.height = height;
            this.right = right;
        }
    }
}