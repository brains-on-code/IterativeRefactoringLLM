package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 *
 * <p>Provides methods to convert:
 * <ul>
 *   <li>Hexadecimal string to decimal integer</li>
 *   <li>Decimal integer to octal integer (as a base-10 representation)</li>
 *   <li>Hexadecimal string directly to octal integer (via decimal)</li>
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string (case-insensitive, digits 0-9 and A-F)
     * @return the decimal integer representation of the given hexadecimal string
     * @throws IllegalArgumentException if the string contains invalid hexadecimal characters
     */
    public static int method1(String hexString) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        hexString = hexString.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < hexString.length(); i++) {
            char currentChar = hexString.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(currentChar);

            if (digitValue == -1) {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + currentChar);
            }

            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its octal representation,
     * returned as an integer whose digits are in base 10.
     *
     * <p>Example: input {@code 10} (decimal) returns {@code 12} (octal).
     *
     * @param decimal the decimal integer to convert
     * @return the octal representation as an integer
     */
    public static int method2(int decimal) {
        int octalValue = 0;
        int place = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octalValue += remainder * place;
            decimal /= 8;
            place *= 10;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal string directly to its octal representation,
     * returned as an integer whose digits are in base 10.
     *
     * <p>This is equivalent to:
     * <pre>
     *   int decimal = method1(hexString);
     *   return method2(decimal);
     * </pre>
     *
     * @param hexString the hexadecimal string to convert
     * @return the octal representation as an integer
     * @throws IllegalArgumentException if the string contains invalid hexadecimal characters
     */
    public static int method3(String hexString) {
        int decimalValue = method1(hexString);
        return method2(decimalValue);
    }
}