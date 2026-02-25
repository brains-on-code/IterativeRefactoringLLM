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

    private KochSnowflakeGenerator() {}

    public static void main(String[] args) {
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
        for (int iteration = 0; iteration < iterations; iteration++) {
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

        Point leftVertex = new Point(margin, baseY);
        Point topVertex = new Point(imageWidth / 2.0, Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + baseY);
        Point rightVertex = new Point(imageWidth - margin, baseY);

        ArrayList<Point> triangleVertices = new ArrayList<>();
        triangleVertices.add(leftVertex);
        triangleVertices.add(topVertex);
        triangleVertices.add(rightVertex);
        triangleVertices.add(leftVertex);

        ArrayList<Point> kochPoints = generateKochPoints(triangleVertices, iterations);
        return drawPolyline(kochPoints, imageWidth, imageWidth);
    }

    private static ArrayList<Point> subdivideKoch(List<Point> polylinePoints) {
        ArrayList<Point> subdividedPoints = new ArrayList<>();
        for (int index = 0; index < polylinePoints.size() - 1; index++) {
            Point startPoint = polylinePoints.get(index);
            Point endPoint = polylinePoints.get(index + 1);

            subdividedPoints.add(startPoint);

            Point segmentVector = endPoint.subtract(startPoint).scale(1.0 / 3);
            Point oneThirdPoint = startPoint.add(segmentVector);
            Point peakPoint = startPoint.add(segmentVector).add(segmentVector.rotate(60));
            Point twoThirdsPoint = startPoint.add(segmentVector.scale(2));

            subdividedPoints.add(oneThirdPoint);
            subdividedPoints.add(peakPoint);
            subdividedPoints.add(twoThirdsPoint);
        }

        subdividedPoints.add(polylinePoints.get(polylinePoints.size() - 1));
        return subdividedPoints;
    }

    private static BufferedImage drawPolyline(ArrayList<Point> polylinePoints, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(1));

        for (int index = 0; index < polylinePoints.size() - 1; index++) {
            int x1 = (int) polylinePoints.get(index).x;
            int y1 = (int) polylinePoints.get(index).y;
            int x2 = (int) polylinePoints.get(index + 1).x;
            int y2 = (int) polylinePoints.get(index + 1).y;

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
            double cosAngle = Math.cos(angleRadians);
            double sinAngle = Math.sin(angleRadians);
            double newX = cosAngle * this.x - sinAngle * this.y;
            double newY = sinAngle * this.x + cosAngle * this.y;
            return new Point(newX, newY);
        }
    }
}