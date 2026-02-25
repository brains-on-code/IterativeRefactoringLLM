package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between HSV and RGB color spaces.
 */
public final class HsvRgbConverter {

    private HsvRgbConverter() {
        // Prevent instantiation
    }

    /**
     * Simple self-test for the HSV/RGB conversion methods.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        // HSV → RGB tests
        assert Arrays.equals(hsvToRgb(0, 0, 0), new int[] {0, 0, 0});
        assert Arrays.equals(hsvToRgb(0, 0, 1), new int[] {255, 255, 255});
        assert Arrays.equals(hsvToRgb(0, 1, 1), new int[] {255, 0, 0});
        assert Arrays.equals(hsvToRgb(60, 1, 1), new int[] {255, 255, 0});
        assert Arrays.equals(hsvToRgb(120, 1, 1), new int[] {0, 255, 0});
        assert Arrays.equals(hsvToRgb(240, 1, 1), new int[] {0, 0, 255});
        assert Arrays.equals(hsvToRgb(300, 1, 1), new int[] {255, 0, 255});
        assert Arrays.equals(hsvToRgb(180, 0.5, 0.5), new int[] {64, 128, 128});
        assert Arrays.equals(hsvToRgb(234, 0.14, 0.88), new int[] {193, 196, 224});
        assert Arrays.equals(hsvToRgb(330, 0.75, 0.5), new int[] {128, 32, 80});

        // RGB → HSV tests (with tolerance)
        assert hsvEqualsWithTolerance(rgbToHsv(0, 0, 0), new double[] {0, 0, 0});
        assert hsvEqualsWithTolerance(rgbToHsv(255, 255, 255), new double[] {0, 0, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(255, 0, 0), new double[] {0, 1, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(255, 255, 0), new double[] {60, 1, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(0, 255, 0), new double[] {120, 1, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(0, 0, 255), new double[] {240, 1, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(255, 0, 255), new double[] {300, 1, 1});
        assert hsvEqualsWithTolerance(rgbToHsv(64, 128, 128), new double[] {180, 0.5, 0.5});
        assert hsvEqualsWithTolerance(rgbToHsv(193, 196, 224), new double[] {234, 0.14, 0.88});
        assert hsvEqualsWithTolerance(rgbToHsv(128, 32, 80), new double[] {330, 0.75, 0.5});
    }

    /**
     * Converts an HSV color value to its RGB representation.
     *
     * @param hue        hue component in degrees, in the range [0, 360]
     * @param saturation saturation component, in the range [0, 1]
     * @param value      value (brightness) component, in the range [0, 1]
     * @return an array of three integers representing RGB components in the range [0, 255]
     * @throws IllegalArgumentException if any component is out of range
     */
    public static int[] hsvToRgb(double hue, double saturation, double value) {
        if (hue < 0 || hue > 360) {
            throw new IllegalArgumentException("hue should be between 0 and 360");
        }
        if (saturation < 0 || saturation > 1) {
            throw new IllegalArgumentException("saturation should be between 0 and 1");
        }
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("value should be between 0 and 1");
        }

        double chroma = value * saturation;
        double huePrime = hue / 60.0;
        double x = chroma * (1 - Math.abs(huePrime % 2 - 1));
        double m = value - chroma;

        return mapHueSectorToRgb(huePrime, chroma, m, x);
    }

    /**
     * Converts an RGB color value to its HSV representation.
     *
     * @param red   red component in the range [0, 255]
     * @param green green component in the range [0, 255]
     * @param blue  blue component in the range [0, 255]
     * @return an array of three doubles representing HSV components:
     *         hue in [0, 360], saturation in [0, 1], value in [0, 1]
     * @throws IllegalArgumentException if any component is out of range
     */
    public static double[] rgbToHsv(int red, int green, int blue) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("red should be between 0 and 255");
        }
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("green should be between 0 and 255");
        }
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("blue should be between 0 and 255");
        }

        double r = red / 255.0;
        double g = green / 255.0;
        double b = blue / 255.0;

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);
        double delta = max - min;

        double value = max;
        double saturation = max == 0 ? 0 : delta / max;
        double hue;

        if (delta == 0) {
            hue = 0;
        } else if (max == r) {
            hue = 60 * ((g - b) / delta);
        } else if (max == g) {
            hue = 60 * (2 + (b - r) / delta);
        } else {
            hue = 60 * (4 + (r - g) / delta);
        }

        hue = (hue + 360) % 360;

        return new double[] {hue, saturation, value};
    }

    /**
     * Compares two HSV triplets with a small tolerance for floating-point error.
     *
     * @param actual   the actual HSV values
     * @param expected the expected HSV values
     * @return true if all components are within their respective tolerances
     */
    private static boolean hsvEqualsWithTolerance(double[] actual, double[] expected) {
        boolean hueClose = Math.abs(actual[0] - expected[0]) < 0.2;
        boolean saturationClose = Math.abs(actual[1] - expected[1]) < 0.002;
        boolean valueClose = Math.abs(actual[2] - expected[2]) < 0.002;

        return hueClose && saturationClose && valueClose;
    }

    /**
     * Maps the hue sector and intermediate HSV values to RGB components.
     *
     * @param huePrime hue divided by 60 (sector index in [0, 6))
     * @param chroma   chroma component
     * @param m        match value added to each component
     * @param x        second-largest component based on hue sector
     * @return RGB components in [0, 255]
     */
    private static int[] mapHueSectorToRgb(double huePrime, double chroma, double m, double x) {
        int red;
        int green;
        int blue;

        if (huePrime >= 0 && huePrime <= 1) {
            red = toRgbComponent(chroma + m);
            green = toRgbComponent(x + m);
            blue = toRgbComponent(m);
        } else if (huePrime > 1 && huePrime <= 2) {
            red = toRgbComponent(x + m);
            green = toRgbComponent(chroma + m);
            blue = toRgbComponent(m);
        } else if (huePrime > 2 && huePrime <= 3) {
            red = toRgbComponent(m);
            green = toRgbComponent(chroma + m);
            blue = toRgbComponent(x + m);
        } else if (huePrime > 3 && huePrime <= 4) {
            red = toRgbComponent(m);
            green = toRgbComponent(x + m);
            blue = toRgbComponent(chroma + m);
        } else if (huePrime > 4 && huePrime <= 5) {
            red = toRgbComponent(x + m);
            green = toRgbComponent(m);
            blue = toRgbComponent(chroma + m);
        } else {
            red = toRgbComponent(chroma + m);
            green = toRgbComponent(m);
            blue = toRgbComponent(x + m);
        }

        return new int[] {red, green, blue};
    }

    /**
     * Converts a normalized color component in [0, 1] to an integer in [0, 255].
     */
    private static int toRgbComponent(double component) {
        return (int) Math.round(255 * component);
    }
}