package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between HSV and RGB color spaces.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Simple self-test for the HSV/RGB conversion methods.
     *
     * @param args ignored
     */
    public static void method1(String[] args) {
        // HSV → RGB tests
        assert Arrays.equals(method2(0, 0, 0), new int[] {0, 0, 0});
        assert Arrays.equals(method2(0, 0, 1), new int[] {255, 255, 255});
        assert Arrays.equals(method2(0, 1, 1), new int[] {255, 0, 0});
        assert Arrays.equals(method2(60, 1, 1), new int[] {255, 255, 0});
        assert Arrays.equals(method2(120, 1, 1), new int[] {0, 255, 0});
        assert Arrays.equals(method2(240, 1, 1), new int[] {0, 0, 255});
        assert Arrays.equals(method2(300, 1, 1), new int[] {255, 0, 255});
        assert Arrays.equals(method2(180, 0.5, 0.5), new int[] {64, 128, 128});
        assert Arrays.equals(method2(234, 0.14, 0.88), new int[] {193, 196, 224});
        assert Arrays.equals(method2(330, 0.75, 0.5), new int[] {128, 32, 80});

        // RGB → HSV tests (with tolerance)
        assert method4(method3(0, 0, 0), new double[] {0, 0, 0});
        assert method4(method3(255, 255, 255), new double[] {0, 0, 1});
        assert method4(method3(255, 0, 0), new double[] {0, 1, 1});
        assert method4(method3(255, 255, 0), new double[] {60, 1, 1});
        assert method4(method3(0, 255, 0), new double[] {120, 1, 1});
        assert method4(method3(0, 0, 255), new double[] {240, 1, 1});
        assert method4(method3(255, 0, 255), new double[] {300, 1, 1});
        assert method4(method3(64, 128, 128), new double[] {180, 0.5, 0.5});
        assert method4(method3(193, 196, 224), new double[] {234, 0.14, 0.88});
        assert method4(method3(128, 32, 80), new double[] {330, 0.75, 0.5});
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
    public static int[] method2(double hue, double saturation, double value) {
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
        double huePrime = hue / 60;
        double x = chroma * (1 - Math.abs(huePrime % 2 - 1));
        double m = value - chroma;

        return method5(huePrime, chroma, m, x);
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
    public static double[] method3(int red, int green, int blue) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("red should be between 0 and 255");
        }

        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("green should be between 0 and 255");
        }

        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("blue should be between 0 and 255");
        }

        double r = (double) red / 255;
        double g = (double) green / 255;
        double b = (double) blue / 255;

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);
        double delta = max - min;

        double value = max;
        double saturation = max == 0 ? 0 : delta / max;
        double hue;

        if (delta == 0) {
            hue = 0;
        } else if (max == r) {
            hue = 60 * (0 + (g - b) / delta);
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
    private static boolean method4(double[] actual, double[] expected) {
        boolean hueClose = Math.abs(actual[0] - expected[0]) < 0.2;
        boolean saturationClose = Math.abs(actual[1] - expected[1]) < 0.002;
        boolean valueClose = Math.abs(actual[2] - expected[2]) < 0.002;

        return hueClose && saturationClose && valueClose;
    }

    /**
     * Helper for HSV → RGB conversion that maps the intermediate values
     * to the correct RGB components based on the hue sector.
     */
    private static int[] method5(double huePrime, double chroma, double m, double x) {
        int red;
        int green;
        int blue;

        if (huePrime >= 0 && huePrime <= 1) {
            red = method6(chroma + m);
            green = method6(x + m);
            blue = method6(m);
        } else if (huePrime > 1 && huePrime <= 2) {
            red = method6(x + m);
            green = method6(chroma + m);
            blue = method6(m);
        } else if (huePrime > 2 && huePrime <= 3) {
            red = method6(m);
            green = method6(chroma + m);
            blue = method6(x + m);
        } else if (huePrime > 3 && huePrime <= 4) {
            red = method6(m);
            green = method6(x + m);
            blue = method6(chroma + m);
        } else if (huePrime > 4 && huePrime <= 5) {
            red = method6(x + m);
            green = method6(m);
            blue = method6(chroma + m);
        } else {
            red = method6(chroma + m);
            green = method6(m);
            blue = method6(x + m);
        }

        return new int[] {red, green, blue};
    }

    /**
     * Converts a normalized color component in [0, 1] to an integer in [0, 255].
     */
    private static int method6(double component) {
        return (int) Math.round(255 * component);
    }
}