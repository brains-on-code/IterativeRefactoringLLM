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
     * @param semiMajorAxis the length of the semi-major axis (horizontal radius)
     * @param semiMinorAxis the length of the semi-minor axis (vertical radius)
     * @return a list of points (represented as int arrays) that form the ellipse
     */
    public static List<int[]> drawEllipse(int centerX, int centerY, int semiMajorAxis, int semiMinorAxis) {
        List<int[]> ellipsePoints = new ArrayList<>();

        // Handle degenerate cases with early returns
        if (semiMajorAxis == 0 && semiMinorAxis == 0) {
            ellipsePoints.add(new int[] {centerX, centerY}); // Only the center point
            return ellipsePoints;
        }

        if (semiMajorAxis == 0) {
            // Semi-major axis is zero, create a vertical line
            for (int y = centerY - semiMinorAxis; y <= centerY + semiMinorAxis; y++) {
                ellipsePoints.add(new int[] {centerX, y});
            }
            return ellipsePoints;
        }

        if (semiMinorAxis == 0) {
            // Semi-minor axis is zero, create a horizontal line
            for (int x = centerX - semiMajorAxis; x <= centerX + semiMajorAxis; x++) {
                ellipsePoints.add(new int[] {x, centerY});
            }
            return ellipsePoints;
        }

        // Normal case: Non-degenerate ellipse
        computeEllipsePoints(ellipsePoints, centerX, centerY, semiMajorAxis, semiMinorAxis);

        return ellipsePoints;
    }

    /**
     * Computes points of a non-degenerate ellipse using the Midpoint Ellipse Algorithm.
     *
     * @param ellipsePoints the collection to which points will be added
     * @param centerX       the x-coordinate of the center of the ellipse
     * @param centerY       the y-coordinate of the center of the ellipse
     * @param semiMajorAxis the length of the semi-major axis (horizontal radius)
     * @param semiMinorAxis the length of the semi-minor axis (vertical radius)
     */
    private static void computeEllipsePoints(
            Collection<int[]> ellipsePoints,
            int centerX,
            int centerY,
            int semiMajorAxis,
            int semiMinorAxis
    ) {
        int x = 0; // Initial x-coordinate
        int y = semiMinorAxis; // Initial y-coordinate

        int semiMinorAxisSquared = semiMinorAxis * semiMinorAxis;
        int semiMajorAxisSquared = semiMajorAxis * semiMajorAxis;

        // Region 1: Initial decision parameter
        double region1DecisionParameter =
                semiMinorAxisSquared
                        - (semiMajorAxisSquared * semiMinorAxis)
                        + (0.25 * semiMajorAxisSquared);

        double deltaX = 2.0 * semiMinorAxisSquared * x;
        double deltaY = 2.0 * semiMajorAxisSquared * y;

        // Region 1: When the slope is less than 1
        while (deltaX < deltaY) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, x, y);

            if (region1DecisionParameter < 0) {
                x++;
                deltaX += 2 * semiMinorAxisSquared;
                region1DecisionParameter += deltaX + semiMinorAxisSquared;
            } else {
                x++;
                y--;
                deltaX += 2 * semiMinorAxisSquared;
                deltaY -= 2 * semiMajorAxisSquared;
                region1DecisionParameter += deltaX - deltaY + semiMinorAxisSquared;
            }
        }

        // Region 2: Initial decision parameter for the second region
        double region2DecisionParameter =
                semiMinorAxisSquared * (x + 0.5) * (x + 0.5)
                        + semiMajorAxisSquared * (y - 1) * (y - 1)
                        - semiMajorAxisSquared * semiMinorAxisSquared;

        // Region 2: When the slope is greater than or equal to 1
        while (y >= 0) {
            addSymmetricEllipsePoints(ellipsePoints, centerX, centerY, x, y);

            if (region2DecisionParameter > 0) {
                y--;
                deltaY -= 2 * semiMajorAxisSquared;
                region2DecisionParameter += semiMajorAxisSquared - deltaY;
            } else {
                y--;
                x++;
                deltaX += 2 * semiMinorAxisSquared;
                deltaY -= 2 * semiMajorAxisSquared;
                region2DecisionParameter += deltaX - deltaY + semiMajorAxisSquared;
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