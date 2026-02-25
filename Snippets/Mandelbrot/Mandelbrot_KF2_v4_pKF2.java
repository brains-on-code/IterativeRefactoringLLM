package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Mandelbrot {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_CENTER_X = -0.6;
    private static final double DEFAULT_CENTER_Y = 0.0;
    private static final double DEFAULT_FIGURE_WIDTH = 3.2;
    private static final int DEFAULT_MAX_ITERATIONS = 50;

    private Mandelbrot() {}

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage =
            createMandelbrotImage(
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                DEFAULT_CENTER_X,
                DEFAULT_CENTER_Y,
                DEFAULT_FIGURE_WIDTH,
                DEFAULT_MAX_ITERATIONS,
                false
            );

        assertPixelColor(
            blackAndWhiteImage,
            0,
            0,
            Color.WHITE,
            "Top-left pixel should be outside the set (white in B/W mode)"
        );
        assertPixelColor(
            blackAndWhiteImage,
            DEFAULT_WIDTH / 2,
            DEFAULT_HEIGHT / 2,
            Color.BLACK,
            "Center pixel should be inside the set (black in B/W mode)"
        );

        BufferedImage coloredImage =
            createMandelbrotImage(
                DEFAULT_WIDTH,
                DEFAULT_HEIGHT,
                DEFAULT_CENTER_X,
                DEFAULT_CENTER_Y,
                DEFAULT_FIGURE_WIDTH,
                DEFAULT_MAX_ITERATIONS,
                true
            );

        assertPixelColor(
            coloredImage,
            0,
            0,
            Color.RED,
            "Top-left pixel should be outside the set (red in color mode)"
        );
        assertPixelColor(
            coloredImage,
            DEFAULT_WIDTH / 2,
            DEFAULT_HEIGHT / 2,
            Color.BLACK,
            "Center pixel should be inside the set (black in color mode)"
        );

        saveImage(coloredImage, "Mandelbrot.png");
    }

    private static void assertPixelColor(
        BufferedImage image,
        int x,
        int y,
        Color expectedColor,
        String message
    ) {
        assert image.getRGB(x, y) == expectedColor.getRGB() : message;
    }

    private static void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an image of the Mandelbrot set.
     *
     * @param imageWidth    width of the resulting image in pixels (must be > 0)
     * @param imageHeight   height of the resulting image in pixels (must be > 0)
     * @param centerX       x-coordinate of the figure center in the complex plane
     * @param centerY       y-coordinate of the figure center in the complex plane
     * @param figureWidth   width of the figure in the complex plane
     * @param maxIterations maximum number of iterations for divergence test (must be > 0)
     * @param useColorMap   if true, use a color gradient; otherwise use black and white
     * @return a BufferedImage representing the Mandelbrot set
     */
    public static BufferedImage createMandelbrotImage(
        int imageWidth,
        int imageHeight,
        double centerX,
        double centerY,
        double figureWidth,
        int maxIterations,
        boolean useColorMap
    ) {
        validateDimensions(imageWidth, imageHeight);
        validateMaxIterations(maxIterations);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        double figureHeight = figureWidth * imageHeight / imageWidth;

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                double pointX = mapToRealCoordinate(pixelX, imageWidth, centerX, figureWidth);
                double pointY = mapToRealCoordinate(pixelY, imageHeight, centerY, figureHeight);

                double normalizedDistance =
                    computeNormalizedEscapeDistance(pointX, pointY, maxIterations);

                Color color = useColorMap
                    ? colorGradientMap(normalizedDistance)
                    : blackAndWhiteMap(normalizedDistance);

                image.setRGB(pixelX, pixelY, color.getRGB());
            }
        }

        return image;
    }

    private static double mapToRealCoordinate(
        int pixelCoordinate,
        int imageSize,
        double centerCoordinate,
        double figureSize
    ) {
        return centerCoordinate + ((double) pixelCoordinate / imageSize - 0.5) * figureSize;
    }

    private static void validateDimensions(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("imageWidth must be greater than zero");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("imageHeight must be greater than zero");
        }
    }

    private static void validateMaxIterations(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations must be greater than zero");
        }
    }

    private static Color blackAndWhiteMap(double normalizedDistance) {
        return normalizedDistance >= 1 ? Color.BLACK : Color.WHITE;
    }

    private static Color colorGradientMap(double normalizedDistance) {
        if (normalizedDistance >= 1) {
            return Color.BLACK;
        }

        double hue = 360 * normalizedDistance;
        double saturation = 1.0;
        double value = 255.0;

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

    private static double computeNormalizedEscapeDistance(
        double pointX,
        double pointY,
        int maxIterations
    ) {
        double a = pointX;
        double b = pointY;
        int lastIteration = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            lastIteration = iteration;

            double aNew = a * a - b * b + pointX;
            b = 2 * a * b + pointY;
            a = aNew;

            if (a * a + b * b > 4) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }
}