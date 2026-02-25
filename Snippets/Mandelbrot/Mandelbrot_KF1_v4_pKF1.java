package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class MandelbrotGenerator {

    private MandelbrotGenerator() {}

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage =
                generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        assert blackAndWhiteImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert blackAndWhiteImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        BufferedImage coloredImage =
                generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();
        assert coloredImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static BufferedImage generateMandelbrotImage(
            int imageWidth,
            int imageHeight,
            double centerX,
            double centerY,
            double viewWidth,
            int maxIterations,
            boolean useColor
    ) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }

        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations should be greater than zero");
        }

        BufferedImage mandelbrotImage =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double viewHeight = viewWidth * imageHeight / imageWidth;

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                double pointReal =
                        centerX + ((double) pixelX / imageWidth - 0.5) * viewWidth;
                double pointImaginary =
                        centerY + ((double) pixelY / imageHeight - 0.5) * viewHeight;

                double normalizedEscapeTime =
                        computeNormalizedEscapeTime(pointReal, pointImaginary, maxIterations);

                Color pixelColor =
                        useColor
                                ? getColorForNormalizedEscapeTime(normalizedEscapeTime)
                                : getBlackAndWhiteColor(normalizedEscapeTime);

                mandelbrotImage.setRGB(pixelX, pixelY, pixelColor.getRGB());
            }
        }

        return mandelbrotImage;
    }

    private static Color getBlackAndWhiteColor(double normalizedEscapeTime) {
        return normalizedEscapeTime >= 1.0 ? new Color(0, 0, 0) : new Color(255, 255, 255);
    }

    private static Color getColorForNormalizedEscapeTime(double normalizedEscapeTime) {
        if (normalizedEscapeTime >= 1.0) {
            return new Color(0, 0, 0);
        }

        double hueDegrees = 360 * normalizedEscapeTime;
        double saturation = 1.0;
        double maxComponentValue = 255.0;

        int hueSectorIndex = (int) Math.floor(hueDegrees / 60) % 6;
        double sectorPosition = hueDegrees / 60 - Math.floor(hueDegrees / 60);

        int primaryComponent = (int) maxComponentValue;
        int zeroComponent = 0;
        int descendingComponent =
                (int) (maxComponentValue * (1 - sectorPosition * saturation));
        int ascendingComponent =
                (int) (maxComponentValue * (1 - (1 - sectorPosition) * saturation));

        switch (hueSectorIndex) {
            case 0:
                return new Color(primaryComponent, ascendingComponent, zeroComponent);
            case 1:
                return new Color(descendingComponent, primaryComponent, zeroComponent);
            case 2:
                return new Color(zeroComponent, primaryComponent, ascendingComponent);
            case 3:
                return new Color(zeroComponent, descendingComponent, primaryComponent);
            case 4:
                return new Color(ascendingComponent, zeroComponent, primaryComponent);
            default:
                return new Color(primaryComponent, zeroComponent, descendingComponent);
        }
    }

    private static double computeNormalizedEscapeTime(
            double pointReal, double pointImaginary, int maxIterations) {

        double zReal = pointReal;
        double zImaginary = pointImaginary;
        int lastIterationIndex = 0;

        for (int iterationIndex = 0; iterationIndex < maxIterations; iterationIndex++) {
            lastIterationIndex = iterationIndex;

            double nextReal =
                    zReal * zReal - zImaginary * zImaginary + pointReal;
            zImaginary = 2 * zReal * zImaginary + pointImaginary;
            zReal = nextReal;

            if (zReal * zReal + zImaginary * zImaginary > 4) {
                break;
            }
        }

        return (double) lastIterationIndex / (maxIterations - 1);
    }
}