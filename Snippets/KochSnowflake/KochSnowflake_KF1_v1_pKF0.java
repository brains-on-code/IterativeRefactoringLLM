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

public final class Class1 {

    private Class1() {
    }

    public static void method1(String[] args) {
        List<Point> basePoints = new ArrayList<>();
        basePoints.add(new Point(0, 0));
        basePoints.add(new Point(1, 0));

        List<Point> kochPoints = generateKochPoints(basePoints, 1);

        assert kochPoints.get(0).x == 0;
        assert kochPoints.get(0).y == 0;

        assert kochPoints.get(1).x == 1.0 / 3;
        assert kochPoints.get(1).y == 0;

        assert kochPoints.get(2).x == 1.0 / 2;
        assert kochPoints.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert kochPoints.get(3).x == 2.0 / 3;
        assert kochPoints.get(3).y == 0;

        assert kochPoints.get(4).x == 1;
        assert kochPoints.get(4).y == 0;

        int imageWidth = 600;
        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;

        BufferedImage image = createKochSnowflakeImage(imageWidth, 5);

        assert image.getRGB(0, 0) == Color.WHITE.getRGB();
        assert image.getRGB((int) margin, (int) baseY) == Color.BLACK.getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Point> generateKochPoints(List<Point> initialPoints, int iterations) {
        List<Point> currentPoints = initialPoints;
        for (int i = 0; i < iterations; i++) {
            currentPoints = subdivideKoch(currentPoints);
        }
        return new ArrayList<>(currentPoints);
    }

    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;

        Point left = new Point(margin, baseY);
        Point top = new Point(imageWidth / 2.0, Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + baseY);
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
            Point oneThird = segment.scale(1.0 / 3);
            Point twoThirds = segment.scale(2.0 / 3);

            Point point1 = start.add(oneThird);
            Point peak = point1.add(oneThird.rotate(60));
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

        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(1));

        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = (int) points.get(i).x;
            int y1 = (int) points.get(i).y;
            int x2 = (int) points.get(i + 1).x;
            int y2 = (int) points.get(i + 1).y;

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

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        public Point add(Point other) {
            return new Point(this.x + other.x, this.y + other.y);
        }

        public Point subtract(Point other) {
            return new Point(this.x - other.x, this.y - other.y);
        }

        public Point scale(double factor) {
            return new Point(this.x * factor, this.y * factor);
        }

        public Point rotate(double angleDegrees) {
            double radians = angleDegrees * Math.PI / 180;
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Point(newX, newY);
        }
    }
}