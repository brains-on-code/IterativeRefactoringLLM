package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the skyline problem using a divide-and-conquer approach.
 */
public class SkylineProblem {

    private final Building[] buildings;
    private int count;

    /**
     * Creates a skyline problem instance with capacity for the given number of buildings.
     *
     * @param capacity maximum number of buildings
     */
    public SkylineProblem(int capacity) {
        this.buildings = new Building[capacity];
        this.count = 0;
    }

    /**
     * Adds a building with the given left, height, and right values.
     *
     * @param left   the left x-coordinate of the building
     * @param height the height of the building
     * @param right  the right x-coordinate of the building
     */
    public void add(int left, int height, int right) {
        buildings[count++] = new Building(left, height, right);
    }

    /**
     * Computes the skyline for buildings in the range [start, end] (inclusive)
     * using a divide-and-conquer strategy.
     *
     * @param start the starting index of the buildings to process
     * @param end   the ending index of the buildings to process
     * @return a list of {@link Skyline} points representing the skyline
     */
    public List<Skyline> findSkyline(int start, int end) {
        if (start == end) {
            List<Skyline> result = new ArrayList<>();
            Building building = buildings[start];
            result.add(new Skyline(building.left, building.height));
            result.add(new Skyline(building.right, 0));
            return result;
        }

        int mid = (start + end) / 2;

        List<Skyline> leftSkyline = findSkyline(start, mid);
        List<Skyline> rightSkyline = findSkyline(mid + 1, end);

        return mergeSkyline(leftSkyline, rightSkyline);
    }

    /**
     * Merges two skylines into a single skyline.
     *
     * @param leftSkyline  the first skyline
     * @param rightSkyline the second skyline
     * @return a list of {@link Skyline} points representing the merged skyline
     */
    public List<Skyline> mergeSkyline(List<Skyline> leftSkyline, List<Skyline> rightSkyline) {
        int currentLeftHeight = 0;
        int currentRightHeight = 0;
        int currentMaxHeight = 0;

        List<Skyline> merged = new ArrayList<>();

        while (!leftSkyline.isEmpty() && !rightSkyline.isEmpty()) {
            Skyline leftPoint = leftSkyline.get(0);
            Skyline rightPoint = rightSkyline.get(0);

            if (leftPoint.x < rightPoint.x) {
                int x = leftPoint.x;
                currentLeftHeight = leftPoint.height;

                if (currentLeftHeight < currentRightHeight) {
                    leftSkyline.remove(0);
                    if (currentMaxHeight != currentRightHeight) {
                        merged.add(new Skyline(x, currentRightHeight));
                        currentMaxHeight = currentRightHeight;
                    }
                } else {
                    currentMaxHeight = currentLeftHeight;
                    leftSkyline.remove(0);
                    merged.add(new Skyline(x, currentLeftHeight));
                }
            } else {
                int x = rightPoint.x;
                currentRightHeight = rightPoint.height;

                if (currentRightHeight < currentLeftHeight) {
                    rightSkyline.remove(0);
                    if (currentMaxHeight != currentLeftHeight) {
                        merged.add(new Skyline(x, currentLeftHeight));
                        currentMaxHeight = currentLeftHeight;
                    }
                } else {
                    currentMaxHeight = currentRightHeight;
                    rightSkyline.remove(0);
                    merged.add(new Skyline(x, currentRightHeight));
                }
            }
        }

        merged.addAll(leftSkyline);
        merged.addAll(rightSkyline);

        return merged;
    }

    /**
     * Represents a point in the skyline with its x-coordinate and height.
     */
    public static class Skyline {
        public int x;
        public int height;

        /**
         * Creates a skyline point.
         *
         * @param x      the x-coordinate of the skyline point
         * @param height the height of the skyline at the given coordinate
         */
        public Skyline(int x, int height) {
            this.x = x;
            this.height = height;
        }
    }

    /**
     * Represents a building with its left, height, and right x-coordinates.
     */
    public static class Building {
        public int left;
        public int height;
        public int right;

        /**
         * Creates a building.
         *
         * @param left   the left x-coordinate of the building
         * @param height the height of the building
         * @param right  the right x-coordinate of the building
         */
        public Building(int left, int height, int right) {
            this.left = left;
            this.height = height;
            this.right = right;
        }
    }
}