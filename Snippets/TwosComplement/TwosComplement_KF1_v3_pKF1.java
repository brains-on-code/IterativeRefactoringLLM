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

        StringBuilder onesComplementBuilder = new StringBuilder();
        for (char bit : binaryInput.toCharArray()) {
            onesComplementBuilder.append(bit == '0' ? '1' : '0');
        }

        StringBuilder twosComplementBuilder = new StringBuilder(onesComplementBuilder);
        boolean carry = true;

        for (int position = onesComplementBuilder.length() - 1; position >= 0 && carry; position--) {
            if (onesComplementBuilder.charAt(position) == '1') {
                twosComplementBuilder.setCharAt(position, '0');
            } else {
                twosComplementBuilder.setCharAt(position, '1');
                carry = false;
            }
        }

        if (carry) {
            twosComplementBuilder.insert(0, '1');
        }

        return twosComplementBuilder.toString();
    }
}