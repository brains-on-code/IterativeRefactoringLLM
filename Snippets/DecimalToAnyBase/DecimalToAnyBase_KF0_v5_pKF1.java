package com.thealgorithms.conversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides methods to convert a decimal number to a string representation
 * in any specified base between 2 and 36.
 *
 * @author Varun Upadhyay (<a href="https://github.com/varunu28">...</a>)
 */
public final class DecimalToAnyBase {
    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char ZERO_CHAR = '0';
    private static final char FIRST_LETTER_CHAR = 'A';
    private static final int FIRST_LETTER_DIGIT_VALUE = 10;

    private DecimalToAnyBase() {
    }

    /**
     * Converts a decimal number to a string representation in the specified base.
     * For example, converting the decimal number 10 to base 2 would return "1010".
     *
     * @param decimalNumber the decimal number to convert
     * @param targetBase    the base to convert to (must be between {@value #MIN_BASE} and {@value #MAX_BASE})
     * @return the string representation of the number in the specified base
     * @throws IllegalArgumentException if the base is out of the supported range
     */
    public static String convertToAnyBase(int decimalNumber, int targetBase) {
        if (targetBase < MIN_BASE || targetBase > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between " + MIN_BASE + " and " + MAX_BASE);
        }

        if (decimalNumber == 0) {
            return String.valueOf(ZERO_CHAR);
        }

        List<Character> baseDigits = new ArrayList<>();
        int remainingValue = decimalNumber;

        while (remainingValue > 0) {
            int remainder = remainingValue % targetBase;
            baseDigits.add(toBaseDigitChar(remainder));
            remainingValue /= targetBase;
        }

        StringBuilder convertedNumber = new StringBuilder(baseDigits.size());
        for (int index = baseDigits.size() - 1; index >= 0; index--) {
            convertedNumber.append(baseDigits.get(index));
        }

        return convertedNumber.toString();
    }

    /**
     * Converts an integer digit value to its corresponding character in the specified base.
     * This method is used to convert values from 0 to 35 into their appropriate character representation.
     * For example, 0-9 are represented as '0'-'9', and 10-35 are represented as 'A'-'Z'.
     *
     * @param digitValue the integer digit value to convert (should be less than the base value)
     * @return the character representing the digit value in the specified base
     */
    private static char toBaseDigitChar(int digitValue) {
        if (digitValue >= 0 && digitValue <= 9) {
            return (char) (ZERO_CHAR + digitValue);
        } else {
            return (char) (FIRST_LETTER_CHAR + digitValue - FIRST_LETTER_DIGIT_VALUE);
        }
    }
}