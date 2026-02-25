package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between RGB and HSV color models.
 *
 * <p>RGB (Red, Green, Blue) is an additive color model where colors are created
 * by combining light of these three primary colors. HSV (Hue, Saturation,
 * Value) represents colors in terms of their shade (hue), intensity
 * (saturation), and brightness (value).
 *
 * <p>Descriptions adapted from:
 * <a href="https://en.wikipedia.org/wiki/RGB_color_model">RGB color model</a> and
 * <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">HSL and HSV</a>.
 */
public final class RgbHsvConversion {

    private static final double HUE_TOLERANCE = 0.2;
    private static final double SATURATION_TOLERANCE = 0.002;
    private static final double VALUE_TOLERANCE = 0.002;

    private RgbHsvConversion() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        // HSV → RGB tests (expected values from RapidTables)
        // https://www.rapidtables.com/convert/color/hsv-to-rgb.html
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

        // RGB → HSV tests (approximate comparison due to rounding)
        assert approximatelyEqualHsv(rgbToHsv(0, 0, 0), new double[] {0, 0, 0});
        assert approximatelyEqualHsv(rgbToHsv(255, 255, 255), new double[] {0, 0, 1});
        assert approximatelyEqualHsv(rgbToHsv(255, 0, 0), new double[] {0, 1, 1});
        assert approximatelyEqualHsv(rgbToHsv(255, 255, 0), new double[] {60, 1, 1});
        assert approximatelyEqualHsv(rgbToHsv(0, 255, 0), new double[] {120, 1, 1});
        assert approximatelyEqualHsv(rgbToHsv(0, 0, 255), new double[] {240, 1, 1});
        assert approximatelyEqualHsv(rgbToHsv(255, 0, 255), new double[] {300, 1, 1});
        assert approximatelyEqualHsv(rgbToHsv(64, 128, 128), new double[] {180, 0.5, 0.5});
        assert approximatelyEqualHsv(rgbToHsv(193, 196, 224), new double[] {234, 0.14, 0.88});
        assert approximatelyEqualHsv(rgbToHsv(128, 32, 80), new double[] {330, 0.75, 0.5});
    }

    /**
     * Converts a color from HSV to RGB.
     *
     * @param hue        hue component in degrees, in the range [0, 360]
     * @param saturation saturation component, in the range [0, 1]
     * @param value      value (brightness) component, in the range [0, 1]
     * @return array of RGB components in the range [0, 255]: [red, green, blue]
     * @throws IllegalArgumentException if any component is out of range
     */
    public static int[] hsvToRgb(double hue, double saturation, double value) {
        validateHue(hue);
        validateUnitInterval("saturation", saturation);
        validateUnitInterval("value", value);

        double chroma = value * saturation;
        double hueSection = hue / 60.0;
        double secondLargestComponent = chroma * (1 - Math.abs(hueSection % 2 - 1));
        double matchValue = value - chroma;

        return getRgbBySection(hueSection, chroma, matchValue, secondLargestComponent);
    }

    /**
     * Converts a color from RGB to HSV.
     *
     * @param red   red component in the range [0, 255]
     * @param green green component in the range [0, 255]
     * @param blue  blue component in the range [0, 255]
     * @return array of HSV components: [hue (0–360), saturation (0–1), value (0–1)]
     * @throws IllegalArgumentException if any component is out of range
     */
    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRgbComponent("red", red);
        validateRgbComponent("green", green);
        validateRgbComponent("blue", blue);

        double dRed = red / 255.0;
        double dGreen = green / 255.0;
        double dBlue = blue / 255.0;

        double value = Math.max(Math.max(dRed, dGreen), dBlue);
        double minComponent = Math.min(Math.min(dRed, dGreen), dBlue);
        double chroma = value - minComponent;

        double saturation = value == 0 ? 0 : chroma / value;
        double hue = computeHue(dRed, dGreen, dBlue, value, chroma);

        return new double[] {hue, saturation, value};
    }

    /**
     * Compares two HSV colors with a small tolerance for each component.
     *
     * @param hsv1 first HSV color [h, s, v]
     * @param hsv2 second HSV color [h, s, v]
     * @return true if all components are within their respective tolerances
     */
    private static boolean approximatelyEqualHsv(double[] hsv1, double[] hsv2) {
        boolean hueClose = Math.abs(hsv1[0] - hsv2[0]) < HUE_TOLERANCE;
        boolean saturationClose = Math.abs(hsv1[1] - hsv2[1]) < SATURATION_TOLERANCE;
        boolean valueClose = Math.abs(hsv1[2] - hsv2[2]) < VALUE_TOLERANCE;

        return hueClose && saturationClose && valueClose;
    }

    /**
     * Determines RGB components based on the hue section.
     *
     * @param hueSection             hue divided by 60 (i.e., which sixth of the color wheel)
     * @param chroma                 chroma component
     * @param matchValue             value minus chroma
     * @param secondLargestComponent second-largest RGB component for this hue section
     * @return array of RGB components in the range [0, 255]
     */
    private static int[] getRgbBySection(
            double hueSection,
            double chroma,
            double matchValue,
            double secondLargestComponent
    ) {
        double rPrime;
        double gPrime;
        double bPrime;

        if (hueSection >= 0 && hueSection <= 1) {
            rPrime = chroma;
            gPrime = secondLargestComponent;
            bPrime = 0;
        } else if (hueSection > 1 && hueSection <= 2) {
            rPrime = secondLargestComponent;
            gPrime = chroma;
            bPrime = 0;
        } else if (hueSection > 2 && hueSection <= 3) {
            rPrime = 0;
            gPrime = chroma;
            bPrime = secondLargestComponent;
        } else if (hueSection > 3 && hueSection <= 4) {
            rPrime = 0;
            gPrime = secondLargestComponent;
            bPrime = chroma;
        } else if (hueSection > 4 && hueSection <= 5) {
            rPrime = secondLargestComponent;
            gPrime = 0;
            bPrime = chroma;
        } else {
            rPrime = chroma;
            gPrime = 0;
            bPrime = secondLargestComponent;
        }

        int red = toRgbComponent(rPrime + matchValue);
        int green = toRgbComponent(gPrime + matchValue);
        int blue = toRgbComponent(bPrime + matchValue);

        return new int[] {red, green, blue};
    }

    /**
     * Converts a normalized component in [0, 1] to an RGB component in [0, 255].
     */
    private static int toRgbComponent(double normalized) {
        return (int) Math.round(255 * normalized);
    }

    /**
     * Computes the hue component for an RGB color in HSV space.
     *
     * @param dRed   normalized red component [0, 1]
     * @param dGreen normalized green component [0, 1]
     * @param dBlue  normalized blue component [0, 1]
     * @param value  maximum of the three components
     * @param chroma difference between max and min components
     * @return hue in degrees [0, 360)
     */
    private static double computeHue(
            double dRed,
            double dGreen,
            double dBlue,
            double value,
            double chroma
    ) {
        if (chroma == 0) {
            return 0;
        }

        double hue;
        if (value == dRed) {
            hue = 60 * ((dGreen - dBlue) / chroma);
        } else if (value == dGreen) {
            hue = 60 * (2 + (dBlue - dRed) / chroma);
        } else {
            hue = 60 * (4 + (dRed - dGreen) / chroma);
        }

        return (hue + 360) % 360;
    }

    /**
     * Validates that a hue value is within [0, 360].
     *
     * @param hue hue value to validate
     * @throws IllegalArgumentException if the hue is out of range
     */
    private static void validateHue(double hue) {
        if (hue < 0 || hue > 360) {
            throw new IllegalArgumentException("hue should be between 0 and 360");
        }
    }

    /**
     * Validates that a value lies within the unit interval [0, 1].
     *
     * @param name  name of the parameter (for error messages)
     * @param value value to validate
     * @throws IllegalArgumentException if the value is out of range
     */
    private static void validateUnitInterval(String name, double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException(name + " should be between 0 and 1");
        }
    }

    /**
     * Validates that an RGB component is within [0, 255].
     *
     * @param name  name of the component (for error messages)
     * @param value component value to validate
     * @throws IllegalArgumentException if the component is out of range
     */
    private static void validateRgbComponent(String name, int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException(name + " should be between 0 and 255");
        }
    }
}