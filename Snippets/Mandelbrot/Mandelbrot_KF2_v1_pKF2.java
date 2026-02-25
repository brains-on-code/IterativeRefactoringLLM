package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Mandelbrot {

    private Mandelbrot() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage =
            createMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        // Top-left pixel should be outside the set (white in B/W mode)
        assert blackAndWhiteImage.getRGB(0, 0) == Color.WHITE.getRGB();
        // Center pixel should be inside the set (black)
        assert blackAndWhiteImage.getRGB(400, 300) == Color.BLACK.getRGB();

        BufferedImage coloredImage =
            createMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        // Top-left pixel should be outside the set (red in color mode)
        assert coloredImage.getRGB(0, 0) == Color.RED.getRGB();
        // Center pixel should be inside the set (black)
        assert coloredImage.getRGB(400, 300) == Color.BLACK.getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an image of the Mandelbrot set.
     *
     * @param imageWidth  width of the resulting image in pixels (must be > 0)
     * @param imageHeight height of the resulting image in pixels (must be > 0)
     * @param centerX     x-coordinate of the figure center in the complex plane
     * @param centerY     y-coordinate of the figure center in the complex plane
     * @param figureWidth width of the figure in the complex plane
     * @param maxIterations maximum number of iterations for divergence test (must be > 0)
     * @param useColorMap if true, use a color gradient; otherwise use black and white
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
                double pointX =
                    centerX + ((double) pixelX / imageWidth - 0.5) * figureWidth;
                double pointY =
                    centerY + ((double) pixelY / imageHeight - 0.5) * figureHeight;

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

    /**
     * Black and white color map:
     * - Inside (or near) the set: black
     * - Outside the set: white
     */
    private static Color blackAndWhiteMap(double normalizedDistance) {
        return normalizedDistance >= 1 ? Color.BLACK : Color.WHITE;
    }

    /**
     * Simple HSV-like color gradient:
     * - Inside (or near) the set: black
     * - Outside the set: hue varies with normalized distance
     */
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

    /**
     * Computes a normalized "escape distance" for a point in the complex plane.
     * The value is in [0, 1], where 1 means the point did not escape within
     * the maximum number of iterations (i.e., is likely in the set).
     */
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