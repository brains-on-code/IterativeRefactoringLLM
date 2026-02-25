package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SkylineProblem} class is used to solve the skyline problem using a
 * divide-and-conquer approach.
 * It reads input for building data, processes it to find the skyline, and
 * prints the skyline.
 */
public class SkylineProblem {

    Building[] buildings;
    int buildingCount;

    /**
     * Adds a building with the given left, height, and right values to the
     * buildings list.
     *
     * @param leftX   The left x-coordinate of the building.
     * @param height  The height of the building.
     * @param rightX  The right x-coordinate of the building.
     */
    public void addBuilding(int leftX, int height, int rightX) {
        buildings[buildingCount++] = new Building(leftX, height, rightX);
    }

    /**
     * Computes the skyline for a range of buildings using the divide-and-conquer
     * strategy.
     *
     * @param startIndex The starting index of the buildings to process.
     * @param endIndex   The ending index of the buildings to process.
     * @return A list of {@link SkylinePoint} objects representing the computed skyline.
     */
    public List<SkylinePoint> findSkyline(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            List<SkylinePoint> skyline = new ArrayList<>();
            Building building = buildings[startIndex];
            skyline.add(new SkylinePoint(building.leftX, building.height));
            skyline.add(new SkylinePoint(building.rightX, 0));
            return skyline;
        }

        int middleIndex = (startIndex + endIndex) / 2;

        List<SkylinePoint> leftSkyline = findSkyline(startIndex, middleIndex);
        List<SkylinePoint> rightSkyline = findSkyline(middleIndex + 1, endIndex);
        return mergeSkylines(leftSkyline, rightSkyline);
    }

    /**
     * Merges two skylines (leftSkyline and rightSkyline) into one combined skyline.
     *
     * @param leftSkyline  The first skyline list.
     * @param rightSkyline The second skyline list.
     * @return A list of {@link SkylinePoint} objects representing the merged skyline.
     */
    public List<SkylinePoint> mergeSkylines(List<SkylinePoint> leftSkyline, List<SkylinePoint> rightSkyline) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        List<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            SkylinePoint leftPoint = leftSkyline.get(0);
            SkylinePoint rightPoint = rightSkyline.get(0);

            if (leftPoint.xCoordinate < rightPoint.xCoordinate) {
                int currentXCoordinate = leftPoint.xCoordinate;
                currentLeftHeight = leftPoint.height;

                if (currentLeftHeight < currentRightHeight) {
                    leftSkyline.remove(0);
                    if (currentMaxHeight != currentRightHeight) {
                        mergedSkyline.add(new SkylinePoint(currentXCoordinate, currentRightHeight));
                    }
                } else {
                    currentMaxHeight = currentLeftHeight;
                    leftSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentXCoordinate, currentLeftHeight));
                }
            } else {
                int currentXCoordinate = rightPoint.xCoordinate;
                currentRightHeight = rightPoint.height;

                if (currentRightHeight < currentLeftHeight) {
                    rightSkyline.remove(0);
                    if (currentMaxHeight != currentLeftHeight) {
                        mergedSkyline.add(new SkylinePoint(currentXCoordinate, currentLeftHeight));
                    }
                } else {
                    currentMaxHeight = currentRightHeight;
                    rightSkyline.remove(0);
                    mergedSkyline.add(new SkylinePoint(currentXCoordinate, currentRightHeight));
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

    /**
     * A class representing a point in the skyline with its x-coordinate and height.
     */
    public class SkylinePoint {
        public int xCoordinate;
        public int height;

        /**
         * Constructor for the {@code SkylinePoint} class.
         *
         * @param xCoordinate The x-coordinate of the skyline point.
         * @param height      The height of the skyline at the given coordinate.
         */
        public SkylinePoint(int xCoordinate, int height) {
            this.xCoordinate = xCoordinate;
            this.height = height;
        }
    }

    /**
     * A class representing a building with its left, height, and right
     * x-coordinates.
     */
    public class Building {
        public int leftX;
        public int height;
        public int rightX;

        /**
         * Constructor for the {@code Building} class.
         *
         * @param leftX   The left x-coordinate of the building.
         * @param height  The height of the building.
         * @param rightX  The right x-coordinate of the building.
         */
        public Building(int leftX, int height, int rightX) {
            this.leftX = leftX;
            this.height = height;
            this.rightX = rightX;
        }
    }
}