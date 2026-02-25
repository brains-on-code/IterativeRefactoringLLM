package com.thealgorithms.bitmanipulation;

/**
 * Utility class for operations on binary strings.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * <p>The input must be a non-empty string consisting only of the characters
     * '0' and '1'. The method returns the two's complement representation of
     * the input, preserving the bit-width unless an overflow requires an
     * additional leading bit.</p>
     *
     * <p>Example:
     * <ul>
     *   <li>"0"   -> "0"</li>
     *   <li>"1"   -> "1"</li>
     *   <li>"01"  -> "11"</li>
     *   <li>"0001"-> "1111"</li>
     * </ul>
     * </p>
     *
     * @param binaryString the input binary string
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input contains characters other than '0' or '1'
     */
    public static String method1(String binaryString) {
        if (!binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        // First, compute the one's complement (invert all bits)
        StringBuilder onesComplement = new StringBuilder();
        for (char bit : binaryString.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }

        // Then, add 1 to the one's complement to get the two's complement
        StringBuilder twosComplement = new StringBuilder(onesComplement);
        boolean carry = true;

        for (int i = onesComplement.length() - 1; i >= 0 && carry; i--) {
            if (onesComplement.charAt(i) == '1') {
                twosComplement.setCharAt(i, '0');
            } else {
                twosComplement.setCharAt(i, '1');
                carry = false;
            }
        }

        // If there is still a carry, prepend '1'
        if (carry) {
            twosComplement.insert(0, '1');
        }

        return twosComplement.toString();
    }
}