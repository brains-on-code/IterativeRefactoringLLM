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

        int currentLeftSkylineHeight = 0;
        int currentRightSkylineHeight = 0;
        int currentMaxSkylineHeight = 0;

        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            SkylinePoint leftSkylinePoint = leftSkyline.get(0);
            SkylinePoint rightSkylinePoint = rightSkyline.get(0);

            if (leftSkylinePoint.xCoordinate < rightSkylinePoint.xCoordinate) {
                int currentXCoordinate = leftSkylinePoint.xCoordinate;
                currentLeftSkylineHeight = leftSkylinePoint.height;

                if (currentLeftSkylineHeight < currentRightSkylineHeight) {
                    leftSkyline.remove(0);
                    if (currentMaxSkylineHeight != currentRightSkylineHeight) {
                        mergedSkyline.add(
                                new SkylinePoint(currentXCoordinate, currentRightSkylineHeight));
                        currentMaxSkylineHeight = currentRightSkylineHeight;
                    }
                } else {
                    currentMaxSkylineHeight = currentLeftSkylineHeight;
                    leftSkyline.remove(0);
                    mergedSkyline.add(
                            new SkylinePoint(currentXCoordinate, currentLeftSkylineHeight));
                }
            } else {
                int currentXCoordinate = rightSkylinePoint.xCoordinate;
                currentRightSkylineHeight = rightSkylinePoint.height;

                if (currentRightSkylineHeight < currentLeftSkylineHeight) {
                    rightSkyline.remove(0);
                    if (currentMaxSkylineHeight != currentLeftSkylineHeight) {
                        mergedSkyline.add(
                                new SkylinePoint(currentXCoordinate, currentLeftSkylineHeight));
                        currentMaxSkylineHeight = currentLeftSkylineHeight;
                    }
                } else {
                    currentMaxSkylineHeight = currentRightSkylineHeight;
                    rightSkyline.remove(0);
                    mergedSkyline.add(
                            new SkylinePoint(currentXCoordinate, currentRightSkylineHeight));
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