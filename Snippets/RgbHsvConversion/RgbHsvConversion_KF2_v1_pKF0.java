package com.thealgorithms.conversions;

import java.util.Arrays;

public final class RgbHsvConversion {

    private static final int MAX_RGB = 255;
    private static final int MIN_RGB = 0;
    private static final double MIN_HUE = 0.0;
    private static final double MAX_HUE = 360.0;
    private static final double MIN_SV = 0.0;
    private static final double MAX_SV = 1.0;

    private RgbHsvConversion() {
        // Utility class
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

    public static int[] hsvToRgb(double hue, double saturation, double value) {
        validateHue(hue);
        validateSaturationOrValue(saturation, "saturation");
        validateSaturationOrValue(value, "value");

        double chroma = value * saturation;
        double hueSection = hue / 60.0;
        double secondLargestComponent = chroma * (1 - Math.abs(hueSection % 2 - 1));
        double matchValue = value - chroma;

        return getRgbBySection(hueSection, chroma, matchValue, secondLargestComponent);
    }

    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRgbComponent(red, "red");
        validateRgbComponent(green, "green");
        validateRgbComponent(blue, "blue");

        double dRed = red / 255.0;
        double dGreen = green / 255.0;
        double dBlue = blue / 255.0;

        double value = Math.max(Math.max(dRed, dGreen), dBlue);
        double minComponent = Math.min(Math.min(dRed, dGreen), dBlue);
        double chroma = value - minComponent;

        double saturation = value == 0 ? 0 : chroma / value;
        double hue = calculateHue(dRed, dGreen, dBlue, value, chroma);

        return new double[] {hue, saturation, value};
    }

    private static double calculateHue(double dRed, double dGreen, double dBlue, double value, double chroma) {
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

    private static boolean approximatelyEqualHsv(double[] hsv1, double[] hsv2) {
        boolean hueClose = Math.abs(hsv1[0] - hsv2[0]) < 0.2;
        boolean saturationClose = Math.abs(hsv1[1] - hsv2[1]) < 0.002;
        boolean valueClose = Math.abs(hsv1[2] - hsv2[2]) < 0.002;

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

    private static void validateSaturationOrValue(double val, String name) {
        if (val < MIN_SV || val > MAX_SV) {
            throw new IllegalArgumentException(name + " should be between 0 and 1");
        }
    }

    private static void validateRgbComponent(int component, String name) {
        if (component < MIN_RGB || component > MAX_RGB) {
            throw new IllegalArgumentException(name + " should be between 0 and 255");
        }
    }
}