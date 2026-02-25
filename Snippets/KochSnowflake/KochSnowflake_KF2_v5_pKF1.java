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
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ArrayList<Vector2> iterate(ArrayList<Vector2> initialPoints, int iterationCount) {
        ArrayList<Vector2> currentPoints = initialPoints;
        for (int iterationIndex = 0; iterationIndex < iterationCount; iterationIndex++) {
            currentPoints = performIterationStep(currentPoints);
        }
        return currentPoints;
    }

    public static BufferedImage createKochSnowflakeImage(int imageWidth, int iterationCount) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        Vector2 topVertex = new Vector2(offsetX, offsetY);
        Vector2 bottomVertex = new Vector2(
            imageWidth / 2.0,
            Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + offsetY
        );
        Vector2 rightVertex = new Vector2(imageWidth - offsetX, offsetY);

        ArrayList<Vector2> initialPolygon = new ArrayList<>();
        initialPolygon.add(topVertex);
        initialPolygon.add(bottomVertex);
        initialPolygon.add(rightVertex);
        initialPolygon.add(topVertex);

        ArrayList<Vector2> snowflakePoints = iterate(initialPolygon, iterationCount);
        return renderPolylineToImage(snowflakePoints, imageWidth, imageWidth);
    }

    private static ArrayList<Vector2> performIterationStep(List<Vector2> polylinePoints) {
        ArrayList<Vector2> refinedPoints = new ArrayList<>();

        for (int index = 0; index < polylinePoints.size() - 1; index++) {
            Vector2 segmentStart = polylinePoints.get(index);
            Vector2 segmentEnd = polylinePoints.get(index + 1);

            refinedPoints.add(segmentStart);

            Vector2 segmentThird = segmentEnd.subtract(segmentStart).multiply(1.0 / 3);
            Vector2 firstThirdPoint = segmentStart.add(segmentThird);
            Vector2 secondThirdPoint = segmentStart.add(segmentThird.multiply(2));
            Vector2 peakPoint = firstThirdPoint.add(segmentThird.rotate(60));

            refinedPoints.add(firstThirdPoint);
            refinedPoints.add(peakPoint);
            refinedPoints.add(secondThirdPoint);
        }

        refinedPoints.add(polylinePoints.get(polylinePoints.size() - 1));
        return refinedPoints;
    }

    private static BufferedImage renderPolylineToImage(ArrayList<Vector2> polylinePoints, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setBackground(Color.WHITE);
        graphics.fillRect(0, 0, imageWidth, imageHeight);

        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(1));

        for (int index = 0; index < polylinePoints.size() - 1; index++) {
            int startX = (int) polylinePoints.get(index).x;
            int startY = (int) polylinePoints.get(index).y;
            int endX = (int) polylinePoints.get(index + 1).x;
            int endY = (int) polylinePoints.get(index + 1).y;

            graphics.drawLine(startX, startY, endX, endY);
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
            double sumX = this.x + other.x;
            double sumY = this.y + other.y;
            return new Vector2(sumX, sumY);
        }

        public Vector2 subtract(Vector2 other) {
            double differenceX = this.x - other.x;
            double differenceY = this.y - other.y;
            return new Vector2(differenceX, differenceY);
        }

        public Vector2 multiply(double scalar) {
            double scaledX = this.x * scalar;
            double scaledY = this.y * scalar;
            return new Vector2(scaledX, scaledY);
        }

        public Vector2 rotate(double angleInDegrees) {
            double radians = Math.toRadians(angleInDegrees);
            double cosine = Math.cos(radians);
            double sine = Math.sin(radians);
            double rotatedX = cosine * this.x - sine * this.y;
            double rotatedY = sine * this.x + cosine * this.y;
            return new Vector2(rotatedX, rotatedY);
        }
    }
}