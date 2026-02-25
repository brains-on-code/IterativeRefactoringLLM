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
 * Generates and renders the Koch snowflake fractal.
 *
 * <p>The Koch snowflake is constructed iteratively:
 * <ol>
 *   <li>Start with an equilateral triangle.</li>
 *   <li>For each line segment:
 *     <ol>
 *       <li>Divide the segment into three equal parts.</li>
 *       <li>Construct an outward-pointing equilateral triangle on the middle segment.</li>
 *       <li>Remove the middle segment (the base of the new triangle).</li>
 *     </ol>
 *   </li>
 * </ol>
 *
 * <p>See:
 * <ul>
 *   <li>https://en.wikipedia.org/wiki/Koch_snowflake</li>
 *   <li>https://natureofcode.com/book/chapter-8-fractals/#84-the-koch-curve-and-the-arraylist-technique</li>
 * </ul>
 */
public final class KochSnowflake {

    private KochSnowflake() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        // Test iterate method on a single horizontal segment [0,0] -> [1,0]
        ArrayList<Vector2> vectors = new ArrayList<>();
        vectors.add(new Vector2(0, 0));
        vectors.add(new Vector2(1, 0));
        ArrayList<Vector2> result = iterate(vectors, 1);

        assert result.get(0).x == 0;
        assert result.get(0).y == 0;

        assert result.get(1).x == 1.0 / 3;
        assert result.get(1).y == 0;

        assert result.get(2).x == 1.0 / 2;
        assert result.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert result.get(3).x == 2.0 / 3;
        assert result.get(3).y == 0;

        assert result.get(4).x == 1;
        assert result.get(4).y == 0;

        // Test getKochSnowflake method
        int imageWidth = 600;
        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;
        BufferedImage image = getKochSnowflake(imageWidth, 5);

        // Background should be white
        assert image.getRGB(0, 0) == Color.WHITE.getRGB();

        // First vertex of the triangle should be black
        assert image.getRGB((int) offsetX, (int) offsetY) == Color.BLACK.getRGB();

        // Save image
        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies the Koch iteration a given number of times to an initial polyline.
     *
     * @param initialVectors the vertices of the initial polyline
     * @param steps          number of iterations to apply (values above 5 grow very fast)
     * @return the vertices after applying the given number of iterations
     */
    public static ArrayList<Vector2> iterate(ArrayList<Vector2> initialVectors, int steps) {
        ArrayList<Vector2> vectors = initialVectors;
        for (int i = 0; i < steps; i++) {
            vectors = iterationStep(vectors);
        }
        return vectors;
    }

    /**
     * Renders a Koch snowflake image.
     *
     * @param imageWidth width (and height) of the rendered image in pixels
     * @param steps      number of Koch iterations to apply
     * @return a {@link BufferedImage} containing the rendered snowflake
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

        ArrayList<Vector2> initialVectors = new ArrayList<>();
        initialVectors.add(v1);
        initialVectors.add(v2);
        initialVectors.add(v3);
        initialVectors.add(v1); // close the triangle

        ArrayList<Vector2> vectors = iterate(initialVectors, steps);
        return getImage(vectors, imageWidth, imageWidth);
    }

    /**
     * Performs a single Koch iteration step on a polyline.
     *
     * <p>For each segment between two consecutive vertices, the segment is
     * replaced by four segments by inserting three new vertices:
     * <ul>
     *   <li>Point at 1/3 of the segment</li>
     *   <li>Point forming a 60° outward "peak"</li>
     *   <li>Point at 2/3 of the segment</li>
     * </ul>
     *
     * @param vectors vertices of the current polyline
     * @return vertices of the polyline after one iteration
     */
    private static ArrayList<Vector2> iterationStep(List<Vector2> vectors) {
        ArrayList<Vector2> newVectors = new ArrayList<>();

        for (int i = 0; i < vectors.size() - 1; i++) {
            Vector2 start = vectors.get(i);
            Vector2 end = vectors.get(i + 1);

            newVectors.add(start);

            Vector2 segmentThird = end.subtract(start).multiply(1.0 / 3.0);
            Vector2 oneThird = start.add(segmentThird);
            Vector2 twoThirds = start.add(segmentThird.multiply(2.0));
            Vector2 peak = oneThird.add(segmentThird.rotate(60));

            newVectors.add(oneThird);
            newVectors.add(peak);
            newVectors.add(twoThirds);
        }

        // Add the last vertex to close the polyline
        newVectors.add(vectors.get(vectors.size() - 1));
        return newVectors;
    }

    /**
     * Renders a polyline as a black line on a white background.
     *
     * @param vectors     vertices of the polyline to render
     * @param imageWidth  width of the image in pixels
     * @param imageHeight height of the image in pixels
     * @return a {@link BufferedImage} containing the rendered polyline
     */
    private static BufferedImage getImage(ArrayList<Vector2> vectors, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // White background
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageHeight);

        // Black polyline
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        for (int i = 0; i < vectors.size() - 1; i++) {
            Vector2 v1 = vectors.get(i);
            Vector2 v2 = vectors.get(i + 1);

            int x1 = (int) v1.x;
            int y1 = (int) v1.y;
            int x2 = (int) v2.x;
            int y2 = (int) v2.y;

            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();
        return image;
    }

    /**
     * Simple 2D vector with basic operations.
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
         * Returns the sum of this vector and the given vector.
         *
         * @param vector vector to add
         * @return resulting vector
         */
        public Vector2 add(Vector2 vector) {
            return new Vector2(this.x + vector.x, this.y + vector.y);
        }

        /**
         * Returns the difference between this vector and the given vector.
         *
         * @param vector vector to subtract
         * @return resulting vector
         */
        public Vector2 subtract(Vector2 vector) {
            return new Vector2(this.x - vector.x, this.y - vector.y);
        }

        /**
         * Returns this vector scaled by the given factor.
         *
         * @param scalar scale factor
         * @return scaled vector
         */
        public Vector2 multiply(double scalar) {
            return new Vector2(this.x * scalar, this.y * scalar);
        }

        /**
         * Returns this vector rotated counterclockwise by the given angle.
         *
         * @param angleInDegrees rotation angle in degrees
         * @return rotated vector
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