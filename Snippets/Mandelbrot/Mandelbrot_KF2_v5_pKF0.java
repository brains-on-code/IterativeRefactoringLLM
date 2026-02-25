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
        final double centerY = 0.0;
        final double figureWidth = 3.2;
        final int maxIterations = 50;

        BufferedImage blackAndWhiteImage =
            createMandelbrotImage(width, height, centerX, centerY, figureWidth, maxIterations, false);

        assert blackAndWhiteImage.getRGB(0, 0) == COLOR_WHITE.getRGB();
        assert blackAndWhiteImage.getRGB(width / 2, height / 2) == COLOR_BLACK.getRGB();

        BufferedImage coloredImage =
            createMandelbrotImage(width, height, centerX, centerY, figureWidth, maxIterations, true);

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
        int maxIterations,
        boolean useDistanceColorCoding
    ) {
        validateDimensions(imageWidth, imageHeight, maxIterations);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double figureHeight = figureWidth * imageHeight / (double) imageWidth;

        for (int imageX = 0; imageX < imageWidth; imageX++) {
            for (int imageY = 0; imageY < imageHeight; imageY++) {
                double figureX = mapToFigureCoordinate(
                    imageX, imageWidth, figureCenterX, figureWidth
                );
                double figureY = mapToFigureCoordinate(
                    imageY, imageHeight, figureCenterY, figureHeight
                );

                double normalizedEscapeDistance =
                    computeEscapeDistance(figureX, figureY, maxIterations);

                Color color = selectColor(normalizedEscapeDistance, useDistanceColorCoding);

                image.setRGB(imageX, imageY, color.getRGB());
            }
        }

        return image;
    }

    private static Color selectColor(double normalizedEscapeDistance, boolean useDistanceColorCoding) {
        return useDistanceColorCoding
            ? colorCodedColorMap(normalizedEscapeDistance)
            : blackAndWhiteColorMap(normalizedEscapeDistance);
    }

    private static double mapToFigureCoordinate(
        int pixelCoordinate,
        int imageSize,
        double figureCenter,
        double figureSize
    ) {
        double normalizedPosition = (double) pixelCoordinate / imageSize - 0.5;
        return figureCenter + normalizedPosition * figureSize;
    }

    private static void validateDimensions(int imageWidth, int imageHeight, int maxIterations) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations should be greater than zero");
        }
    }

    private static Color blackAndWhiteColorMap(double normalizedDistance) {
        return normalizedDistance >= 1.0 ? COLOR_BLACK : COLOR_WHITE;
    }

    private static Color colorCodedColorMap(double normalizedDistance) {
        if (normalizedDistance >= 1.0) {
            return COLOR_BLACK;
        }

        double hue = 360.0 * normalizedDistance;
        double saturation = 1.0;
        double value = 255.0;

        int hi = (int) Math.floor(hue / 60.0) % 6;
        double f = hue / 60.0 - Math.floor(hue / 60.0);

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

    private static double computeEscapeDistance(double figureX, double figureY, int maxIterations) {
        double a = figureX;
        double b = figureY;
        int lastIteration = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            lastIteration = iteration;

            double aNew = a * a - b * b + figureX;
            b = 2 * a * b + figureY;
            a = aNew;

            if (a * a + b * b > 4.0) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }
}