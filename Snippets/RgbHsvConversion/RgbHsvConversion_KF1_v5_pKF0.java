package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between HSV and RGB color spaces.
 */
public final class HsvToRgbConverter {

    private static final double MAX_HUE = 360.0;
    private static final double MIN_HUE = 0.0;
    private static final double MIN_SATURATION = 0.0;
    private static final double MAX_SATURATION = 1.0;
    private static final double MIN_VALUE = 0.0;
    private static final double MAX_VALUE = 1.0;

    private static final int MIN_RGB = 0;
    private static final int MAX_RGB = 255;

    private static final double HUE_TOLERANCE = 0.2;
    private static final double SV_TOLERANCE = 0.002;

    private HsvToRgbConverter() {
        // Utility class; prevent instantiation.
    }

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

        // RGB → HSV tests
        assert hsvAlmostEquals(rgbToHsv(0, 0, 0), new double[] {0, 0, 0});
        assert hsvAlmostEquals(rgbToHsv(255, 255, 255), new double[] {0, 0, 1});
        assert hsvAlmostEquals(rgbToHsv(255, 0, 0), new double[] {0, 1, 1});
        assert hsvAlmostEquals(rgbToHsv(255, 255, 0), new double[] {60, 1, 1});
        assert hsvAlmostEquals(rgbToHsv(0, 255, 0), new double[] {120, 1, 1});
        assert hsvAlmostEquals(rgbToHsv(0, 0, 255), new double[] {240, 1, 1});
        assert hsvAlmostEquals(rgbToHsv(255, 0, 255), new double[] {300, 1, 1});
        assert hsvAlmostEquals(rgbToHsv(64, 128, 128), new double[] {180, 0.5, 0.5});
        assert hsvAlmostEquals(rgbToHsv(193, 196, 224), new double[] {234, 0.14, 0.88});
        assert hsvAlmostEquals(rgbToHsv(128, 32, 80), new double[] {330, 0.75, 0.5});
    }

    /**
     * Converts HSV (Hue, Saturation, Value) to RGB.
     *
     * @param hue        hue in degrees, in the range [0, 360]
     * @param saturation saturation in the range [0, 1]
     * @param value      value (brightness) in the range [0, 1]
     * @return array of RGB components in the range [0, 255]
     */
    public static int[] hsvToRgb(double hue, double saturation, double value) {
        validateHue(hue);
        validateSaturation(saturation);
        validateValue(value);

        double chroma = value * saturation;
        double hueSector = hue / 60.0;
        double x = chroma * (1 - Math.abs(hueSector % 2 - 1));
        double m = value - chroma;

        return composeRgbFromChroma(hueSector, chroma, m, x);
    }

    /**
     * Converts RGB to HSV (Hue, Saturation, Value).
     *
     * @param red   red component in the range [0, 255]
     * @param green green component in the range [0, 255]
     * @param blue  blue component in the range [0, 255]
     * @return array {hue, saturation, value} where
     *         hue is in [0, 360], saturation and value are in [0, 1]
     */
    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRgbComponent(red, "red");
        validateRgbComponent(green, "green");
        validateRgbComponent(blue, "blue");

        double r = normalizeRgbComponent(red);
        double g = normalizeRgbComponent(green);
        double b = normalizeRgbComponent(blue);

        double max = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);
        double delta = max - min;

        double value = max;
        double saturation = (max == 0) ? 0 : delta / max;
        double hue = calculateHue(r, g, b, max, delta);

        return new double[] {hue, saturation, value};
    }

    private static double normalizeRgbComponent(int component) {
        return component / (double) MAX_RGB;
    }

    private static double calculateHue(double r, double g, double b, double max, double delta) {
        if (delta == 0) {
            return 0;
        }

        if (max == r) {
            return normalizeHue(60 * ((g - b) / delta));
        }
        if (max == g) {
            return normalizeHue(60 * (2 + (b - r) / delta));
        }
        return normalizeHue(60 * (4 + (r - g) / delta));
    }

    private static double normalizeHue(double hue) {
        return (hue + 360) % 360;
    }

    private static boolean hsvAlmostEquals(double[] actual, double[] expected) {
        return isClose(actual[0], expected[0], HUE_TOLERANCE)
            && isClose(actual[1], expected[1], SV_TOLERANCE)
            && isClose(actual[2], expected[2], SV_TOLERANCE);
    }

    private static boolean isClose(double a, double b, double tolerance) {
        return Math.abs(a - b) < tolerance;
    }

    private static int[] composeRgbFromChroma(double hueSector, double chroma, double m, double x) {
        double rPrime;
        double gPrime;
        double bPrime;

        if (hueSector >= 0 && hueSector <= 1) {
            rPrime = chroma;
            gPrime = x;
            bPrime = 0;
        } else if (hueSector <= 2) {
            rPrime = x;
            gPrime = chroma;
            bPrime = 0;
        } else if (hueSector <= 3) {
            rPrime = 0;
            gPrime = chroma;
            bPrime = x;
        } else if (hueSector <= 4) {
            rPrime = 0;
            gPrime = x;
            bPrime = chroma;
        } else if (hueSector <= 5) {
            rPrime = x;
            gPrime = 0;
            bPrime = chroma;
        } else {
            rPrime = chroma;
            gPrime = 0;
            bPrime = x;
        }

        int red = toRgbComponent(rPrime + m);
        int green = toRgbComponent(gPrime + m);
        int blue = toRgbComponent(bPrime + m);

        return new int[] {red, green, blue};
    }

    private static int toRgbComponent(double value) {
        return (int) Math.round(MAX_RGB * value);
    }

    private static void validateHue(double hue) {
        if (hue < MIN_HUE || hue > MAX_HUE) {
            throw new IllegalArgumentException("hue should be between 0 and 360");
        }
    }

    private static void validateSaturation(double saturation) {
        if (saturation < MIN_SATURATION || saturation > MAX_SATURATION) {
            throw new IllegalArgumentException("saturation should be between 0 and 1");
        }
    }

    private static void validateValue(double value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("value should be between 0 and 1");
        }
    }

    private static void validateRgbComponent(int component, String name) {
        if (component < MIN_RGB || component > MAX_RGB) {
            throw new IllegalArgumentException(name + " should be between 0 and 255");
        }
    }
}