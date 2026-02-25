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
    private KochSnowflake() {
    }

    public static void main(String[] args) {
        ArrayList<Vector2> vectors = new ArrayList<Vector2>();
        vectors.add(new Vector2(0, 0));
        vectors.add(new Vector2(1, 0));
        ArrayList<Vector2> result = iterate(vectors, 1);

        assert result.get(0).x == 0;
        assert result.get(0).y == 0;

        assert result.get(1).x == 1. / 3;
        assert result.get(1).y == 0;

        assert result.get(2).x == 1. / 2;
        assert result.get(2).y == Math.sin(Math.PI / 3) / 3;

        assert result.get(3).x == 2. / 3;
        assert result.get(3).y == 0;

        assert result.get(4).x == 1;
        assert result.get(4).y == 0;

        int imageWidth = 600;
        double offsetX = imageWidth / 10.;
        double offsetY = imageWidth / 3.7;
        BufferedImage image = getKochSnowflake(imageWidth, 5);

        assert image.getRGB(0, 0) == new Color(255, 255, 255).getRGB();

        assert image.getRGB((int) offsetX, (int) offsetY) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(image, "png", new File("KochSnowflake.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Vector2> iterate(ArrayList<Vector2> initialVectors, int steps) {
        ArrayList<Vector2> vectors = initialVectors;
        for (int i = 0; i < steps; i++) {
            vectors = iterationStep(vectors);
        }

        return vectors;
    }


    public static BufferedImage getKochSnowflake(int imageWidth, int steps) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        double offsetX = imageWidth / 10.;
        double offsetY = imageWidth / 3.7;
        Vector2 vector1 = new Vector2(offsetX, offsetY);
        Vector2 vector2 = new Vector2(imageWidth / 2.0, Math.sin(Math.PI / 3.0) * imageWidth * 0.8 + offsetY);
        Vector2 vector3 = new Vector2(imageWidth - offsetX, offsetY);
        ArrayList<Vector2> initialVectors = new ArrayList<Vector2>();
        initialVectors.add(vector1);
        initialVectors.add(vector2);
        initialVectors.add(vector3);
        initialVectors.add(vector1);
        ArrayList<Vector2> vectors = iterate(initialVectors, steps);
        return getImage(vectors, imageWidth, imageWidth);
    }


    private static ArrayList<Vector2> iterationStep(List<Vector2> vectors) {
        ArrayList<Vector2> newVectors = new ArrayList<Vector2>();
        for (int i = 0; i < vectors.size() - 1; i++) {
            Vector2 startVector = vectors.get(i);
            Vector2 endVector = vectors.get(i + 1);
            newVectors.add(startVector);
            Vector2 differenceVector = endVector.subtract(startVector).multiply(1. / 3);
            newVectors.add(startVector.add(differenceVector));
            newVectors.add(startVector.add(differenceVector).add(differenceVector.rotate(60)));
            newVectors.add(startVector.add(differenceVector.multiply(2)));
        }

        newVectors.add(vectors.get(vectors.size() - 1));
        return newVectors;
    }


    private static BufferedImage getImage(ArrayList<Vector2> vectors, int imageWidth, int imageHeight) {
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, imageHeight);

        g2d.setColor(Color.BLACK);
        BasicStroke bs = new BasicStroke(1);
        g2d.setStroke(bs);
        for (int i = 0; i < vectors.size() - 1; i++) {
            int x1 = (int) vectors.get(i).x;
            int y1 = (int) vectors.get(i).y;
            int x2 = (int) vectors.get(i + 1).x;
            int y2 = (int) vectors.get(i + 1).y;

            g2d.drawLine(x1, y1, x2, y2);
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


        public Vector2 add(Vector2 vector) {
            double x = this.x + vector.x;
            double y = this.y + vector.y;
            return new Vector2(x, y);
        }


        public Vector2 subtract(Vector2 vector) {
            double x = this.x - vector.x;
            double y = this.y - vector.y;
            return new Vector2(x, y);
        }


        public Vector2 multiply(double scalar) {
            double x = this.x * scalar;
            double y = this.y * scalar;
            return new Vector2(x, y);
        }


        public Vector2 rotate(double angleInDegrees) {
            double radians = angleInDegrees * Math.PI / 180;
            double ca = Math.cos(radians);
            double sa = Math.sin(radians);
            double x = ca * this.x - sa * this.y;
            double y = sa * this.x + ca * this.y;
            return new Vector2(x, y);
        }
    }
}
