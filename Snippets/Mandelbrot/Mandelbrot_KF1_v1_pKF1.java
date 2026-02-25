package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public final class MandelbrotGenerator {

    private MandelbrotGenerator() {
    }

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage = generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        assert blackAndWhiteImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();
        assert blackAndWhiteImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        BufferedImage coloredImage = generateMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();
        assert coloredImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }

        if (maxIterations <= 0) {
            throw new IllegalArgumentException("maxIterations should be greater than zero");
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double viewHeight = viewWidth / imageWidth * imageHeight;

        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                double real = centerX + ((double) x / imageWidth - 0.5) * viewWidth;
                double imaginary = centerY + ((double) y / imageHeight - 0.5) * viewHeight;

                double normalizedIteration = computeEscapeTime(real, imaginary, maxIterations);

                Color color = colored
                        ? getColorForIteration(normalizedIteration)
                        : getBlackAndWhiteColor(normalizedIteration);

                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }

    private static Color getBlackAndWhiteColor(double normalizedIteration) {
        return normalizedIteration >= 1 ? new Color(0, 0, 0) : new Color(255, 255, 255);
    }

    private static Color getColorForIteration(double normalizedIteration) {
        if (normalizedIteration >= 1) {
            return new Color(0, 0, 0);
        } else {
            double hueDegrees = 360 * normalizedIteration;
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
    }

    private static double computeEscapeTime(double real, double imaginary, int maxIterations) {
        double zReal = real;
        double zImaginary = imaginary;
        int lastIteration = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            lastIteration = iteration;

            double nextReal = zReal * zReal - zImaginary * zImaginary + real;
            zImaginary = 2 * zReal * zImaginary + imaginary;
            zReal = nextReal;

            if (zReal * zReal + zImaginary * zImaginary > 4) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }
}