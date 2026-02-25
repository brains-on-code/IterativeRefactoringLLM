package com.thealgorithms.others;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Renders images of the Mandelbrot set.
 *
 * <p>The Mandelbrot set is the set of complex numbers {@code c} for which the
 * sequence {@code z_(n+1) = z_n^2 + c} with {@code z_0 = 0} remains bounded.
 *
 * <p>Complex numbers are written as {@code a + b*i}, where {@code a} is the
 * real part (x-axis) and {@code b} is the imaginary part (y-axis).
 *
 * <p>Most visualizations color points outside the set according to how quickly
 * they diverge, while points inside the set are typically colored black.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Mandelbrot_set">Mandelbrot set</a>
 * @see <a href="https://en.wikipedia.org/wiki/Plotting_algorithms_for_the_Mandelbrot_set">
 * Plotting algorithms for the Mandelbrot set</a>
 */
public final class Mandelbrot {

    private static final Color COLOR_BLACK = new Color(0, 0, 0);
    private static final Color COLOR_WHITE = new Color(255, 255, 255);
    private static final Color COLOR_RED = new Color(255, 0, 0);

    private Mandelbrot() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        BufferedImage blackAndWhiteImage =
            getImage(800, 600, -0.6, 0, 3.2, 50, false);

        assertPixelColor(blackAndWhiteImage, 0, 0, COLOR_WHITE);
        assertPixelColor(blackAndWhiteImage, 400, 300, COLOR_BLACK);

        BufferedImage coloredImage =
            getImage(800, 600, -0.6, 0, 3.2, 50, true);

        assertPixelColor(coloredImage, 0, 0, COLOR_RED);
        assertPixelColor(coloredImage, 400, 300, COLOR_BLACK);

        writeImageToFile(coloredImage, "Mandelbrot.png");
    }

    private static void assertPixelColor(BufferedImage image, int x, int y, Color expectedColor) {
        assert image.getRGB(x, y) == expectedColor.getRGB();
    }

    private static void writeImageToFile(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an image of the Mandelbrot set.
     *
     * <p>Two coordinate systems are used:
     * <ul>
     *   <li><b>Image coordinates</b>: pixel positions (x, y).</li>
     *   <li><b>Figure coordinates</b>: complex plane coordinates (real, imaginary).</li>
     * </ul>
     *
     * <p>The figure coordinates determine which region of the complex plane is
     * rendered. The main body of the Mandelbrot set is roughly:
     * {@code -1.5 < x < 0.5} and {@code -1 < y < 1}.
     *
     * @param imageWidth  width of the image in pixels; must be > 0
     * @param imageHeight height of the image in pixels; must be > 0
     * @param figureCenterX real coordinate of the figure center
     * @param figureCenterY imaginary coordinate of the figure center
     * @param figureWidth width of the region in the complex plane
     * @param maxStep maximum number of iterations for divergence testing; must be > 0
     * @param useDistanceColorCoding {@code true} for colored output,
     *                               {@code false} for black-and-white
     * @return rendered Mandelbrot image
     */
    public static BufferedImage getImage(
        int imageWidth,
        int imageHeight,
        double figureCenterX,
        double figureCenterY,
        double figureWidth,
        int maxStep,
        boolean useDistanceColorCoding
    ) {
        validateDimensions(imageWidth, imageHeight);
        validateMaxStep(maxStep);

        BufferedImage image =
            new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        double figureHeight = figureWidth * imageHeight / imageWidth;

        for (int imageX = 0; imageX < imageWidth; imageX++) {
            for (int imageY = 0; imageY < imageHeight; imageY++) {
                double figureX = toFigureCoordinate(
                    imageX, imageWidth, figureCenterX, figureWidth
                );
                double figureY = toFigureCoordinate(
                    imageY, imageHeight, figureCenterY, figureHeight
                );

                double distance = getDistance(figureX, figureY, maxStep);

                Color color = useDistanceColorCoding
                    ? colorCodedColorMap(distance)
                    : blackAndWhiteColorMap(distance);

                image.setRGB(imageX, imageY, color.getRGB());
            }
        }

        return image;
    }

    private static double toFigureCoordinate(
        int imageCoord,
        int imageSize,
        double figureCenter,
        double figureSize
    ) {
        return figureCenter + ((double) imageCoord / imageSize - 0.5) * figureSize;
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
     * Black-and-white color map.
     *
     * <p>Points inside the Mandelbrot set (non-divergent) are black; all others
     * are white.
     *
     * @param distance relative distance to divergence threshold
     * @return black for {@code distance >= 1}, white otherwise
     */
    private static Color blackAndWhiteColorMap(double distance) {
        return distance >= 1 ? COLOR_BLACK : COLOR_WHITE;
    }

    /**
     * Color map that encodes divergence speed.
     *
     * <p>Points inside the Mandelbrot set (non-divergent) are black. Points
     * outside are colored using a simple HSV-to-RGB mapping where the hue is
     * derived from the distance.
     *
     * @param distance relative distance to divergence threshold
     * @return color corresponding to the given distance
     */
    private static Color colorCodedColorMap(double distance) {
        if (distance >= 1) {
            return COLOR_BLACK;
        }

        double hue = 360 * distance;
        double saturation = 1;
        double value = 255;

        int hi = (int) (Math.floor(hue / 60)) % 6;
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
     * Computes the relative iteration count until divergence.
     *
     * <p>For a complex number {@code c = figureX + figureY*i}, this method
     * iterates {@code z_(n+1) = z_n^2 + c} starting from {@code z_0 = 0} up to
     * {@code maxStep} times, or until {@code |z_n|^2 > 4}.
     *
     * <p>The returned value is {@code step / (maxStep - 1)}. Points that do not
     * diverge within {@code maxStep} iterations return a value close to 1.
     *
     * @param figureX real part of the complex number
     * @param figureY imaginary part of the complex number
     * @param maxStep maximum number of iterations
     * @return relative iteration count until divergence in [0, 1]
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

            if (a * a + b * b > 4) {
                break;
            }
        }

        return (double) currentStep / (maxStep - 1);
    }
}