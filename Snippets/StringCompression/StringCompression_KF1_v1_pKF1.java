package com.thealgorithms.strings;

/**
 * Utility class for run-length encoding of strings.
 */
public final class RunLengthEncoding {

    private RunLengthEncoding() {
    }

    /**
     * Encodes the input string using run-length encoding.
     *
     * @param input the string to encode
     * @return the run-length encoded string
     */
    public static String encode(String input) {
        int count = 1;
        String encoded = "";

        if (input.length() == 1) {
            return "" + input.charAt(0);
        }

        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                count = count + 1;
            }
            if ((i + 1) == input.length() - 1 && input.charAt(i + 1) == input.charAt(i)) {
                encoded = appendRun(encoded, count, input.charAt(i));
                break;
            } else if (input.charAt(i) != input.charAt(i + 1)) {
                if ((i + 1) == input.length() - 1) {
                    encoded = appendRun(encoded, count, input.charAt(i)) + input.charAt(i + 1);
                    break;
                } else {
                    encoded = appendRun(encoded, count, input.charAt(i));
                    count = 1;
                }
            }
        }
        return encoded;
    }

    /**
     * Appends a run of characters to the encoded string.
     *
     * @param currentEncoded the current encoded string
     * @param count          the number of times the character repeats
     * @param character      the character being repeated
     * @return the updated encoded string
     */
    public static String appendRun(String currentEncoded, int count, char character) {
        if (count > 1) {
            currentEncoded += character + "" + count;
        } else {
            currentEncoded += character + "";
        }
        return currentEncoded;
    }
}