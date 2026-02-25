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

    private RgbHsvConversion() {
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
        double intermediateComponent = chroma * (1 - Math.abs(hueSector % 2 - 1));
        double matchComponent = value - chroma;

        return getRgbByHueSector(hueSector, chroma, matchComponent, intermediateComponent);
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
        if (red < 0 || red > 255) {
            throw new IllegalArgumentException("red should be between 0 and 255");
        }

        if (green < 0 || green > 255) {
            throw new IllegalArgumentException("green should be between 0 and 255");
        }

        if (blue < 0 || blue > 255) {
            throw new IllegalArgumentException("blue should be between 0 and 255");
        }

        double normalizedRed = (double) red / 255;
        double normalizedGreen = (double) green / 255;
        double normalizedBlue = (double) blue / 255;

        double value = Math.max(Math.max(normalizedRed, normalizedGreen), normalizedBlue);
        double minComponent = Math.min(Math.min(normalizedRed, normalizedGreen), normalizedBlue);
        double chroma = value - minComponent;
        double saturation = value == 0 ? 0 : chroma / value;
        double hue;

        if (chroma == 0) {
            hue = 0;
        } else if (value == normalizedRed) {
            hue = 60 * (0 + (normalizedGreen - normalizedBlue) / chroma);
        } else if (value == normalizedGreen) {
            hue = 60 * (2 + (normalizedBlue - normalizedRed) / chroma);
        } else {
            hue = 60 * (4 + (normalizedRed - normalizedGreen) / chroma);
        }

        hue = (hue + 360) % 360;

        return new double[] {hue, saturation, value};
    }

    private static boolean approximatelyEqualHsv(double[] hsvColor1, double[] hsvColor2) {
        boolean hueCloseEnough = Math.abs(hsvColor1[0] - hsvColor2[0]) < 0.2;
        boolean saturationCloseEnough = Math.abs(hsvColor1[1] - hsvColor2[1]) < 0.002;
        boolean valueCloseEnough = Math.abs(hsvColor1[2] - hsvColor2[2]) < 0.002;

        return hueCloseEnough && saturationCloseEnough && valueCloseEnough;
    }

    private static int[] getRgbByHueSector(
            double hueSector,
            double chroma,
            double matchComponent,
            double intermediateComponent
    ) {
        int red;
        int green;
        int blue;

        if (hueSector >= 0 && hueSector <= 1) {
            red = toRgbComponent(chroma + matchComponent);
            green = toRgbComponent(intermediateComponent + matchComponent);
            blue = toRgbComponent(matchComponent);
        } else if (hueSector > 1 && hueSector <= 2) {
            red = toRgbComponent(intermediateComponent + matchComponent);
            green = toRgbComponent(chroma + matchComponent);
            blue = toRgbComponent(matchComponent);
        } else if (hueSector > 2 && hueSector <= 3) {
            red = toRgbComponent(matchComponent);
            green = toRgbComponent(chroma + matchComponent);
            blue = toRgbComponent(intermediateComponent + matchComponent);
        } else if (hueSector > 3 && hueSector <= 4) {
            red = toRgbComponent(matchComponent);
            green = toRgbComponent(intermediateComponent + matchComponent);
            blue = toRgbComponent(chroma + matchComponent);
        } else if (hueSector > 4 && hueSector <= 5) {
            red = toRgbComponent(intermediateComponent + matchComponent);
            green = toRgbComponent(matchComponent);
            blue = toRgbComponent(chroma + matchComponent);
        } else {
            red = toRgbComponent(chroma + matchComponent);
            green = toRgbComponent(matchComponent);
            blue = toRgbComponent(intermediateComponent + matchComponent);
        }

        return new int[] {red, green, blue};
    }

    private static int toRgbComponent(double normalizedComponent) {
        return (int) Math.round(255 * normalizedComponent);
    }
}