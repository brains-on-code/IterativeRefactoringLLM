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

        assert blackAndWhiteImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert blackAndWhiteImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        BufferedImage coloredImage =
                createMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();
        assert coloredImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
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

        BufferedImage image =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        double viewHeight = viewWidth * imageHeight / imageWidth;

        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                double pointX = centerX + ((double) pixelX / imageWidth - 0.5) * viewWidth;
                double pointY = centerY + ((double) pixelY / imageHeight - 0.5) * viewHeight;

                double normalizedEscapeIteration =
                        computeNormalizedEscapeIteration(pointX, pointY, maxIterations);

                Color color =
                        useColorGradient
                                ? mapDistanceToColorGradient(normalizedEscapeIteration)
                                : mapDistanceToBlackAndWhite(normalizedEscapeIteration);

                image.setRGB(pixelX, pixelY, color.getRGB());
            }
        }

        return image;
    }

    private static Color mapDistanceToBlackAndWhite(double normalizedEscapeIteration) {
        return normalizedEscapeIteration >= 1 ? Color.BLACK : Color.WHITE;
    }

    private static Color mapDistanceToColorGradient(double normalizedEscapeIteration) {
        if (normalizedEscapeIteration >= 1) {
            return Color.BLACK;
        }

        double hueDegrees = 360 * normalizedEscapeIteration;
        double saturation = 1;
        double value = 255;

        int hueSector = (int) (Math.floor(hueDegrees / 60)) % 6;
        double fractionalSector = hueDegrees / 60 - Math.floor(hueDegrees / 60);

        int v = (int) value;
        int p = 0;
        int q = (int) (value * (1 - fractionalSector * saturation));
        int t = (int) (value * (1 - (1 - fractionalSector) * saturation));

        switch (hueSector) {
            case 0:
                return new Color(v, t, p);
            case 1:
                return new Color(q, v, p);
            case 2:
                return new Color(p, v, t);
            case 3:
                return new Color(p, q, v);
            case 4:
                return new Color(t, p, v);
            default:
                return new Color(v, p, q);
        }
    }

    private static double computeNormalizedEscapeIteration(
            double pointX, double pointY, int maxIterations) {

        double real = pointX;
        double imaginary = pointY;
        int lastIteration = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            lastIteration = iteration;

            double nextReal = real * real - imaginary * imaginary + pointX;
            imaginary = 2 * real * imaginary + pointY;
            real = nextReal;

            if (real * real + imaginary * imaginary > 4) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }
}