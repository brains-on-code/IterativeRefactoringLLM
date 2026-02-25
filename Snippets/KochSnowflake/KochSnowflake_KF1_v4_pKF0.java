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

public final class KochSnowflakeGenerator {

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color LINE_COLOR = Color.BLACK;
    private static final int LINE_THICKNESS = 1;

    private static final String OUTPUT_FILE_NAME = "KochSnowflake.png";
    private static final String OUTPUT_FORMAT = "png";

    private static final double DEFAULT_MARGIN_RATIO = 0.1;
    private static final double DEFAULT_BASE_Y_DIVISOR = 3.7;
    private static final double TRIANGLE_HEIGHT_FACTOR = 0.8;
    private static final double KOCH_ONE_THIRD = 1.0 / 3.0;
    private static final double KOCH_TWO_THIRDS = 2.0 / 3.0;
    private static final double EQUILATERAL_ANGLE_DEGREES = 60.0;

    private KochSnowflakeGenerator() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        List<Point> baseSegment = List.of(new Point(0, 0), new Point(1, 0));

        List<Point> kochPoints = generateKochPoints(baseSegment, 1);
        validateKochSegment(kochPoints);

        int imageWidth = 600;
        int iterations = 5;

        BufferedImage image = createKochSnowflakeImage(imageWidth, iterations);

        double margin = imageWidth * DEFAULT_MARGIN_RATIO;
        double baseY = imageWidth / DEFAULT_BASE_Y_DIVISOR;

        assert image.getRGB(0, 0) == BACKGROUND_COLOR.getRGB();
        assert image.getRGB((int) margin, (int) baseY) == LINE_COLOR.getRGB();

        try {
            ImageIO.write(image, OUTPUT_FORMAT, new File(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validateKochSegment(List<Point> kochPoints) {
        assert kochPoints.get(0).x == 0;
        assert kochPoints.get(0).y == 0;

        assert kochPoints.get(1).x == KOCH_ONE_THIRD;
        assert kochPoints.get(1).y == 0;

        assert kochPoints.get(2).x == 0.5;
        assert kochPoints.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert kochPoints.get(3).x == KOCH_TWO_THIRDS;
        assert kochPoints.get(3).y == 0;

        assert kochPoints.get(4).x == 1;
        assert kochPoints.get(4).y == 0;
    }

    public static ArrayList<Point> generateKochPoints(List<Point> initialPoints, int iterations) {
        List<Point> currentPoints = new ArrayList<>(initialPoints);
        for (int i = 0; i < iterations; i++) {
            currentPoints = subdivideKoch(currentPoints);
        }
        return new ArrayList<>(currentPoints);
    }

    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double margin = imageWidth * DEFAULT_MARGIN_RATIO;
        double baseY = imageWidth / DEFAULT_BASE_Y_DIVISOR;

        Point left = new Point(margin, baseY);
        Point top = new Point(
            imageWidth / 2.0,
            Math.sin(Math.PI / 3.0) * imageWidth * TRIANGLE_HEIGHT_FACTOR + baseY
        );
        Point right = new Point(imageWidth - margin, baseY);

        List<Point> triangle = new ArrayList<>();
        triangle.add(left);
        triangle.add(top);
        triangle.add(right);
        triangle.add(left);

        ArrayList<Point> kochPoints = generateKochPoints(triangle, iterations);
        return drawPolyline(kochPoints, imageWidth, imageWidth);
    }

    private static ArrayList<Point> subdivideKoch(List<Point> points) {
        ArrayList<Point> result = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);

            result.add(start);

            Point segment = end.subtract(start);
            Point oneThird = segment.scale(KOCH_ONE_THIRD);
            Point twoThirds = segment.scale(KOCH_TWO_THIRDS);

            Point point1 = start.add(oneThird);
            Point peak = point1.add(oneThird.rotate(EQUILATERAL_ANGLE_DEGREES));
            Point point2 = start.add(twoThirds);

            result.add(point1);
            result.add(peak);
            result.add(point2);
        }

        result.add(points.get(points.size() - 1));
        return result;
    }

    private static BufferedImage drawPolyline(List<Point> points, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setBackground(BACKGROUND_COLOR);
        graphics.clearRect(0, 0, width, height);

        graphics.setColor(LINE_COLOR);
        graphics.setStroke(new BasicStroke(LINE_THICKNESS));

        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);

            int x1 = (int) start.x;
            int y1 = (int) start.y;
            int x2 = (int) end.x;
            int y2 = (int) end.y;

            graphics.drawLine(x1, y1, x2, y2);
        }

        graphics.dispose();
        return image;
    }

    private static class Point {

        final double x;
        final double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point add(Point other) {
            return new Point(this.x + other.x, this.y + other.y);
        }

        Point subtract(Point other) {
            return new Point(this.x - other.x, this.y - other.y);
        }

        Point scale(double factor) {
            return new Point(this.x * factor, this.y * factor);
        }

        Point rotate(double angleDegrees) {
            double radians = Math.toRadians(angleDegrees);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Point(newX, newY);
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }
    }
}