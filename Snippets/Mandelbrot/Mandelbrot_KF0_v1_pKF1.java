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

    private Mandelbrot() {}

    public static void main(String[] args) {
        // Test black and white
        BufferedImage blackAndWhiteImage =
                renderMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, false);

        // Pixel outside the Mandelbrot set should be white.
        assert blackAndWhiteImage.getRGB(0, 0) == new Color(255, 255, 255).getRGB();

        // Pixel inside the Mandelbrot set should be black.
        assert blackAndWhiteImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        // Test color-coding
        BufferedImage coloredImage =
                renderMandelbrotImage(800, 600, -0.6, 0, 3.2, 50, true);

        // Pixel distant to the Mandelbrot set should be red.
        assert coloredImage.getRGB(0, 0) == new Color(255, 0, 0).getRGB();

        // Pixel inside the Mandelbrot set should be black.
        assert coloredImage.getRGB(400, 300) == new Color(0, 0, 0).getRGB();

        // Save image
        try {
            ImageIO.write(coloredImage, "png", new File("Mandelbrot.png"));
        } catch (IOException exception) {
            exception.printStackTrace();
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
     * @param maxIterations Maximum number of iterations to check for divergent behavior.
     * @param useDistanceColorCoding Render in color or black and white.
     * @return The image of the rendered Mandelbrot set.
     */
    public static BufferedImage renderMandelbrotImage(
            int imageWidth,
            int imageHeight,
            double figureCenterX,
            double figureCenterY,
            double figureWidth,
            int maxIterations,
            boolean useDistanceColorCoding) {

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
        double figureHeight = figureWidth * imageHeight / imageWidth;

        // loop through the image-coordinates
        for (int pixelX = 0; pixelX < imageWidth; pixelX++) {
            for (int pixelY = 0; pixelY < imageHeight; pixelY++) {
                // determine the figure-coordinates based on the image-coordinates
                double figureX =
                        figureCenterX + ((double) pixelX / imageWidth - 0.5) * figureWidth;
                double figureY =
                        figureCenterY + ((double) pixelY / imageHeight - 0.5) * figureHeight;

                double normalizedEscapeIteration =
                        computeNormalizedEscapeIteration(figureX, figureY, maxIterations);

                // color the corresponding pixel based on the selected coloring-function
                Color pixelColor =
                        useDistanceColorCoding
                                ? mapDistanceToColor(normalizedEscapeIteration)
                                : mapDistanceToBlackAndWhite(normalizedEscapeIteration);

                image.setRGB(pixelX, pixelY, pixelColor.getRGB());
            }
        }

        return image;
    }

    /**
     * Black and white color-coding that ignores the relative distance. The
     * Mandelbrot set is black, everything else is white.
     *
     * @param normalizedEscapeIteration Distance until divergence threshold
     * @return The color corresponding to the distance.
     */
    private static Color mapDistanceToBlackAndWhite(double normalizedEscapeIteration) {
        return normalizedEscapeIteration >= 1 ? new Color(0, 0, 0) : new Color(255, 255, 255);
    }

    /**
     * Color-coding taking the relative distance into account. The Mandelbrot
     * set is black.
     *
     * @param normalizedEscapeIteration Distance until divergence threshold.
     * @return The color corresponding to the distance.
     */
    private static Color mapDistanceToColor(double normalizedEscapeIteration) {
        if (normalizedEscapeIteration >= 1) {
            return new Color(0, 0, 0);
        }

        // simplified transformation of HSV to RGB
        // normalizedEscapeIteration determines hue
        double hueDegrees = 360 * normalizedEscapeIteration;
        double saturation = 1;
        double value = 255;

        int hueSectorIndex = (int) (Math.floor(hueDegrees / 60)) % 6;
        double fractionalSector = hueDegrees / 60 - Math.floor(hueDegrees / 60);

        int v = (int) value;
        int p = 0;
        int q = (int) (value * (1 - fractionalSector * saturation));
        int t = (int) (value * (1 - (1 - fractionalSector) * saturation));

        switch (hueSectorIndex) {
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

    /**
     * Return the relative distance (ratio of iterations taken to maxIterations) after
     * which the complex number constituted by this x-y-pair diverges. Members
     * of the Mandelbrot set do not diverge so their distance is 1.
     *
     * @param figureX The x-coordinate within the figure.
     * @param figureY The y-coordinate within the figure.
     * @param maxIterations Maximum number of iterations to check for divergent behavior.
     * @return The relative distance as the ratio of iterations taken to maxIterations.
     */
    private static double computeNormalizedEscapeIteration(
            double figureX, double figureY, int maxIterations) {

        double realComponent = figureX;
        double imaginaryComponent = figureY;
        int lastIteration = 0;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            lastIteration = iteration;

            double nextRealComponent =
                    realComponent * realComponent - imaginaryComponent * imaginaryComponent + figureX;
            imaginaryComponent = 2 * realComponent * imaginaryComponent + figureY;
            realComponent = nextRealComponent;

            // divergence happens for all complex number with an absolute value
            // greater than 4 (= divergence threshold)
            if (realComponent * realComponent + imaginaryComponent * imaginaryComponent > 4) {
                break;
            }
        }

        return (double) lastIteration / (maxIterations - 1);
    }
}