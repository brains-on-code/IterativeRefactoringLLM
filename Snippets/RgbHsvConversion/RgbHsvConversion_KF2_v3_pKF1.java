package com.thealgorithms.conversions;

import java.util.Arrays;

public final class RgbHsvConversion {

    private RgbHsvConversion() {}

    public static void main(String[] args) {

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

    public static int[] hsvToRgb(double hueDegrees, double saturation, double value) {
        validateHue(hueDegrees);
        validateSaturation(saturation);
        validateValue(value);

        double chroma = value * saturation;
        double hueSector = hueDegrees / 60;
        double secondaryComponent = chroma * (1 - Math.abs(hueSector % 2 - 1));
        double matchValue = value - chroma;

        return convertHueSectorToRgb(hueSector, chroma, matchValue, secondaryComponent);
    }

    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRed(red);
        validateGreen(green);
        validateBlue(blue);

        double normalizedRed = (double) red / 255;
        double normalizedGreen = (double) green / 255;
        double normalizedBlue = (double) blue / 255;

        double value = Math.max(Math.max(normalizedRed, normalizedGreen), normalizedBlue);
        double minComponent = Math.min(Math.min(normalizedRed, normalizedGreen), normalizedBlue);
        double chroma = value - minComponent;

        double saturation = value == 0 ? 0 : chroma / value;
        double hueDegrees;

        if (chroma == 0) {
            hueDegrees = 0;
        } else if (value == normalizedRed) {
            hueDegrees = 60 * (0 + (normalizedGreen - normalizedBlue) / chroma);
        } else if (value == normalizedGreen) {
            hueDegrees = 60 * (2 + (normalizedBlue - normalizedRed) / chroma);
        } else {
            hueDegrees = 60 * (4 + (normalizedRed - normalizedGreen) / chroma);
        }

        hueDegrees = (hueDegrees + 360) % 360;

        return new double[] {hueDegrees, saturation, value};
    }

    private static boolean approximatelyEqualHsv(double[] hsv1, double[] hsv2) {
        boolean hueClose = Math.abs(hsv1[0] - hsv2[0]) < 0.2;
        boolean saturationClose = Math.abs(hsv1[1] - hsv2[1]) < 0.002;
        boolean valueClose = Math.abs(hsv1[2] - hsv2[2]) < 0.002;

        return hueClose && saturationClose && valueClose;
    }

    private static int[] convertHueSectorToRgb(
            double hueSector, double chroma, double matchValue, double secondaryComponent) {

        int red;
        int green;
        int blue;

        if (hueSector >= 0 && hueSector <= 1) {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(secondaryComponent + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSector > 1 && hueSector <= 2) {
            red = toRgbComponent(secondaryComponent + matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSector > 2 && hueSector <= 3) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(secondaryComponent + matchValue);
        } else if (hueSector > 3 && hueSector <= 4) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(secondaryComponent + matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else if (hueSector > 4 && hueSector <= 5) {
            red = toRgbComponent(secondaryComponent + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(secondaryComponent + matchValue);
        }

        return new int[] {red, green, blue};
    }

    private static int toRgbComponent(double normalizedComponent) {
        return (int) Math.round(255 * normalizedComponent);
    }

    private static void validateHue(double hueDegrees) {
        if (hueDegrees < 0 || hueDegrees > 360) {
            throw new IllegalArgumentException("hue should be between 0 and 360");
        }
    }

    private static void validateSaturation(double saturation) {
        if (saturation < 0 || saturation > 1) {
            throw new IllegalArgumentException("saturation should be between 0 and 1");
        }
    }

    private static void validateValue(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("value should be between 0 and 1");
        }
    }

    private static void validateRed(int red) {
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("red should be between 0 and 255");
        }
    }

    private static void validateGreen(int green) {
        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("green should be between 0 and 255");
        }
    }

    private static void validateBlue(int blue) {
        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("blue should be between 0 and 255");
        }
    }
}