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

public final class KochSnowflake {

    private KochSnowflake() {}

    public static void main(String[] args) {
        ArrayList<Vector2> baseSegment = new ArrayList<>();
        baseSegment.add(new Vector2(0, 0));
        baseSegment.add(new Vector2(1, 0));
        ArrayList<Vector2> firstIteration = iterate(baseSegment, 1);

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

        int imageWidth = 600;
        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;
        BufferedImage snowflakeImage = createKochSnowflakeImage(imageWidth, 5);

        assert snowflakeImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert snowflakeImage.getRGB((int) offsetX, (int) offsetY) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(snowflakeImage, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Vector2> iterate(ArrayList<Vector2> initialPoints, int iterations) {
        ArrayList<Vector2> currentPoints = initialPoints;
        for (int i = 0; i < iterations; i++) {
            currentPoints = performIterationStep(currentPoints);
        }
        return currentPoints;
    }

    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        Vector2 vertexA = new Vector2(offsetX, offsetY);
        Vector2 vertexB = new Vector2(
            imageWidth / 2.0,
            Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + offsetY
        );
        Vector2 vertexC = new Vector2(imageWidth - offsetX, offsetY);

        ArrayList<Vector2> initialPolygon = new ArrayList<>();
        initialPolygon.add(vertexA);
        initialPolygon.add(vertexB);
        initialPolygon.add(vertexC);
        initialPolygon.add(vertexA);

        ArrayList<Vector2> snowflakePoints = iterate(initialPolygon, iterations);
        return renderPolylineToImage(snowflakePoints, imageWidth, imageWidth);
    }

    private static ArrayList<Vector2> performIterationStep(List<Vector2> points) {
        ArrayList<Vector2> refinedPoints = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            Vector2 segmentStart = points.get(i);
            Vector2 segmentEnd = points.get(i + 1);

            refinedPoints.add(segmentStart);

            Vector2 segmentThird = segmentEnd.subtract(segmentStart).multiply(1.0 / 3);
            Vector2 firstThirdPoint = segmentStart.add(segmentThird);
            Vector2 secondThirdPoint = segmentStart.add(segmentThird.multiply(2));
            Vector2 peakPoint = firstThirdPoint.add(segmentThird.rotate(60));

            refinedPoints.add(firstThirdPoint);
            refinedPoints.add(peakPoint);
            refinedPoints.add(secondThirdPoint);
        }

        refinedPoints.add(points.get(points.size() - 1));
        return refinedPoints;
    }

    private static BufferedImage renderPolylineToImage(ArrayList<Vector2> points, int imageWidth, int imageHeight) {
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

        public Vector2 add(Vector2 other) {
            double newX = this.x + other.x;
            double newY = this.y + other.y;
            return new Vector2(newX, newY);
        }

        public Vector2 subtract(Vector2 other) {
            double newX = this.x - other.x;
            double newY = this.y - other.y;
            return new Vector2(newX, newY);
        }

        public Vector2 multiply(double scalar) {
            double newX = this.x * scalar;
            double newY = this.y * scalar;
            return new Vector2(newX, newY);
        }

        public Vector2 rotate(double angleInDegrees) {
            double radians = angleInDegrees * Math.PI / 180;
            double cosAngle = Math.cos(radians);
            double sinAngle = Math.sin(radians);
            double newX = cosAngle * this.x - sinAngle * this.y;
            double newY = sinAngle * this.x + cosAngle * this.y;
            return new Vector2(newX, newY);
        }
    }
}