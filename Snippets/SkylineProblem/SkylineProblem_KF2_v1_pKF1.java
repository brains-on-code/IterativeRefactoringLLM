package com.thealgorithms.others;

import java.util.ArrayList;

public class SkylineProblem {

    Building[] buildings;
    int buildingCount;

    public void addBuilding(int left, int height, int right) {
        buildings[buildingCount++] = new Building(left, height, right);
    }

    public ArrayList<SkylinePoint> computeSkyline(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            ArrayList<SkylinePoint> skylinePoints = new ArrayList<>();
            skylinePoints.add(new SkylinePoint(buildings[startIndex].left, buildings[startIndex].height));
            skylinePoints.add(new SkylinePoint(buildings[endIndex].right, 0));
            return skylinePoints;
        }

        int midIndex = (startIndex + endIndex) / 2;

        ArrayList<SkylinePoint> leftSkyline = this.computeSkyline(startIndex, midIndex);
        ArrayList<SkylinePoint> rightSkyline = this.computeSkyline(midIndex + 1, endIndex);
        return this.mergeSkylines(leftSkyline, rightSkyline);
    }

    public ArrayList<SkylinePoint> mergeSkylines(ArrayList<SkylinePoint> leftSkyline, ArrayList<SkylinePoint> rightSkyline) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        ArrayList<SkylinePoint> mergedSkyline = new ArrayList<>();
        int currentMaxHeight = 0;

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            if (leftSkyline.get(0).xCoordinate < rightSkyline.get(0).xCoordinate) {
                int currentX = leftSkyline.get(0).xCoordinate;
                currentLeftHeight = leftSkyline.get(0).height;

                if (currentLeftHeight < currentRightHeight) {
                    leftSkyline.remove(0);
                    if (currentMaxHeight != currentRightHeight) {
                        mergedSkyline.add(new SkylinePoint(currentX, currentRightHeight));
                    }
                } else {
                    currentMaxHeight = currentLeftHeight;
                    leftSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentX, currentLeftHeight));
                }
            } else {
                int currentX = rightSkyline.get(0).xCoordinate;
                currentRightHeight = rightSkyline.get(0).height;

                if (currentRightHeight < currentLeftHeight) {
                    rightSkyline.remove(0);
                    if (currentMaxHeight != currentLeftHeight) {
                        mergedSkyline.add(new SkylinePoint(currentX, currentLeftHeight));
                    }
                } else {
                    currentMaxHeight = currentRightHeight;
                    rightSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentX, currentRightHeight));
                }
            }
        }

        while (!leftSkyline.isEmpty()) {
            mergedSkyline.add(leftSkyline.get(0));
            leftSkyline.remove(0);
        }

        while (!rightSkyline.isEmpty()) {
            mergedSkyline.add(rightSkyline.get(0));
            rightSkyline.remove(0);
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