package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Mandelbrot {

    private static final Color COLOR_WHITE = new Color(255, 255, 255);
    private static final Color COLOR_BLACK = new Color(0, 0, 0);
    private static final Color COLOR_RED = new Color(255, 0, 0);

    private Mandelbrot() {
        // Utility class
    }

    public static void main(String[] args) {
        final int width = 800;
        final int height = 600;
        final double centerX = -0.6;
        final double centerY = 0;
        final double figureWidth = 3.2;
        final int maxStep = 50;

        BufferedImage blackAndWhiteImage =
            createMandelbrotImage(width, height, centerX, centerY, figureWidth, maxStep, false);

        assert blackAndWhiteImage.getRGB(0, 0) == COLOR_WHITE.getRGB();
        assert blackAndWhiteImage.getRGB(width / 2, height / 2) == COLOR_BLACK.getRGB();

        BufferedImage coloredImage =
            createMandelbrotImage(width, height, centerX, centerY, figureWidth, maxStep, true);

        assert coloredImage.getRGB(0, 0) == COLOR_RED.getRGB();
        assert coloredImage.getRGB(width / 2, height / 2) == COLOR_BLACK.getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage createMandelbrotImage(
        int imageWidth,
        int imageHeight,
        double figureCenterX,
        double figureCenterY,
        double figureWidth,
        int maxStep,
        boolean useDistanceColorCoding
    ) {
        validateDimensions(imageWidth, imageHeight, maxStep);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double figureHeight = figureWidth * imageHeight / imageWidth;

        for (int imageX = 0; imageX < imageWidth; imageX++) {
            for (int imageY = 0; imageY < imageHeight; imageY++) {
                double figureX = mapToFigureCoordinate(
                    imageX, imageWidth, figureCenterX, figureWidth
                );
                double figureY = mapToFigureCoordinate(
                    imageY, imageHeight, figureCenterY, figureHeight
                );

                double distance = computeEscapeDistance(figureX, figureY, maxStep);
                Color color = useDistanceColorCoding
                    ? colorCodedColorMap(distance)
                    : blackAndWhiteColorMap(distance);

                image.setRGB(imageX, imageY, color.getRGB());
            }
        }

        return image;
    }

    private static double mapToFigureCoordinate(
        int pixelCoordinate,
        int imageSize,
        double figureCenter,
        double figureSize
    ) {
        return figureCenter + ((double) pixelCoordinate / imageSize - 0.5) * figureSize;
    }

    private static void validateDimensions(int imageWidth, int imageHeight, int maxStep) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }
        if (maxStep <= 0) {
            throw new IllegalArgumentException("maxStep should be greater than zero");
        }
    }

    private static Color blackAndWhiteColorMap(double distance) {
        return distance >= 1 ? COLOR_BLACK : COLOR_WHITE;
    }

    private static Color colorCodedColorMap(double distance) {
        if (distance >= 1) {
            return COLOR_BLACK;
        }

        double hue = 360 * distance;
        double saturation = 1;
        double value = 255;

        int hi = (int) Math.floor(hue / 60) % 6;
        double f = hue / 60 - Math.floor(hue / 60);

        int v = (int) value;
        int p = 0;
        int q = (int) (value * (1 - f * saturation));
        int t = (int) (value * (1 - (1 - f) * saturation));

        return switch (hi) {
            case 0 -> new Color(v, t, p);
            case 1 -> new Color(q, v, p);
            case 2 -> new Color(p, v, t);
            case 3 -> new Color(p, q, v);
            case 4 -> new Color(t, p, v);
            default -> new Color(v, p, q);
        };
    }

    private static double computeEscapeDistance(double figureX, double figureY, int maxStep) {
        double a = figureX;
        double b = figureY;
        int currentStep = 0;

        for (int step = 0; step < maxStep; step++) {
            currentStep = step;

            double aNew = a * a - b * b + figureX;
            b = 2 * a * b + figureY;
            a = aNew;

            if (a * a + b * b > 4) {
                break;
            }
        }

        return (double) currentStep / (maxStep - 1);
    }
}