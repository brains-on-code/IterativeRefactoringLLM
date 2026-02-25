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
 * Utility class for generating and rendering the Koch snowflake fractal.
 */
public final class KochSnowflake {

    private KochSnowflake() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        // Basic test of the Koch subdivision logic.
        ArrayList<Point2D> baseSegment = new ArrayList<>();
        baseSegment.add(new Point2D(0, 0));
        baseSegment.add(new Point2D(1, 0));

        ArrayList<Point2D> subdivided = applyKochIterations(baseSegment, 1);

        assert subdivided.get(0).x == 0;
        assert subdivided.get(0).y == 0;

        assert subdivided.get(1).x == 1.0 / 3;
        assert subdivided.get(1).y == 0;

        assert subdivided.get(2).x == 1.0 / 2;
        assert subdivided.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert subdivided.get(3).x == 2.0 / 3;
        assert subdivided.get(3).y == 0;

        assert subdivided.get(4).x == 1;
        assert subdivided.get(4).y == 0;

        int imageWidth = 600;
        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;

        BufferedImage image = createKochSnowflakeImage(imageWidth, 5);

        // Background should be white.
        assert image.getRGB(0, 0) == Color.WHITE.getRGB();

        // A point on the base should be black (part of the snowflake).
        assert image.getRGB((int) margin, (int) baseY) == Color.BLACK.getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Applies the Koch subdivision a given number of iterations to an initial polyline.
     *
     * @param points     initial list of points
     * @param iterations number of Koch iterations
     * @return resulting list of points after iterations
     */
    public static ArrayList<Point2D> applyKochIterations(ArrayList<Point2D> points, int iterations) {
        ArrayList<Point2D> current = points;
        for (int i = 0; i < iterations; i++) {
            current = subdivideKoch(current);
        }
        return current;
    }

    /**
     * Creates an image of a Koch snowflake.
     *
     * @param imageWidth width (and height) of the image in pixels
     * @param iterations number of Koch iterations
     * @return rendered snowflake image
     */
    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;

        Point2D a = new Point2D(margin, baseY);
        Point2D b = new Point2D(imageWidth / 2.0, Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + baseY);
        Point2D c = new Point2D(imageWidth - margin, baseY);

        ArrayList<Point2D> triangle = new ArrayList<>();
        triangle.add(a);
        triangle.add(b);
        triangle.add(c);
        triangle.add(a); // Close the triangle.

        ArrayList<Point2D> snowflakePoints = applyKochIterations(triangle, iterations);
        return renderPolyline(snowflakePoints, imageWidth, imageWidth);
    }

    /**
     * Performs one Koch subdivision step on a polyline.
     *
     * @param points input polyline points
     * @return subdivided polyline points
     */
    private static ArrayList<Point2D> subdivideKoch(List<Point2D> points) {
        ArrayList<Point2D> result = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            Point2D p1 = points.get(i);
            Point2D p2 = points.get(i + 1);

            result.add(p1);

            Point2D segment = p2.subtract(p1).scale(1.0 / 3);
            Point2D pA = p1.add(segment);
            Point2D pB = p1.add(segment).add(segment.rotate(60));
            Point2D pC = p1.add(segment.scale(2));

            result.add(pA);
            result.add(pB);
            result.add(pC);
        }

        result.add(points.get(points.size() - 1));
        return result;
    }

    /**
     * Renders a polyline into a buffered image.
     *
     * @param points list of points to draw
     * @param width  image width
     * @param height image height
     * @return rendered image
     */
    private static BufferedImage renderPolyline(ArrayList<Point2D> points, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setBackground(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(1));

        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = (int) points.get(i).x;
            int y1 = (int) points.get(i).y;
            int x2 = (int) points.get(i + 1).x;
            int y2 = (int) points.get(i + 1).y;

            g.drawLine(x1, y1, x2, y2);
        }

        g.dispose();
        return image;
    }

    /**
     * Simple 2D point with basic vector operations.
     */
    private static class Point2D {

        double x;
        double y;

        Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        public Point2D add(Point2D other) {
            return new Point2D(this.x + other.x, this.y + other.y);
        }

        public Point2D subtract(Point2D other) {
            return new Point2D(this.x - other.x, this.y - other.y);
        }

        public Point2D scale(double factor) {
            return new Point2D(this.x * factor, this.y * factor);
        }

        public Point2D rotate(double angleDegrees) {
            double radians = angleDegrees * Math.PI / 180;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Point2D(newX, newY);
        }
    }
}