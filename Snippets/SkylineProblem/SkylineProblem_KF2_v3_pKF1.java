package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SkylineProblem {

    private Building[] buildings;
    private int numberOfBuildings;

    public void addBuilding(int leftX, int height, int rightX) {
        buildings[numberOfBuildings++] = new Building(leftX, height, rightX);
    }

    public List<SkylinePoint> computeSkyline(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            List<SkylinePoint> skyline = new ArrayList<>();
            Building building = buildings[startIndex];
            skyline.add(new SkylinePoint(building.leftX, building.height));
            skyline.add(new SkylinePoint(building.rightX, 0));
            return skyline;
        }

        int middleIndex = (startIndex + endIndex) / 2;

        List<SkylinePoint> leftSkyline = computeSkyline(startIndex, middleIndex);
        List<SkylinePoint> rightSkyline = computeSkyline(middleIndex + 1, endIndex);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public List<SkylinePoint> mergeSkylines(
            List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {

        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            SkylinePoint leftPoint = leftSkyline.get(0);
            SkylinePoint rightPoint = rightSkyline.get(0);

            if (leftPoint.xCoordinate < rightPoint.xCoordinate) {
                int currentX = leftPoint.xCoordinate;
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
                int currentX = rightPoint.xCoordinate;
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

    public class SkylinePoint {
        public int xCoordinate;
        public int height;

        public SkylinePoint(int xCoordinate, int height) {
            this.xCoordinate = xCoordinate;
            this.height = height;
        }
    }

    public class Building {
        public int leftX;
        public int height;
        public int rightX;

        public Building(int leftX, int height, int rightX) {
            this.leftX = leftX;
            this.height = height;
            this.rightX = rightX;
        }
    }
}