package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public final class Mandelbrot {
    private Mandelbrot() {
    }

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage = getImage(800, 600, -0.6, 0, 3.2, 50, false);

        assert blackAndWhiteImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();

        assert blackAndWhiteImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        BufferedImage coloredImage = getImage(800, 600, -0.6, 0, 3.2, 50, true);

        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();

        assert coloredImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage getImage(int imageWidth, int imageHeight, double figureCenterX, double figureCenterY, double figureWidth, int maxStep, boolean useDistanceColorCoding) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }

        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }

        if (maxStep <= 0) {
            throw new IllegalArgumentException("maxStep should be greater than zero");
        }

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double figureHeight = figureWidth / imageWidth * imageHeight;

        for (int imageX = 0; imageX < imageWidth; imageX++) {
            for (int imageY = 0; imageY < imageHeight; imageY++) {
                double figureX = figureCenterX + ((double) imageX / imageWidth - 0.5) * figureWidth;
                double figureY = figureCenterY + ((double) imageY / imageHeight - 0.5) * figureHeight;

                double distance = getDistance(figureX, figureY, maxStep);

                image.setRGB(imageX, imageY, useDistanceColorCoding ? colorCodedColorMap(distance).getRGB() : blackAndWhiteColorMap(distance).getRGB());
            }
        }

        return image;
    }


    private static Color blackAndWhiteColorMap(double distance) {
        return distance >= 1 ? new Color(0, 0, 0) : new Color(255, 255, 255);
    }


    private static Color colorCodedColorMap(double distance) {
        if (distance >= 1) {
            return new Color(0, 0, 0);
        } else {
            double hue = 360 * distance;
            double saturation = 1;
            double val = 255;
            int hi = (int) (Math.floor(hue / 60)) % 6;
            double f = hue / 60 - Math.floor(hue / 60);

            int v = (int) val;
            int p = 0;
            int q = (int) (val * (1 - f * saturation));
            int t = (int) (val * (1 - (1 - f) * saturation));

            switch (hi) {
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


    private static double getDistance(double figureX, double figureY, int maxStep) {
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
