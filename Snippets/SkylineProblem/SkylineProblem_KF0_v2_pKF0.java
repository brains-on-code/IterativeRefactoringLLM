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

    private final Building[] buildings;
    private int buildingCount;

    public SkylineProblem(int capacity) {
        this.buildings = new Building[capacity];
        this.buildingCount = 0;
    }

    /**
     * Adds a building with the given left, height, and right values to the
     * buildings list.
     *
     * @param left   The left x-coordinate of the building.
     * @param height The height of the building.
     * @param right  The right x-coordinate of the building.
     */
    public void add(int left, int height, int right) {
        buildings[buildingCount++] = new Building(left, height, right);
    }

    /**
     * Computes the skyline for a range of buildings using the divide-and-conquer
     * strategy.
     *
     * @param start The starting index of the buildings to process.
     * @param end   The ending index of the buildings to process.
     * @return A list of {@link Skyline} objects representing the computed skyline.
     */
    public List<Skyline> findSkyline(int start, int end) {
        if (start == end) {
            return createSkylineForSingleBuilding(buildings[start]);
        }

        int mid = start + (end - start) / 2;

        List<Skyline> leftSkyline = findSkyline(start, mid);
        List<Skyline> rightSkyline = findSkyline(mid + 1, end);

        return mergeSkyline(leftSkyline, rightSkyline);
    }

    private List<Skyline> createSkylineForSingleBuilding(Building building) {
        List<Skyline> skyline = new ArrayList<>(2);
        skyline.add(new Skyline(building.left, building.height));
        skyline.add(new Skyline(building.right, 0));
        return skyline;
    }

    /**
     * Merges two skylines (leftSkyline and rightSkyline) into one combined skyline.
     *
     * @param leftSkyline  The first skyline list.
     * @param rightSkyline The second skyline list.
     * @return A list of {@link Skyline} objects representing the merged skyline.
     */
    public List<Skyline> mergeSkyline(List<Skyline> leftSkyline, List<Skyline> rightSkyline) {
        List<Skyline> merged = new ArrayList<>();

        int leftHeight = 0;
        int rightHeight = 0;
        int currentMaxHeight = 0;

        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftSkyline.size() && rightIndex < rightSkyline.size()) {
            Skyline leftPoint = leftSkyline.get(leftIndex);
            Skyline rightPoint = rightSkyline.get(rightIndex);

            int xCoordinate;
            if (leftPoint.x < rightPoint.x) {
                xCoordinate = leftPoint.x;
                leftHeight = leftPoint.height;
                leftIndex++;
            } else {
                xCoordinate = rightPoint.x;
                rightHeight = rightPoint.height;
                rightIndex++;
            }

            int maxHeightAtX = Math.max(leftHeight, rightHeight);
            if (maxHeightAtX != currentMaxHeight) {
                merged.add(new Skyline(xCoordinate, maxHeightAtX));
                currentMaxHeight = maxHeightAtX;
            }
        }

        appendRemainingPoints(merged, leftSkyline, leftIndex);
        appendRemainingPoints(merged, rightSkyline, rightIndex);

        return merged;
    }

    private void appendRemainingPoints(List<Skyline> result, List<Skyline> source, int startIndex) {
        for (int i = startIndex; i < source.size(); i++) {
            result.add(source.get(i));
        }
    }

    /**
     * A class representing a point in the skyline with its x-coordinate and height.
     */
    public static class Skyline {
        public final int x;
        public final int height;

        /**
         * Constructor for the {@code Skyline} class.
         *
         * @param x      The x-coordinate of the skyline point.
         * @param height The height of the skyline at the given coordinate.
         */
        public Skyline(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }

    /**
     * A class representing a building with its left, height, and right
     * x-coordinates.
     */
    public static class Building {
        public final int left;
        public final int height;
        public final int right;

        /**
         * Constructor for the {@code Building} class.
         *
         * @param left   The left x-coordinate of the building.
         * @param height The height of the building.
         * @param right  The right x-coordinate of the building.
         */
        public Building(int left, int height, int right) {
            this.left = left;
            this.height = height;
            this.right = right;
        }
    }
}