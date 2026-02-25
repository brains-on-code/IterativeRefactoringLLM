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

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color LINE_COLOR = Color.BLACK;
    private static final int LINE_THICKNESS = 1;

    private static final double ROTATION_ANGLE_DEGREES = 60.0;
    private static final double ONE_THIRD = 1.0 / 3.0;
    private static final double TWO_THIRDS = 2.0 / 3.0;

    private KochSnowflake() {
        // Utility class
    }

    public static void main(String[] args) {
        List<Vector2> baseSegment = List.of(new Vector2(0, 0), new Vector2(1, 0));

        List<Vector2> result = iterate(baseSegment, 1);

        assert result.get(0).x == 0;
        assert result.get(0).y == 0;

        assert result.get(1).x == ONE_THIRD;
        assert result.get(1).y == 0;

        assert result.get(2).x == 0.5;
        assert result.get(2).y == Math.sin(Math.PI / 3) * ONE_THIRD;

        assert result.get(3).x == TWO_THIRDS;
        assert result.get(3).y == 0;

        assert result.get(4).x == 1;
        assert result.get(4).y == 0;

        int imageWidth = 600;
        double offsetX = imageWidth / 10.0;
        double offsetY = imageWidth / 3.7;

        BufferedImage image = getKochSnowflake(imageWidth, 5);

        assert image.getRGB(0, 0) == BACKGROUND_COLOR.getRGB();
        assert image.getRGB((int) offsetX, (int) offsetY) == LINE_COLOR.getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Vector2> iterate(List<Vector2> initialVectors, int steps) {
        List<Vector2> vectors = new ArrayList<>(initialVectors);
        for (int i = 0; i < steps; i++) {
            vectors = iterationStep(vectors);
        }
        return vectors;
    }

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

        List<Vector2> initialVectors = List.of(v1, v2, v3, v1);

        List<Vector2> vectors = iterate(initialVectors, steps);
        return createImage(vectors, imageWidth, imageWidth);
    }

    private static List<Vector2> iterationStep(List<Vector2> vectors) {
        List<Vector2> newVectors = new ArrayList<>();

        for (int i = 0; i < vectors.size() - 1; i++) {
            Vector2 start = vectors.get(i);
            Vector2 end = vectors.get(i + 1);

            newVectors.add(start);

            Vector2 segment = end.subtract(start).multiply(ONE_THIRD);
            Vector2 firstThird = start.add(segment);
            Vector2 secondThird = start.add(segment.multiply(2.0));
            Vector2 peak = firstThird.add(segment.rotate(ROTATION_ANGLE_DEGREES));

            newVectors.add(firstThird);
            newVectors.add(peak);
            newVectors.add(secondThird);
        }

        newVectors.add(vectors.get(vectors.size() - 1));
        return newVectors;
    }

    private static BufferedImage createImage(List<Vector2> vectors, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        try {
            graphics.setBackground(BACKGROUND_COLOR);
            graphics.clearRect(0, 0, imageWidth, imageHeight);

            graphics.setColor(LINE_COLOR);
            graphics.setStroke(new BasicStroke(LINE_THICKNESS));

            for (int i = 0; i < vectors.size() - 1; i++) {
                Vector2 start = vectors.get(i);
                Vector2 end = vectors.get(i + 1);

                int x1 = (int) start.x;
                int y1 = (int) start.y;
                int x2 = (int) end.x;
                int y2 = (int) end.y;

                graphics.drawLine(x1, y1, x2, y2);
            }
        } finally {
            graphics.dispose();
        }

        return image;
    }

    private static class Vector2 {

        final double x;
        final double y;

        Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%f, %f]", x, y);
        }

        Vector2 add(Vector2 vector) {
            return new Vector2(this.x + vector.x, this.y + vector.y);
        }

        Vector2 subtract(Vector2 vector) {
            return new Vector2(this.x - vector.x, this.y - vector.y);
        }

        Vector2 multiply(double scalar) {
            return new Vector2(this.x * scalar, this.y * scalar);
        }

        Vector2 rotate(double angleInDegrees) {
            double radians = Math.toRadians(angleInDegrees);
            double cos = Math.cos(radians);
            double sin = Math.sin(radians);
            double newX = cos * this.x - sin * this.y;
            double newY = sin * this.x + cos * this.y;
            return new Vector2(newX, newY);
        }
    }
}