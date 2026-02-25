package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class Mandelbrot {

    private Mandelbrot() {}

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage =
                createMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        assert blackAndWhiteImage.getRGB(0, 0) == Color.WHITE.getRGB();
        assert blackAndWhiteImage.getRGB(400, 300) == Color.BLACK.getRGB();

        BufferedImage coloredImage =
                createMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();
        assert coloredImage.getRGB(400, 300) == Color.BLACK.getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static BufferedImage createMandelbrotImage(
            int imageWidth,
            int imageHeight,
            double centerX,
            double centerY,
            double viewWidth,
            int maxIterations,
            boolean useColorGradient) {

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
                double complexReal =
                        centerX + ((double) pixelX / imageWidth - 0.5) * viewWidth;
                double complexImaginary =
                        centerY + ((double) pixelY / imageHeight - 0.5) * viewHeight;

                double normalizedEscapeIteration =
                        computeNormalizedEscapeIteration(
                                complexReal, complexImaginary, maxIterations);

                Color pixelColor =
                        useColorGradient
                                ? mapNormalizedIterationToGradientColor(normalizedEscapeIteration)
                                : mapNormalizedIterationToBlackAndWhite(normalizedEscapeIteration);

                mandelbrotImage.setRGB(pixelX, pixelY, pixelColor.getRGB());
            }
        }

        return mandelbrotImage;
    }

    private static Color mapNormalizedIterationToBlackAndWhite(
            double normalizedEscapeIteration) {
        return normalizedEscapeIteration >= 1 ? Color.BLACK : Color.WHITE;
    }

    private static Color mapNormalizedIterationToGradientColor(
            double normalizedEscapeIteration) {
        if (normalizedEscapeIteration >= 1) {
            return Color.BLACK;
        }

        double hueDegrees = 360 * normalizedEscapeIteration;
        double saturation = 1;
        double brightness = 255;

        int hueSectorIndex = (int) (Math.floor(hueDegrees / 60)) % 6;
        double sectorFraction = hueDegrees / 60 - Math.floor(hueDegrees / 60);

        int valueComponent = (int) brightness;
        int baseComponent = 0;
        int descendingComponent =
                (int) (brightness * (1 - sectorFraction * saturation));
        int ascendingComponent =
                (int) (brightness * (1 - (1 - sectorFraction) * saturation));

        switch (hueSectorIndex) {
            case 0:
                return new Color(valueComponent, ascendingComponent, baseComponent);
            case 1:
                return new Color(descendingComponent, valueComponent, baseComponent);
            case 2:
                return new Color(baseComponent, valueComponent, ascendingComponent);
            case 3:
                return new Color(baseComponent, descendingComponent, valueComponent);
            case 4:
                return new Color(ascendingComponent, baseComponent, valueComponent);
            default:
                return new Color(valueComponent, baseComponent, descendingComponent);
        }
    }

    private static double computeNormalizedEscapeIteration(
            double complexReal, double complexImaginary, int maxIterations) {

        double zReal = complexReal;
        double zImaginary = complexImaginary;
        int lastIterationIndex = 0;

        for (int iterationIndex = 0; iterationIndex < maxIterations; iterationIndex++) {
            lastIterationIndex = iterationIndex;

            double nextReal =
                    zReal * zReal - zImaginary * zImaginary + complexReal;
            zImaginary = 2 * zReal * zImaginary + complexImaginary;
            zReal = nextReal;

            if (zReal * zReal + zImaginary * zImaginary > 4) {
                break;
            }
        }

        return (double) lastIterationIndex / (maxIterations - 1);
    }
}