package com.thealgorithms.others;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * The Koch snowflake is a fractal curve and one of the earliest fractals to
 * have been described. The Koch snowflake can be built up iteratively, in a
 * sequence of stages. The first stage is an equilateral triangle, and each
 * successive stage is formed by adding outward bends to each side of the
 * previous stage, making smaller equilateral triangles. This can be achieved
 * through the following steps for each line: 1. divide the line segment into
 * three segments of equal length. 2. draw an equilateral triangle that has the
 * middle segment from step 1 as its base and points outward. 3. remove the line
 * segment that is the base of the triangle from step 2. (description adapted
 * from https://en.wikipedia.org/wiki/Koch_snowflake ) (for a more detailed
 * explanation and an implementation in the Processing language, see
 * https://natureofcode.com/book/chapter-8-fractals/
 * #84-the-koch-curve-and-the-arraylist-technique ).
 */
public final class KochSnowflake {

    private KochSnowflake() {}

    public static void main(String[] args) {
        // Test iterate method
        ArrayList<Vector2> initialSegment = new ArrayList<>();
        initialSegment.add(new Vector2(0, 0));
        initialSegment.add(new Vector2(1, 0));
        ArrayList<Vector2> firstIteration = iterate(initialSegment, 1);

        assert firstIteration.get(0).x == 0;
        assert firstIteration.get(0).y == 0;

        assert firstIteration.get(1).x == 1.0 / 3;
        assert firstIteration.get(1).y == 0;

        assert firstIteration.get(2).x == 1.0 / 2;
        assert firstIteration.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert firstIteration.get(3).x == 2.0 / 3;
        assert firstIteration.get(3).y == 0;

        assert firstIteration.get(4).x == 1;
        assert firstIteration.get(4).y == 0;

        // Test getKochSnowflake method
        int imageWidth = 600;
        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;
        BufferedImage snowflakeImage = getKochSnowflake(imageWidth, 5);

        // The background should be white
        assert snowflakeImage.getRGB(0, 0) == Color.WHITE.getRGB();

        // The snowflake is drawn in black and this is the position of the first vector
        assert snowflakeImage.getRGB((int) offsetX, (int) offsetY) == Color.BLACK.getRGB();

        // Save image
        try {
            ImageIO.write(snowflakeImage, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go through the number of iterations determined by the argument "steps".
     * Be careful with high values (above 5) since the time to calculate
     * increases exponentially.
     *
     * @param initialPoints The points composing the shape to which the
     * algorithm is applied.
     * @param iterationCount The number of iterations.
     * @return The transformed points after the iteration steps.
     */
    public static ArrayList<Vector2> iterate(ArrayList<Vector2> initialPoints, int iterationCount) {
        ArrayList<Vector2> currentPoints = initialPoints;
        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            currentPoints = performIterationStep(currentPoints);
        }
        return currentPoints;
    }

    /**
     * Method to render the Koch snowflake to an image.
     *
     * @param imageWidth The width of the rendered image.
     * @param iterationCount The number of iterations.
     * @return The image of the rendered Koch snowflake.
     */
    public static BufferedImage getKochSnowflake(int imageWidth, int iterationCount) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        Vector2 topVertex = new Vector2(offsetX, offsetY);
        Vector2 leftVertex = new Vector2(
            imageWidth / 2.0,
            Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + offsetY
        );
        Vector2 rightVertex = new Vector2(imageWidth - offsetX, offsetY);

        ArrayList<Vector2> triangleVertices = new ArrayList<>();
        triangleVertices.add(topVertex);
        triangleVertices.add(leftVertex);
        triangleVertices.add(rightVertex);
        triangleVertices.add(topVertex);

        ArrayList<Vector2> snowflakePoints = iterate(triangleVertices, iterationCount);
        return renderSnowflakeToImage(snowflakePoints, imageWidth, imageWidth);
    }

    /**
     * Loops through each pair of adjacent points. Each line between two
     * adjacent points is divided into 4 segments by adding 3 additional
     * points in-between the original two points. The point in the middle is
     * constructed through a 60 degree rotation so it is bent outwards.
     *
     * @param points The points composing the shape to which the algorithm is
     * applied.
     * @return The transformed points after the iteration step.
     */
    private static ArrayList<Vector2> performIterationStep(List<Vector2> points) {
        ArrayList<Vector2> refinedPoints = new ArrayList<>();

        for (int pointIndex = 0; pointIndex < points.size() - 1; pointIndex++) {
            Vector2 startPoint = points.get(pointIndex);
            Vector2 endPoint = points.get(pointIndex + 1);

            refinedPoints.add(startPoint);

            Vector2 segmentVector = endPoint.subtract(startPoint).multiply(1.0 / 3);
            Vector2 firstThirdPoint = startPoint.add(segmentVector);
            Vector2 secondThirdPoint = startPoint.add(segmentVector.multiply(2));
            Vector2 peakPoint = firstThirdPoint.add(segmentVector.rotate(60));

            refinedPoints.add(firstThirdPoint);
            refinedPoints.add(peakPoint);
            refinedPoints.add(secondThirdPoint);
        }

        refinedPoints.add(points.get(points.size() - 1));
        return refinedPoints;
    }

    /**
     * Utility method to render the Koch snowflake to an image.
     *
     * @param points The points defining the edges to be rendered.
     * @param imageWidth The width of the rendered image.
     * @param imageHeight The height of the rendered image.
     * @return The image of the rendered edges.
     */
    private static BufferedImage renderSnowflakeToImage(ArrayList<Vector2> points, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // Set the background white
        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);

        // Draw the edges
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(1));

        for (int pointIndex = 0; pointIndex < points.size() - 1; pointIndex++) {
            int startX = (int) points.get(pointIndex).x;
            int startY = (int) points.get(pointIndex).y;
            int endX = (int) points.get(pointIndex + 1).x;
            int endY = (int) points.get(pointIndex + 1).y;

            graphics.drawLine(startX, startY, endX, endY);
        }

        return image;
    }

    /**
     * Inner class to handle the vector calculations.
     */
    private static class Vector2 {

        double x;
        double y;

        Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        /**
         * Vector addition.
         *
         * @param other The vector to be added.
         * @return The sum vector.
         */
        public Vector2 add(Vector2 other) {
            double resultX = this.x + other.x;
            double resultY = this.y + other.y;
            return new Vector2(resultX, resultY);
        }

        /**
         * Vector subtraction.
         *
         * @param other The vector to be subtracted.
         * @return The difference vector.
         */
        public Vector2 subtract(Vector2 other) {
            double resultX = this.x - other.x;
            double resultY = this.y - other.y;
            return new Vector2(resultX, resultY);
        }

        /**
         * Vector scalar multiplication.
         *
         * @param scalar The factor by which to multiply the vector.
         * @return The scaled vector.
         */
        public Vector2 multiply(double scalar) {
            double resultX = this.x * scalar;
            double resultY = this.y * scalar;
            return new Vector2(resultX, resultY);
        }

        /**
         * Vector rotation (see https://en.wikipedia.org/wiki/Rotation_matrix).
         *
         * @param angleInDegrees The angle by which to rotate the vector.
         * @return The rotated vector.
         */
        public Vector2 rotate(double angleInDegrees) {
            double radians = angleInDegrees * Math.PI / 180;
            double cosine = Math.cos(radians);
            double sine = Math.sin(radians);
            double rotatedX = cosine * this.x - sine * this.y;
            double rotatedY = sine * this.x + cosine * this.y;
            return new Vector2(rotatedX, rotatedY);
        }
    }
}