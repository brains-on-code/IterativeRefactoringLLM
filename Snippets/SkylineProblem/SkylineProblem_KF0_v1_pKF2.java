package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.List;

/**
 * Solves the skyline problem using a divide-and-conquer approach.
 */
public class SkylineProblem {

    private Building[] buildings;
    private int count;

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
            result.add(new Skyline(buildings[start].left, buildings[start].height));
            result.add(new Skyline(buildings[start].right, 0));
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
     * @param sky1 the first skyline
     * @param sky2 the second skyline
     * @return a list of {@link Skyline} points representing the merged skyline
     */
    public List<Skyline> mergeSkyline(List<Skyline> sky1, List<Skyline> sky2) {
        int currentH1 = 0;
        int currentH2 = 0;
        int maxH = 0;

        List<Skyline> merged = new ArrayList<>();

        while (!sky1.isEmpty() && !sky2.isEmpty()) {
            Skyline p1 = sky1.get(0);
            Skyline p2 = sky2.get(0);

            if (p1.coordinates < p2.coordinates) {
                int x = p1.coordinates;
                currentH1 = p1.height;

                if (currentH1 < currentH2) {
                    sky1.remove(0);
                    if (maxH != currentH2) {
                        merged.add(new Skyline(x, currentH2));
                        maxH = currentH2;
                    }
                } else {
                    maxH = currentH1;
                    sky1.remove(0);
                    merged.add(new Skyline(x, currentH1));
                }
            } else {
                int x = p2.coordinates;
                currentH2 = p2.height;

                if (currentH2 < currentH1) {
                    sky2.remove(0);
                    if (maxH != currentH1) {
                        merged.add(new Skyline(x, currentH1));
                        maxH = currentH1;
                    }
                } else {
                    maxH = currentH2;
                    sky2.remove(0);
                    merged.add(new Skyline(x, currentH2));
                }
            }
        }

        merged.addAll(sky1);
        merged.addAll(sky2);

        return merged;
    }

    /**
     * Represents a point in the skyline with its x-coordinate and height.
     */
    public static class Skyline {
        public int coordinates;
        public int height;

        /**
         * Creates a skyline point.
         *
         * @param coordinates the x-coordinate of the skyline point
         * @param height      the height of the skyline at the given coordinate
         */
        public Skyline(int coordinates, int height) {
            this.coordinates = coordinates;
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