package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

public class SkylineProblem {

    private Building[] buildings;
    private int buildingCount;

    public void addBuilding(int leftX, int height, int rightX) {
        buildings[buildingCount++] = new Building(leftX, height, rightX);
    }

    public List<SkylinePoint> computeSkyline(int startBuildingIndex, int endBuildingIndex) {
        if (startBuildingIndex == endBuildingIndex) {
            List<SkylinePoint> skyline = new ArrayList<>();
            Building building = buildings[startBuildingIndex];
            skyline.add(new SkylinePoint(building.leftX, building.height));
            skyline.add(new SkylinePoint(building.rightX, 0));
            return skyline;
        }

        int middleBuildingIndex = (startBuildingIndex + endBuildingIndex) / 2;

        List<SkylinePoint> leftSkyline =
                computeSkyline(startBuildingIndex, middleBuildingIndex);
        List<SkylinePoint> rightSkyline =
                computeSkyline(middleBuildingIndex + 1, endBuildingIndex);

        return mergeSkylines(leftSkyline, rightSkyline);
    }

    public List<SkylinePoint> mergeSkylines(
            List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {

        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            SkylinePoint nextLeftPoint = leftSkyline.get(0);
            SkylinePoint nextRightPoint = rightSkyline.get(0);

            if (nextLeftPoint.xCoordinate < nextRightPoint.xCoordinate) {
                int currentX = nextLeftPoint.xCoordinate;
                currentLeftHeight = nextLeftPoint.height;

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
                int currentX = nextRightPoint.xCoordinate;
                currentRightHeight = nextRightPoint.height;

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