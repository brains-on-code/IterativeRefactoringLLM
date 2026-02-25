package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The Mandelbrot set is the set of complex numbers "c" for which the series
 * "z_(n+1) = z_n * z_n + c" does not diverge, i.e. remains bounded. Thus, a
 * complex number "c" is a member of the Mandelbrot set if, when starting with
 * "z_0 = 0" and applying the iteration repeatedly, the absolute value of "z_n"
 * remains bounded for all "n > 0". Complex numbers can be written as "a + b*i":
 * "a" is the real component, usually drawn on the x-axis, and "b*i" is the
 * imaginary component, usually drawn on the y-axis. Most visualizations of the
 * Mandelbrot set use a color-coding to indicate after how many steps in the
 * series the numbers outside the set cross the divergence threshold. Images of
 * the Mandelbrot set exhibit an elaborate and infinitely complicated boundary
 * that reveals progressively ever-finer recursive detail at increasing
 * magnifications, making the boundary of the Mandelbrot set a fractal curve.
 * (description adapted from https://en.wikipedia.org/wiki/Mandelbrot_set ) (see
 * also https://en.wikipedia.org/wiki/Plotting_algorithms_for_the_Mandelbrot_set
 * )
 */
public final class Mandelbrot {

    private static final Color COLOR_BLACK = new Color(0, 0, 0);
    private static final Color COLOR_WHITE = new Color(255, 255, 255);
    private static final Color COLOR_RED = new Color(255, 0, 0);

    private Mandelbrot() {
        // Utility class
    }

    public static void main(String[] args) {
        final int width = 800;
        final int height = 600;
        final double centerX = -0.6;
        final double centerY = 0;
        final double figureWidth = 3.2;
        final int maxStep = 50;

        BufferedImage blackAndWhiteImage =
                getImage(width, height, centerX, centerY, figureWidth, maxStep, false);

        // Pixel outside the Mandelbrot set should be white.
        assert blackAndWhiteImage.getRGB(0, 0) == COLOR_WHITE.getRGB();

        // Pixel inside the Mandelbrot set should be black.
        assert blackAndWhiteImage.getRGB(width / 2, height / 2) == COLOR_BLACK.getRGB();

        BufferedImage coloredImage =
                getImage(width, height, centerX, centerY, figureWidth, maxStep, true);

        // Pixel distant to the Mandelbrot set should be red.
        assert coloredImage.getRGB(0, 0) == COLOR_RED.getRGB();

        // Pixel inside the Mandelbrot set should be black.
        assert coloredImage.getRGB(width / 2, height / 2) == COLOR_BLACK.getRGB();

        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to generate the image of the Mandelbrot set. Two types of
     * coordinates are used: image-coordinates that refer to the pixels and
     * figure-coordinates that refer to the complex numbers inside and outside
     * the Mandelbrot set. The figure-coordinates in the arguments of this
     * method determine which section of the Mandelbrot set is viewed. The main
     * area of the Mandelbrot set is roughly between "-1.5 < x < 0.5" and "-1 <
     * y < 1" in the figure-coordinates.
     *
     * @param imageWidth The width of the rendered image.
     * @param imageHeight The height of the rendered image.
     * @param figureCenterX The x-coordinate of the center of the figure.
     * @param figureCenterY The y-coordinate of the center of the figure.
     * @param figureWidth The width of the figure.
     * @param maxStep Maximum number of steps to check for divergent behavior.
     * @param useDistanceColorCoding Render in color or black and white.
     * @return The image of the rendered Mandelbrot set.
     */
    public static BufferedImage getImage(
            int imageWidth,
            int imageHeight,
            double figureCenterX,
            double figureCenterY,
            double figureWidth,
            int maxStep,
            boolean useDistanceColorCoding) {

        validateDimensions(imageWidth, imageHeight, maxStep);

        BufferedImage image =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        double figureHeight = figureWidth * imageHeight / imageWidth;

        for (int imageX = 0; imageX < imageWidth; imageX++) {
            for (int imageY = 0; imageY < imageHeight; imageY++) {
                double figureX = toFigureX(imageX, imageWidth, figureCenterX, figureWidth);
                double figureY = toFigureY(imageY, imageHeight, figureCenterY, figureHeight);

                double distance = getDistance(figureX, figureY, maxStep);
                Color color = useDistanceColorCoding
                        ? colorCodedColorMap(distance)
                        : blackAndWhiteColorMap(distance);

                image.setRGB(imageX, imageY, color.getRGB());
            }
        }

        return image;
    }

    private static void validateDimensions(int imageWidth, int imageHeight, int maxStep) {
        if (imageWidth <= 0) {
            throw new IllegalArgumentException("imageWidth should be greater than zero");
        }
        if (imageHeight <= 0) {
            throw new IllegalArgumentException("imageHeight should be greater than zero");
        }
        if (maxStep <= 0) {
            throw new IllegalArgumentException("maxStep should be greater than zero");
        }
    }

    private static double toFigureX(
            int imageX, int imageWidth, double figureCenterX, double figureWidth) {
        return figureCenterX + ((double) imageX / imageWidth - 0.5) * figureWidth;
    }

    private static double toFigureY(
            int imageY, int imageHeight, double figureCenterY, double figureHeight) {
        return figureCenterY + ((double) imageY / imageHeight - 0.5) * figureHeight;
    }

    /**
     * Black and white color-coding that ignores the relative distance. The
     * Mandelbrot set is black, everything else is white.
     *
     * @param distance Distance until divergence threshold
     * @return The color corresponding to the distance.
     */
    private static Color blackAndWhiteColorMap(double distance) {
        return distance >= 1 ? COLOR_BLACK : COLOR_WHITE;
    }

    /**
     * Color-coding taking the relative distance into account. The Mandelbrot
     * set is black.
     *
     * @param distance Distance until divergence threshold.
     * @return The color corresponding to the distance.
     */
    private static Color colorCodedColorMap(double distance) {
        if (distance >= 1) {
            return COLOR_BLACK;
        }

        // simplified transformation of HSV to RGB
        double hue = 360 * distance;
        double saturation = 1;
        double value = 255;

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
     * Return the relative distance (ratio of steps taken to maxStep) after
     * which the complex number constituted by this x-y-pair diverges. Members
     * of the Mandelbrot set do not diverge so their distance is 1.
     *
     * @param figureX The x-coordinate within the figure.
     * @param figureY The y-coordinate within the figure.
     * @param maxStep Maximum number of steps to check for divergent behavior.
     * @return The relative distance as the ratio of steps taken to maxStep.
     */
    private static double getDistance(double figureX, double figureY, int maxStep) {
        double a = figureX;
        double b = figureY;
        int currentStep = 0;

        for (int step = 0; step < maxStep; step++) {
            currentStep = step;

            double aNew = a * a - b * b + figureX;
            b = 2 * a * b + figureY;
            a = aNew;

            // divergence happens for all complex numbers with an absolute value
            // greater than 4 (= divergence threshold)
            if (a * a + b * b > 4) {
                break;
            }
        }

        return (double) currentStep / (maxStep - 1);
    }
}