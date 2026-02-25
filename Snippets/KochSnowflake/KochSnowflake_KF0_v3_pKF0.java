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
 * previous stage, making smaller equilateral triangles.
 *
 * Steps for each line:
 * 1. Divide the line segment into three segments of equal length.
 * 2. Draw an equilateral triangle that has the middle segment from step 1 as
 *    its base and points outward.
 * 3. Remove the line segment that is the base of the triangle from step 2.
 *
 * (description adapted from https://en.wikipedia.org/wiki/Koch_snowflake )
 * (for a more detailed explanation and an implementation in the Processing
 * language, see https://natureofcode.com/book/chapter-8-fractals/
 * #84-the-koch-curve-and-the-arraylist-technique ).
 */
public final class KochSnowflake {

    private static final double ROTATION_ANGLE_DEGREES = 60.0;
    private static final double ONE_THIRD = 1.0 / 3.0;
    private static final double TWO_THIRDS = 2.0 / 3.0;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color SNOWFLAKE_COLOR = Color.BLACK;
    private static final int STROKE_WIDTH = 1;

    private KochSnowflake() {
        // Utility class
    }

    public static void main(String[] args) {
        testIterate();
        testGetKochSnowflake();
    }

    private static void testIterate() {
        List<Vector2> initial = new ArrayList<>();
        initial.add(new Vector2(0, 0));
        initial.add(new Vector2(1, 0));

        List<Vector2> result = iterate(initial, 1);

        assert result.get(0).x == 0;
        assert result.get(0).y == 0;

        assert result.get(1).x == ONE_THIRD;
        assert result.get(1).y == 0;

        assert result.get(2).x == 0.5;
        assert result.get(2).y == Math.sin(Math.PI / 3) * ONE_THIRD;

        assert result.get(3).x == TWO_THIRDS;
        assert result.get(3).y == 0;

        assert result.get(4).x == 1;
        assert result.get(4).y == 0;
    }

    private static void testGetKochSnowflake() {
        int imageWidth = 600;
        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        BufferedImage image = getKochSnowflake(imageWidth, 5);

        // The background should be white
        assert image.getRGB(0, 0) == BACKGROUND_COLOR.getRGB();

        // The snowflake is drawn in black and this is the position of the first vector
        assert image.getRGB((int) offsetX, (int) offsetY) == SNOWFLAKE_COLOR.getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go through the number of iterations determined by the argument "steps".
     * Be careful with high values (above 5) since the time to calculate
     * increases exponentially.
     *
     * @param initialVectors The vectors composing the shape to which the
     *                       algorithm is applied.
     * @param steps          The number of iterations.
     * @return The transformed vectors after the iteration-steps.
     */
    public static List<Vector2> iterate(List<Vector2> initialVectors, int steps) {
        List<Vector2> vectors = new ArrayList<>(initialVectors);
        for (int i = 0; i < steps; i++) {
            vectors = iterationStep(vectors);
        }
        return vectors;
    }

    /**
     * Method to render the Koch snowflake to an image.
     *
     * @param imageWidth The width of the rendered image.
     * @param steps      The number of iterations.
     * @return The image of the rendered Koch snowflake.
     */
    public static BufferedImage getKochSnowflake(int imageWidth, int steps) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        Vector2 v1 = new Vector2(offsetX, offsetY);
        Vector2 v2 = new Vector2(
            imageWidth / 2.0,
            Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + offsetY
        );
        Vector2 v3 = new Vector2(imageWidth - offsetX, offsetY);

        List<Vector2> initialVectors = new ArrayList<>();
        initialVectors.add(v1);
        initialVectors.add(v2);
        initialVectors.add(v3);
        initialVectors.add(v1);

        List<Vector2> vectors = iterate(initialVectors, steps);
        return renderImage(vectors, imageWidth, imageWidth);
    }

    /**
     * Loops through each pair of adjacent vectors. Each line between two
     * adjacent vectors is divided into 4 segments by adding 3 additional
     * vectors in-between the original two vectors. The vector in the middle is
     * constructed through a 60 degree rotation so it is bent outwards.
     *
     * @param vectors The vectors composing the shape to which the algorithm is
     *                applied.
     * @return The transformed vectors after the iteration-step.
     */
    private static List<Vector2> iterationStep(List<Vector2> vectors) {
        List<Vector2> newVectors = new ArrayList<>();

        int lastIndex = vectors.size() - 1;
        for (int i = 0; i < lastIndex; i++) {
            Vector2 start = vectors.get(i);
            Vector2 end = vectors.get(i + 1);

            newVectors.add(start);

            Vector2 segment = end.subtract(start).multiply(ONE_THIRD);
            Vector2 firstThird = start.add(segment);
            Vector2 secondThird = start.add(segment.multiply(2.0));
            Vector2 peak = firstThird.add(segment.rotate(ROTATION_ANGLE_DEGREES));

            newVectors.add(firstThird);
            newVectors.add(peak);
            newVectors.add(secondThird);
        }

        newVectors.add(vectors.get(lastIndex));
        return newVectors;
    }

    /**
     * Utility-method to render the Koch snowflake to an image.
     *
     * @param vectors     The vectors defining the edges to be rendered.
     * @param imageWidth  The width of the rendered image.
     * @param imageHeight The height of the rendered image.
     * @return The image of the rendered edges.
     */
    private static BufferedImage renderImage(List<Vector2> vectors, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        try {
            // Set the background white
            graphics.setBackground(BACKGROUND_COLOR);
            graphics.clearRect(0, 0, imageWidth, imageHeight);

            // Draw the edges
            graphics.setColor(SNOWFLAKE_COLOR);
            graphics.setStroke(new BasicStroke(STROKE_WIDTH));

            int lastIndex = vectors.size() - 1;
            for (int i = 0; i < lastIndex; i++) {
                Vector2 start = vectors.get(i);
                Vector2 end = vectors.get(i + 1);

                int x1 = (int) start.x;
                int y1 = (int) start.y;
                int x2 = (int) end.x;
                int y2 = (int) end.y;

                graphics.drawLine(x1, y1, x2, y2);
            }
        } finally {
            graphics.dispose();
        }

        return image;
    }

    /**
     * Inner class to handle the vector calculations.
     */
    private static class Vector2 {

        final double x;
        final double y;

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
         * @param vector The vector to be added.
         * @return The sum-vector.
         */
        public Vector2 add(Vector2 vector) {
            return new Vector2(this.x + vector.x, this.y + vector.y);
        }

        /**
         * Vector subtraction.
         *
         * @param vector The vector to be subtracted.
         * @return The difference-vector.
         */
        public Vector2 subtract(Vector2 vector) {
            return new Vector2(this.x - vector.x, this.y - vector.y);
        }

        /**
         * Vector scalar multiplication.
         *
         * @param scalar The factor by which to multiply the vector.
         * @return The scaled vector.
         */
        public Vector2 multiply(double scalar) {
            return new Vector2(this.x * scalar, this.y * scalar);
        }

        /**
         * Vector rotation (see https://en.wikipedia.org/wiki/Rotation_matrix).
         *
         * @param angleInDegrees The angle by which to rotate the vector.
         * @return The rotated vector.
         */
        public Vector2 rotate(double angleInDegrees) {
            double radians = Math.toRadians(angleInDegrees);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Vector2(newX, newY);
        }
    }
}