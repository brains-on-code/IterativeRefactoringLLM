package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * The RGB color model is an additive color model in which red, green, and blue
 * light are added together in various ways to reproduce a broad array of
 * colors. The name of the model comes from the initials of the three additive
 * primary colors, red, green, and blue. Meanwhile, the HSV representation
 * models how colors appear under light. In it, colors are represented using
 * three components: hue, saturation and (brightness-)value. This class provides
 * methods for converting colors from one representation to the other.
 * (description adapted from <a href="https://en.wikipedia.org/wiki/RGB_color_model">[1]</a> and
 * <a href="https://en.wikipedia.org/wiki/HSL_and_HSV">[2]</a>).
 */
public final class RgbHsvConversion {

    private static final int MIN_RGB = 0;
    private static final int MAX_RGB = 255;
    private static final double MIN_HUE = 0.0;
    private static final double MAX_HUE = 360.0;
    private static final double MIN_SV = 0.0;
    private static final double MAX_SV = 1.0;

    private static final double HUE_TOLERANCE = 0.2;
    private static final double SV_TOLERANCE = 0.002;

    private RgbHsvConversion() {
        // Utility class
    }

    public static void main(String[] args) {
        // Expected RGB-values taken from https://www.rapidtables.com/convert/color/hsv-to-rgb.html

        // Test hsvToRgb-method
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

        // Test rgbToHsv-method
        // approximate-assertions needed because of small deviations due to converting between
        // int-values and double-values.
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
     * Conversion from the HSV-representation to the RGB-representation.
     *
     * @param hue Hue of the color.
     * @param saturation Saturation of the color.
     * @param value Brightness-value of the color.
     * @return The tuple of RGB-components.
     */
    public static int[] hsvToRgb(double hue, double saturation, double value) {
        validateHue(hue);
        validateSaturation(saturation);
        validateValue(value);

        double chroma = value * saturation;
        double hueSection = hue / 60.0;
        double secondLargestComponent = chroma * (1 - Math.abs(hueSection % 2 - 1));
        double matchValue = value - chroma;

        return getRgbBySection(hueSection, chroma, matchValue, secondLargestComponent);
    }

    /**
     * Conversion from the RGB-representation to the HSV-representation.
     *
     * @param red Red-component of the color.
     * @param green Green-component of the color.
     * @param blue Blue-component of the color.
     * @return The tuple of HSV-components.
     */
    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRgbComponent(red, "red");
        validateRgbComponent(green, "green");
        validateRgbComponent(blue, "blue");

        double dRed = red / 255.0;
        double dGreen = green / 255.0;
        double dBlue = blue / 255.0;

        double max = Math.max(Math.max(dRed, dGreen), dBlue);
        double min = Math.min(Math.min(dRed, dGreen), dBlue);
        double chroma = max - min;

        double value = max;
        double saturation = (value == 0) ? 0 : chroma / value;
        double hue = calculateHue(dRed, dGreen, dBlue, max, chroma);

        return new double[] {hue, saturation, value};
    }

    private static double calculateHue(double red, double green, double blue, double max, double chroma) {
        if (chroma == 0) {
            return 0;
        }

        double hue;
        if (max == red) {
            hue = 60 * ((green - blue) / chroma);
        } else if (max == green) {
            hue = 60 * (2 + (blue - red) / chroma);
        } else {
            hue = 60 * (4 + (red - green) / chroma);
        }

        return (hue + 360) % 360;
    }

    private static boolean approximatelyEqualHsv(double[] hsv1, double[] hsv2) {
        boolean hueClose = Math.abs(hsv1[0] - hsv2[0]) < HUE_TOLERANCE;
        boolean saturationClose = Math.abs(hsv1[1] - hsv2[1]) < SV_TOLERANCE;
        boolean valueClose = Math.abs(hsv1[2] - hsv2[2]) < SV_TOLERANCE;

        return hueClose && saturationClose && valueClose;
    }

    private static int[] getRgbBySection(
            double hueSection, double chroma, double matchValue, double secondLargestComponent) {

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

    private static int toRgbComponent(double value) {
        return (int) Math.round(MAX_RGB * value);
    }

    private static void validateHue(double hue) {
        if (hue < MIN_HUE || hue > MAX_HUE) {
            throw new IllegalArgumentException("hue should be between 0 and 360");
        }
    }

    private static void validateSaturation(double saturation) {
        if (saturation < MIN_SV || saturation > MAX_SV) {
            throw new IllegalArgumentException("saturation should be between 0 and 1");
        }
    }

    private static void validateValue(double value) {
        if (value < MIN_SV || value > MAX_SV) {
            throw new IllegalArgumentException("value should be between 0 and 1");
        }
    }

    private static void validateRgbComponent(int component, String name) {
        if (component < MIN_RGB || component > MAX_RGB) {
            throw new IllegalArgumentException(name + " should be between 0 and 255");
        }
    }
}