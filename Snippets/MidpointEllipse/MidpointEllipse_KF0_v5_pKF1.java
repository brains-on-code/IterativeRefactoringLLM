package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The MidpointEllipse class implements the Midpoint Ellipse Drawing Algorithm.
 * This algorithm efficiently computes the points on an ellipse by dividing it into two regions
 * and using decision parameters to determine the next point to plot.
 */
public final class MidpointEllipse {

    private MidpointEllipse() {
        // Private constructor to prevent instantiation
    }

    /**
     * Draws an ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param centerX the x-coordinate of the center of the ellipse
     * @param centerY the y-coordinate of the center of the ellipse
     * @param radiusX the length of the semi-major axis (horizontal radius)
     * @param radiusY the length of the semi-minor axis (vertical radius)
     * @return a list of points (represented as int arrays) that form the ellipse
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int radiusX, int radiusY) {
        List<int[]> ellipsePoints = new ArrayList<>();

        // Handle degenerate cases with early returns
        if (radiusX == 0 && radiusY == 0) {
            ellipsePoints.add(new int[] {centerX, centerY}); // Only the center point
            return ellipsePoints;
        }

        if (radiusX == 0) {
            // Horizontal radius is zero, create a vertical line
            for (int y = centerY - radiusY; y <= centerY + radiusY; y++) {
                ellipsePoints.add(new int[] {centerX, y});
            }
            return ellipsePoints;
        }

        if (radiusY == 0) {
            // Vertical radius is zero, create a horizontal line
            for (int x = centerX - radiusX; x <= centerX + radiusX; x++) {
                ellipsePoints.add(new int[] {x, centerY});
            }
            return ellipsePoints;
        }

        // Normal case: Non-degenerate ellipse
        computeEllipsePoints(ellipsePoints, centerX, centerY, radiusX, radiusY);

        return ellipsePoints;
    }

    /**
     * Computes points of a non-degenerate ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param ellipsePoints the collection to which points will be added
     * @param centerX       the x-coordinate of the center of the ellipse
     * @param centerY       the y-coordinate of the center of the ellipse
     * @param radiusX       the length of the semi-major axis (horizontal radius)
     * @param radiusY       the length of the semi-minor axis (vertical radius)
     */
    private static void computeEllipsePoints(
            Collection<int[]> ellipsePoints,
            int centerX,
            int centerY,
            int radiusX,
            int radiusY
    ) {
        int pointX = 0;       // Initial x-coordinate
        int pointY = radiusY; // Initial y-coordinate

        int radiusYSquared = radiusY * radiusY;
        int radiusXSquared = radiusX * radiusX;

        // Region 1: Initial decision parameter
        double region1DecisionParameter =
                radiusYSquared
                        - (radiusXSquared * radiusY)
                        + (0.25 * radiusXSquared);

        double deltaX = 2.0 * radiusYSquared * pointX;
        double deltaY = 2.0 * radiusXSquared * pointY;

        // Region 1: When the slope is less than 1
        while (deltaX < deltaY) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, pointX, pointY);

            if (region1DecisionParameter < 0) {
                pointX++;
                deltaX += 2 * radiusYSquared;
                region1DecisionParameter += deltaX + radiusYSquared;
            } else {
                pointX++;
                pointY--;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                region1DecisionParameter += deltaX - deltaY + radiusYSquared;
            }
        }

        // Region 2: Initial decision parameter for the second region
        double region2DecisionParameter =
                radiusYSquared * (pointX + 0.5) * (pointX + 0.5)
                        + radiusXSquared * (pointY - 1) * (pointY - 1)
                        - radiusXSquared * radiusYSquared;

        // Region 2: When the slope is greater than or equal to 1
        while (pointY >= 0) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, pointX, pointY);

            if (region2DecisionParameter > 0) {
                pointY--;
                deltaY -= 2 * radiusXSquared;
                region2DecisionParameter += radiusXSquared - deltaY;
            } else {
                pointY--;
                pointX++;
                deltaX += 2 * radiusYSquared;
                deltaY -= 2 * radiusXSquared;
                region2DecisionParameter += deltaX - deltaY + radiusXSquared;
            }
        }
    }

    /**
     * Adds points for all four quadrants of the ellipse based on symmetry.
     *
     * @param ellipsePoints the collection to which points will be added
     * @param centerX       the x-coordinate of the center of the ellipse
     * @param centerY       the y-coordinate of the center of the ellipse
     * @param offsetX       the x-coordinate relative to the center
     * @param offsetY       the y-coordinate relative to the center
     */
    private static void addSymmetricEllipsePoints(
            Collection<int[]> ellipsePoints,
            int centerX,
            int centerY,
            int offsetX,
            int offsetY
    ) {
        ellipsePoints.add(new int[] {centerX + offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY + offsetY});
        ellipsePoints.add(new int[] {centerX + offsetX, centerY - offsetY});
        ellipsePoints.add(new int[] {centerX - offsetX, centerY - offsetY});
    }
}