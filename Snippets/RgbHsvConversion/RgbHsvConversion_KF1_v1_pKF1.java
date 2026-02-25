package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between HSV and RGB color spaces.
 */
public final class HsvRgbConverter {

    private HsvRgbConverter() {
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
     * Converts HSV (hue, saturation, value) to RGB.
     *
     * @param hue        hue in [0, 360]
     * @param saturation saturation in [0, 1]
     * @param value      value in [0, 1]
     * @return array {red, green, blue}, each in [0, 255]
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
        double hueSector = hue / 60;
        double secondComponent = chroma * (1 - Math.abs(hueSector % 2 - 1));
        double matchValue = value - chroma;

        return composeRgbFromChroma(hueSector, chroma, matchValue, secondComponent);
    }

    /**
     * Converts RGB (red, green, blue) to HSV.
     *
     * @param red   red in [0, 255]
     * @param green green in [0, 255]
     * @param blue  blue in [0, 255]
     * @return array {hue, saturation, value} where hue in [0, 360], saturation and value in [0, 1]
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

        double r = (double) red / 255;
        double g = (double) green / 255;
        double b = (double) blue / 255;

        double value = Math.max(Math.max(r, g), b);
        double min = Math.min(Math.min(r, g), b);
        double chroma = value - min;

        double saturation = value == 0 ? 0 : chroma / value;
        double hue;

        if (chroma == 0) {
            hue = 0;
        } else if (value == r) {
            hue = 60 * (0 + (g - b) / chroma);
        } else if (value == g) {
            hue = 60 * (2 + (b - r) / chroma);
        } else {
            hue = 60 * (4 + (r - g) / chroma);
        }

        hue = (hue + 360) % 360;

        return new double[] {hue, saturation, value};
    }

    private static boolean hsvAlmostEquals(double[] actual, double[] expected) {
        boolean hueClose = Math.abs(actual[0] - expected[0]) < 0.2;
        boolean saturationClose = Math.abs(actual[1] - expected[1]) < 0.002;
        boolean valueClose = Math.abs(actual[2] - expected[2]) < 0.002;

        return hueClose && saturationClose && valueClose;
    }

    private static int[] composeRgbFromChroma(
            double hueSector, double chroma, double matchValue, double secondComponent) {

        int red;
        int green;
        int blue;

        if (hueSector >= 0 && hueSector <= 1) {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(secondComponent + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSector > 1 && hueSector <= 2) {
            red = toRgbComponent(secondComponent + matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSector > 2 && hueSector <= 3) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(secondComponent + matchValue);
        } else if (hueSector > 3 && hueSector <= 4) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(secondComponent + matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else if (hueSector > 4 && hueSector <= 5) {
            red = toRgbComponent(secondComponent + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(secondComponent + matchValue);
        }

        return new int[] {red, green, blue};
    }

    private static int toRgbComponent(double value) {
        return (int) Math.round(255 * value);
    }
}