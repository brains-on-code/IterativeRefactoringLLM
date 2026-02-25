package com.thealgorithms.others;

import java.util.ArrayList;

/**
 * The {@code SkylineProblem} class is used to solve the skyline problem using a
 * divide-and-conquer approach.
 * It reads input for building data, processes it to find the skyline, and
 * prints the skyline.
 */
public class SkylineProblem {

    Building[] buildings;
    int numberOfBuildings;

    /**
     * Adds a building with the given left, height, and right values to the
     * buildings list.
     *
     * @param leftX   The left x-coordinate of the building.
     * @param height  The height of the building.
     * @param rightX  The right x-coordinate of the building.
     */
    public void addBuilding(int leftX, int height, int rightX) {
        buildings[numberOfBuildings++] = new Building(leftX, height, rightX);
    }

    /**
     * Computes the skyline for a range of buildings using the divide-and-conquer
     * strategy.
     *
     * @param startIndex The starting index of the buildings to process.
     * @param endIndex   The ending index of the buildings to process.
     * @return A list of {@link SkylinePoint} objects representing the computed skyline.
     */
    public ArrayList<SkylinePoint> findSkyline(int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            ArrayList<SkylinePoint> skyline = new ArrayList<>();
            skyline.add(new SkylinePoint(buildings[startIndex].leftX, buildings[startIndex].height));
            skyline.add(new SkylinePoint(buildings[endIndex].rightX, 0));
            return skyline;
        }

        int middleIndex = (startIndex + endIndex) / 2;

        ArrayList<SkylinePoint> leftSkyline = this.findSkyline(startIndex, middleIndex);
        ArrayList<SkylinePoint> rightSkyline = this.findSkyline(middleIndex + 1, endIndex);
        return this.mergeSkylines(leftSkyline, rightSkyline);
    }

    /**
     * Merges two skylines (leftSkyline and rightSkyline) into one combined skyline.
     *
     * @param leftSkyline  The first skyline list.
     * @param rightSkyline The second skyline list.
     * @return A list of {@link SkylinePoint} objects representing the merged skyline.
     */
    public ArrayList<SkylinePoint> mergeSkylines(ArrayList<SkylinePoint> leftSkyline, ArrayList<SkylinePoint> rightSkyline) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        ArrayList<SkylinePoint> mergedSkyline = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            if (leftSkyline.get(0).xCoordinate < rightSkyline.get(0).xCoordinate) {
                int currentXCoordinate = leftSkyline.get(0).xCoordinate;
                currentLeftHeight = leftSkyline.get(0).height;

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
                int currentXCoordinate = rightSkyline.get(0).xCoordinate;
                currentRightHeight = rightSkyline.get(0).height;

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
            mergedSkyline.add(leftSkyline.get(0));
            leftSkyline.remove(0);
        }

        while (!rightSkyline.isEmpty()) {
            mergedSkyline.add(rightSkyline.get(0));
            rightSkyline.remove(0);
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