package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class MandelbrotGenerator {

    private static final int DEFAULT_IMAGE_WIDTH = 800;
    private static final int DEFAULT_IMAGE_HEIGHT = 600;

    private static final double DEFAULT_CENTER_X = -0.6;
    private static final double DEFAULT_CENTER_Y = 0.0;
    private static final double DEFAULT_VIEW_WIDTH = 3.2;

    private static final int DEFAULT_MAX_ITERATIONS = 50;

    private static final String OUTPUT_FILE_NAME = "Mandelbrot.png";
    private static final String OUTPUT_FORMAT = "png";

    private MandelbrotGenerator() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        BufferedImage blackWhiteImage = generateMandelbrotImage(
            DEFAULT_IMAGE_WIDTH,
            DEFAULT_IMAGE_HEIGHT,
            DEFAULT_CENTER_X,
            DEFAULT_CENTER_Y,
            DEFAULT_VIEW_WIDTH,
            DEFAULT_MAX_ITERATIONS,
            false
        );

        assertPixelColor(blackWhiteImage, 0, 0, Color.WHITE);
        assertPixelColor(
            blackWhiteImage,
            DEFAULT_IMAGE_WIDTH / 2,
            DEFAULT_IMAGE_HEIGHT / 2,
            Color.BLACK
        );

        BufferedImage coloredImage = generateMandelbrotImage(
            DEFAULT_IMAGE_WIDTH,
            DEFAULT_IMAGE_HEIGHT,
            DEFAULT_CENTER_X,
            DEFAULT_CENTER_Y,
            DEFAULT_VIEW_WIDTH,
            DEFAULT_MAX_ITERATIONS,
            true
        );

        assertPixelColor(coloredImage, 0, 0, new Color(255, 0, 0));
        assertPixelColor(
            coloredImage,
            DEFAULT_IMAGE_WIDTH / 2,
            DEFAULT_IMAGE_HEIGHT / 2,
            Color.BLACK
        );

        writeImageToFile(coloredImage, OUTPUT_FORMAT, OUTPUT_FILE_NAME);
    }

    public static BufferedImage generateMandelbrotImage(
        int imageWidth,
        int imageHeight,
        double centerX,
        double centerY,
        double viewWidth,
        int maxIterations,
        boolean colored
    ) {
        validateDimensions(imageWidth, imageHeight);
        validateIterations(maxIterations);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        double viewHeight = viewWidth * imageHeight / imageWidth;

        for (int x = 0; x < imageWidth; x++) {
            double real = computeRealCoordinate(x, imageWidth, centerX, viewWidth);

            for (int y = 0; y < imageHeight; y++) {
                double imaginary =
                    computeImaginaryCoordinate(y, imageHeight, centerY, viewHeight);

                double normalizedIteration =
                    computeMandelbrotEscape(real, imaginary, maxIterations);

                Color color = colored
                    ? getSmoothColor(normalizedIteration)
                    : getBlackWhiteColor(normalizedIteration);

                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }

    private static void validateDimensions(int imageWidth, int imageHeight) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }
    }

    private static void validateIterations(int maxIterations) {
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations should be greater than zero");
        }
    }

    private static double computeRealCoordinate(
        int x,
        int imageWidth,
        double centerX,
        double viewWidth
    ) {
        return centerX + ((double) x / imageWidth - 0.5) * viewWidth;
    }

    private static double computeImaginaryCoordinate(
        int y,
        int imageHeight,
        double centerY,
        double viewHeight
    ) {
        return centerY + ((double) y / imageHeight - 0.5) * viewHeight;
    }

    private static Color getBlackWhiteColor(double normalizedIteration) {
        return normalizedIteration >= 1 ? Color.BLACK : Color.WHITE;
    }

    private static Color getSmoothColor(double normalizedIteration) {
        if (normalizedIteration >= 1) {
            return Color.BLACK;
        }

        double hueDegrees = 360 * normalizedIteration;
        double saturation = 1.0;
        double value = 255.0;

        int sector = (int) Math.floor(hueDegrees / 60) % 6;
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

    private static double computeMandelbrotEscape(
        double real,
        double imaginary,
        int maxIterations
    ) {
        double zReal = real;
        double zImaginary = imaginary;
        int lastIteration = 0;

        for (int i = 0; i < maxIterations; i++) {
            lastIteration = i;

            double nextReal = zReal * zReal - zImaginary * zImaginary + real;
            double nextImaginary = 2 * zReal * zImaginary + imaginary;

            zReal = nextReal;
            zImaginary = nextImaginary;

            if (hasEscaped(zReal, zImaginary)) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }

    private static boolean hasEscaped(double zReal, double zImaginary) {
        return zReal * zReal + zImaginary * zImaginary > 4;
    }

    private static void assertPixelColor(
        BufferedImage image,
        int x,
        int y,
        Color expectedColor
    ) {
        assert image.getRGB(x, y) == expectedColor.getRGB();
    }

    private static void writeImageToFile(
        BufferedImage image,
        String format,
        String fileName
    ) {
        try {
            ImageIO.write(image, format, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}