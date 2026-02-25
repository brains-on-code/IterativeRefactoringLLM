package com.thealgorithms.conversions;

import java.util.Arrays;

/**
 * Utility class for converting between RGB and HSV color spaces.
 *
 * <p>RGB components are in the range [0, 255].</p>
 * <p>HSV components are:
 * <ul>
 *   <li>Hue in degrees: [0, 360]</li>
 *   <li>Saturation: [0, 1]</li>
 *   <li>Value (brightness): [0, 1]</li>
 * </ul>
 * </p>
 */
public final class RgbHsvConversion {

    private static final double HUE_TOLERANCE = 0.2;
    private static final double SATURATION_TOLERANCE = 0.002;
    private static final double VALUE_TOLERANCE = 0.002;

    private RgbHsvConversion() {
        // Prevent instantiation.
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

        // RGB → HSV tests (with tolerance for floating-point comparisons)
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
     * Converts an HSV color to its RGB representation.
     *
     * @param hue        hue component in degrees [0, 360]
     * @param saturation saturation component [0, 1]
     * @param value      value (brightness) component [0, 1]
     * @return array of RGB components [red, green, blue], each in [0, 255]
     * @throws IllegalArgumentException if any component is out of range
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
     * Converts an RGB color to its HSV representation.
     *
     * @param red   red component [0, 255]
     * @param green green component [0, 255]
     * @param blue  blue component [0, 255]
     * @return array of HSV components [hue, saturation, value] where:
     *         hue in [0, 360], saturation and value in [0, 1]
     * @throws IllegalArgumentException if any component is out of range
     */
    public static double[] rgbToHsv(int red, int green, int blue) {
        validateRed(red);
        validateGreen(green);
        validateBlue(blue);

        double dRed = red / 255.0;
        double dGreen = green / 255.0;
        double dBlue = blue / 255.0;

        double value = Math.max(Math.max(dRed, dGreen), dBlue);
        double minComponent = Math.min(Math.min(dRed, dGreen), dBlue);
        double chroma = value - minComponent;

        double saturation = value == 0 ? 0 : chroma / value;
        double hue;

        if (chroma == 0) {
            hue = 0;
        } else if (value == dRed) {
            hue = 60 * ((dGreen - dBlue) / chroma);
        } else if (value == dGreen) {
            hue = 60 * (2 + (dBlue - dRed) / chroma);
        } else {
            hue = 60 * (4 + (dRed - dGreen) / chroma);
        }

        hue = (hue + 360) % 360;

        return new double[] {hue, saturation, value};
    }

    /**
     * Compares two HSV colors with a small tolerance for floating-point errors.
     *
     * @param hsv1 first HSV color [h, s, v]
     * @param hsv2 second HSV color [h, s, v]
     * @return true if all components are approximately equal
     */
    private static boolean approximatelyEqualHsv(double[] hsv1, double[] hsv2) {
        boolean hueClose = Math.abs(hsv1[0] - hsv2[0]) < HUE_TOLERANCE;
        boolean saturationClose = Math.abs(hsv1[1] - hsv2[1]) < SATURATION_TOLERANCE;
        boolean valueClose = Math.abs(hsv1[2] - hsv2[2]) < VALUE_TOLERANCE;

        return hueClose && saturationClose && valueClose;
    }

    /**
     * Determines the RGB components based on the hue section.
     *
     * @param hueSection             hue divided by 60 (identifies the sector of the color wheel)
     * @param chroma                 chroma component
     * @param matchValue             value offset added to each component
     * @param secondLargestComponent second-largest RGB component before adding matchValue
     * @return array of RGB components [red, green, blue], each in [0, 255]
     */
    private static int[] getRgbBySection(
            double hueSection,
            double chroma,
            double matchValue,
            double secondLargestComponent
    ) {
        int red;
        int green;
        int blue;

        if (hueSection >= 0 && hueSection <= 1) {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(secondLargestComponent + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSection > 1 && hueSection <= 2) {
            red = toRgbComponent(secondLargestComponent + matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(matchValue);
        } else if (hueSection > 2 && hueSection <= 3) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(chroma + matchValue);
            blue = toRgbComponent(secondLargestComponent + matchValue);
        } else if (hueSection > 3 && hueSection <= 4) {
            red = toRgbComponent(matchValue);
            green = toRgbComponent(secondLargestComponent + matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else if (hueSection > 4 && hueSection <= 5) {
            red = toRgbComponent(secondLargestComponent + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(chroma + matchValue);
        } else {
            red = toRgbComponent(chroma + matchValue);
            green = toRgbComponent(matchValue);
            blue = toRgbComponent(secondLargestComponent + matchValue);
        }

        return new int[] {red, green, blue};
    }

    /**
     * Converts a normalized color component in [0, 1] to an integer RGB component in [0, 255].
     *
     * @param input normalized component
     * @return integer RGB component
     */
    private static int toRgbComponent(double input) {
        return (int) Math.round(255 * input);
    }

    private static void validateHue(double hue) {
        if (hue < 0 || hue > 360) {
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