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
        ArrayList<Point> baseSegment = new ArrayList<>();
        baseSegment.add(new Point(0, 0));
        baseSegment.add(new Point(1, 0));
        ArrayList<Point> kochPoints = generateKochPoints(baseSegment, 1);

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

        assert image.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert image.getRGB((int) margin, (int) baseY) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Point> generateKochPoints(ArrayList<Point> initialPoints, int iterations) {
        ArrayList<Point> currentPoints = initialPoints;
        for (int i = 0; i < iterations; i++) {
            currentPoints = subdivideKoch(currentPoints);
        }
        return currentPoints;
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

        ArrayList<Point> triangle = new ArrayList<>();
        triangle.add(left);
        triangle.add(top);
        triangle.add(right);
        triangle.add(left);

        ArrayList<Point> kochPoints = generateKochPoints(triangle, iterations);
        return drawPolyline(kochPoints, imageWidth, imageWidth);
    }

    private static ArrayList<Point> subdivideKoch(List<Point> points) {
        ArrayList<Point> subdivided = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Point start = points.get(i);
            Point end = points.get(i + 1);

            subdivided.add(start);

            Point segment = end.subtract(start).scale(1.0 / 3);
            Point oneThird = start.add(segment);
            Point peak = start.add(segment).add(segment.rotate(60));
            Point twoThirds = start.add(segment.scale(2));

            subdivided.add(oneThird);
            subdivided.add(peak);
            subdivided.add(twoThirds);
        }

        subdivided.add(points.get(points.size() - 1));
        return subdivided;
    }

    private static BufferedImage drawPolyline(ArrayList<Point> points, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(1));

        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = (int) points.get(i).x;
            int y1 = (int) points.get(i).y;
            int x2 = (int) points.get(i + 1).x;
            int y2 = (int) points.get(i + 1).y;

            graphics.drawLine(x1, y1, x2, y2);
        }

        return image;
    }

    private static class Point {

        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        public Point add(Point other) {
            double newX = this.x + other.x;
            double newY = this.y + other.y;
            return new Point(newX, newY);
        }

        public Point subtract(Point other) {
            double newX = this.x - other.x;
            double newY = this.y - other.y;
            return new Point(newX, newY);
        }

        public Point scale(double factor) {
            double newX = this.x * factor;
            double newY = this.y * factor;
            return new Point(newX, newY);
        }

        public Point rotate(double angleDegrees) {
            double angleRadians = angleDegrees * Math.PI / 180;
            double cos = Math.cos(angleRadians);
            double sin = Math.sin(angleRadians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Point(newX, newY);
        }
    }
}