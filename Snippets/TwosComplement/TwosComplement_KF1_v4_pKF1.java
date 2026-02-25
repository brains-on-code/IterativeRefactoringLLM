package com.thealgorithms.bitmanipulation;

/**
 * Utility class for computing the two's complement of a binary string.
 */
public final class BinaryTwosComplement {

    private BinaryTwosComplement() {
    }

    /**
     * Computes the two's complement of a given binary string.
     *
     * @param binaryInput a non-empty string consisting only of '0' and '1'
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input contains characters other than '0' or '1'
     */
    public static String computeTwosComplement(String binaryInput) {
        if (!binaryInput.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        StringBuilder onesComplement = new StringBuilder();
        for (char bit : binaryInput.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }

        StringBuilder twosComplement = new StringBuilder(onesComplement);
        boolean hasCarry = true;

        for (int index = onesComplement.length() - 1; index >= 0 && hasCarry; index--) {
            if (onesComplement.charAt(index) == '1') {
                twosComplement.setCharAt(index, '0');
            } else {
                twosComplement.setCharAt(index, '1');
                hasCarry = false;
            }
        }

        if (hasCarry) {
            twosComplement.insert(0, '1');
        }

        return twosComplement.toString();
    }
}