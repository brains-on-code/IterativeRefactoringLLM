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
        ArrayList<Point> kochCurvePoints = generateKochCurvePoints(baseSegment, 1);

        assert kochCurvePoints.get(0).x == 0;
        assert kochCurvePoints.get(0).y == 0;

        assert kochCurvePoints.get(1).x == 1.0 / 3;
        assert kochCurvePoints.get(1).y == 0;

        assert kochCurvePoints.get(2).x == 1.0 / 2;
        assert kochCurvePoints.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert kochCurvePoints.get(3).x == 2.0 / 3;
        assert kochCurvePoints.get(3).y == 0;

        assert kochCurvePoints.get(4).x == 1;
        assert kochCurvePoints.get(4).y == 0;

        int imageWidth = 600;
        double margin = imageWidth / 10.0;
        double baseY = imageWidth / 3.7;
        BufferedImage snowflakeImage = createKochSnowflakeImage(imageWidth, 5);

        assert snowflakeImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert snowflakeImage.getRGB((int) margin, (int) baseY) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(snowflakeImage, "png", new File("KochSnowflake.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<Point> generateKochCurvePoints(ArrayList<Point> initialPolyline, int iterationCount) {
        ArrayList<Point> currentPolyline = initialPolyline;
        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            currentPolyline = subdivideKochSegment(currentPolyline);
        }
        return currentPolyline;
    }

    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterationCount) {
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

        ArrayList<Point> snowflakePolyline = generateKochCurvePoints(triangleVertices, iterationCount);
        return drawPolyline(snowflakePolyline, imageWidth, imageWidth);
    }

    private static ArrayList<Point> subdivideKochSegment(List<Point> polylinePoints) {
        ArrayList<Point> subdividedPolyline = new ArrayList<>();
        for (int pointIndex = 0; pointIndex < polylinePoints.size() - 1; pointIndex++) {
            Point segmentStart = polylinePoints.get(pointIndex);
            Point segmentEnd = polylinePoints.get(pointIndex + 1);

            subdividedPolyline.add(segmentStart);

            Point segmentVector = segmentEnd.subtract(segmentStart).scale(1.0 / 3);
            Point oneThirdPoint = segmentStart.add(segmentVector);
            Point peakPoint = segmentStart.add(segmentVector).add(segmentVector.rotate(60));
            Point twoThirdsPoint = segmentStart.add(segmentVector.scale(2));

            subdividedPolyline.add(oneThirdPoint);
            subdividedPolyline.add(peakPoint);
            subdividedPolyline.add(twoThirdsPoint);
        }

        subdividedPolyline.add(polylinePoints.get(polylinePoints.size() - 1));
        return subdividedPolyline;
    }

    private static BufferedImage drawPolyline(ArrayList<Point> polylinePoints, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphicsContext = image.createGraphics();

        graphicsContext.setBackground(Color.WHITE);
        graphicsContext.fillRect(0, 0, imageWidth, imageHeight);

        graphicsContext.setColor(Color.BLACK);
        graphicsContext.setStroke(new BasicStroke(1));

        for (int pointIndex = 0; pointIndex < polylinePoints.size() - 1; pointIndex++) {
            int startX = (int) polylinePoints.get(pointIndex).x;
            int startY = (int) polylinePoints.get(pointIndex).y;
            int endX = (int) polylinePoints.get(pointIndex + 1).x;
            int endY = (int) polylinePoints.get(pointIndex + 1).y;

            graphicsContext.drawLine(startX, startY, endX, endY);
        }

        return image;
    }

    private static class Point {

        double x;
        double y;

        Point(double xCoordinate, double yCoordinate) {
            this.x = xCoordinate;
            this.y = yCoordinate;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", this.x, this.y);
        }

        public Point add(Point otherPoint) {
            double newX = this.x + otherPoint.x;
            double newY = this.y + otherPoint.y;
            return new Point(newX, newY);
        }

        public Point subtract(Point otherPoint) {
            double newX = this.x - otherPoint.x;
            double newY = this.y - otherPoint.y;
            return new Point(newX, newY);
        }

        public Point scale(double scaleFactor) {
            double newX = this.x * scaleFactor;
            double newY = this.y * scaleFactor;
            return new Point(newX, newY);
        }

        public Point rotate(double angleDegrees) {
            double angleRadians = angleDegrees * Math.PI / 180;
            double cosine = Math.cos(angleRadians);
            double sine = Math.sin(angleRadians);
            double newX = cosine * this.x - sine * this.y;
            double newY = sine * this.x + cosine * this.y;
            return new Point(newX, newY);
        }
    }
}