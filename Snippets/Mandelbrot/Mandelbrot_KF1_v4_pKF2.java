package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Utility class for generating Mandelbrot set images.
 */
public final class MandelbrotGenerator {

    private MandelbrotGenerator() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        BufferedImage bwImage =
            generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        assert bwImage.getRGB(0, 0) == Color.WHITE.getRGB();
        assert bwImage.getRGB(400, 300) == Color.BLACK.getRGB();

        BufferedImage colorImage =
            generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert colorImage.getRGB(0, 0) == Color.RED.getRGB();
        assert colorImage.getRGB(400, 300) == Color.BLACK.getRGB();

        try {
            ImageIO.write(colorImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a Mandelbrot set image.
     *
     * @param imageWidth  image width in pixels (must be > 0)
     * @param imageHeight image height in pixels (must be > 0)
     * @param centerX     x-coordinate of the center in the complex plane
     * @param centerY     y-coordinate of the center in the complex plane
     * @param planeWidth  width of the complex-plane region to render
     * @param maxStep     maximum number of iterations (must be > 0)
     * @param colored     true for colored output, false for black-and-white
     * @return generated Mandelbrot image
     */
    public static BufferedImage generateMandelbrotImage(
        int imageWidth,
        int imageHeight,
        double centerX,
        double centerY,
        double planeWidth,
        int maxStep,
        boolean colored
    ) {
        validateDimensions(imageWidth, imageHeight);
        validateMaxStep(maxStep);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        double planeHeight = planeWidth * imageHeight / imageWidth;

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                double real = mapToReal(x, imageWidth, centerX, planeWidth);
                double imaginary = mapToImaginary(y, imageHeight, centerY, planeHeight);

                double normalizedIteration =
                    computeNormalizedIteration(real, imaginary, maxStep);

                Color color = colored
                    ? colorForIteration(normalizedIteration)
                    : bwColorForIteration(normalizedIteration);

                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }

    private static double mapToReal(
        int x,
        int imageWidth,
        double centerX,
        double planeWidth
    ) {
        return centerX + ((double) x / imageWidth - 0.5) * planeWidth;
    }

    private static double mapToImaginary(
        int y,
        int imageHeight,
        double centerY,
        double planeHeight
    ) {
        return centerY + ((double) y / imageHeight - 0.5) * planeHeight;
    }

    private static void validateDimensions(int imageWidth, int imageHeight) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth must be greater than zero");
        }
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight must be greater than zero");
        }
    }

    private static void validateMaxStep(int maxStep) {
        if (maxStep <= 0) {
            throw new IllegalArgumentException("maxStep must be greater than zero");
        }
    }

    /**
     * Returns a black-and-white color based on the normalized iteration count.
     *
     * @param normalizedIteration normalized iteration count in [0, 1]
     * @return black if inside the set, white otherwise
     */
    private static Color bwColorForIteration(double normalizedIteration) {
        return normalizedIteration >= 1 ? Color.BLACK : Color.WHITE;
    }

    /**
     * Returns a color based on the normalized iteration count using an HSV-like
     * mapping.
     *
     * @param normalizedIteration normalized iteration count in [0, 1]
     * @return color corresponding to the iteration count
     */
    private static Color colorForIteration(double normalizedIteration) {
        if (normalizedIteration >= 1) {
            return Color.BLACK;
        }

        double hueDegrees = 360 * normalizedIteration;
        double saturation = 1;
        double value = 255;

        int sector = (int) (Math.floor(hueDegrees / 60)) % 6;
        double fractional = hueDegrees / 60 - Math.floor(hueDegrees / 60);

        int v = (int) value;
        int p = 0;
        int q = (int) (value * (1 - fractional * saturation));
        int t = (int) (value * (1 - (1 - fractional) * saturation));

        return switch (sector) {
            case 0 -> new Color(v, t, p);
            case 1 -> new Color(q, v, p);
            case 2 -> new Color(p, v, t);
            case 3 -> new Color(p, q, v);
            case 4 -> new Color(t, p, v);
            default -> new Color(v, p, q);
        };
    }

    /**
     * Computes the normalized iteration count for a point in the complex plane
     * with respect to the Mandelbrot set.
     *
     * @param real    real part of the complex number
     * @param imag    imaginary part of the complex number
     * @param maxStep maximum number of iterations
     * @return normalized iteration count in [0, 1]
     */
    private static double computeNormalizedIteration(
        double real,
        double imag,
        int maxStep
    ) {
        double zReal = real;
        double zImag = imag;
        int lastIteration = 0;

        for (int i = 0; i < maxStep; i++) {
            lastIteration = i;

            double nextReal = zReal * zReal - zImag * zImag + real;
            zImag = 2 * zReal * zImag + imag;
            zReal = nextReal;

            if (zReal * zReal + zImag * zImag > 4) {
                break;
            }
        }

        return (double) lastIteration / (maxStep - 1);
    }
}