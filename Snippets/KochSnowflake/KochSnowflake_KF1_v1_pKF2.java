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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    public static void method1(String[] args) {
        // Basic test of the Koch subdivision logic.
        ArrayList<Class2> baseSegment = new ArrayList<>();
        baseSegment.add(new Class2(0, 0));
        baseSegment.add(new Class2(1, 0));

        ArrayList<Class2> subdivided = method2(baseSegment, 1);

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

        BufferedImage image = method3(imageWidth, 5);

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
     * @param points    initial list of points
     * @param iterations number of Koch iterations
     * @return resulting list of points after iterations
     */
    public static ArrayList<Class2> method2(ArrayList<Class2> points, int iterations) {
        ArrayList<Class2> current = points;
        for (int i = 0; i < iterations; i++) {
            current = method4(current);
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
    public static BufferedImage method3(int imageWidth, int iterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;

        Class2 a = new Class2(margin, baseY);
        Class2 b = new Class2(imageWidth / 2.0, Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + baseY);
        Class2 c = new Class2(imageWidth - margin, baseY);

        ArrayList<Class2> triangle = new ArrayList<>();
        triangle.add(a);
        triangle.add(b);
        triangle.add(c);
        triangle.add(a); // close the triangle

        ArrayList<Class2> snowflakePoints = method2(triangle, iterations);
        return method5(snowflakePoints, imageWidth, imageWidth);
    }

    /**
     * Performs one Koch subdivision step on a polyline.
     *
     * @param points input polyline points
     * @return subdivided polyline points
     */
    private static ArrayList<Class2> method4(List<Class2> points) {
        ArrayList<Class2> result = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            Class2 p1 = points.get(i);
            Class2 p2 = points.get(i + 1);

            result.add(p1);

            Class2 segment = p2.subtract(p1).scale(1.0 / 3);
            Class2 pA = p1.add(segment);
            Class2 pB = p1.add(segment).add(segment.rotate(60));
            Class2 pC = p1.add(segment.scale(2));

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
    private static BufferedImage method5(ArrayList<Class2> points, int width, int height) {
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
    private static class Class2 {

        double x;
        double y;

        Class2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        public Class2 add(Class2 other) {
            return new Class2(this.x + other.x, this.y + other.y);
        }

        public Class2 subtract(Class2 other) {
            return new Class2(this.x - other.x, this.y - other.y);
        }

        public Class2 scale(double factor) {
            return new Class2(this.x * factor, this.y * factor);
        }

        public Class2 rotate(double angleDegrees) {
            double radians = angleDegrees * Math.PI / 180;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Class2(newX, newY);
        }
    }
}