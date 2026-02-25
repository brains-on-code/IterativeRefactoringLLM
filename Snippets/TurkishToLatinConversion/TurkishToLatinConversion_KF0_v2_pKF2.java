package com.thealgorithms.conversions;

/**
 * Utility class for converting Turkish characters to their Latin equivalents.
 */
public final class TurkishToLatinConversion {

    /** Mapping of Turkish-specific characters. */
    private static final char[] TURKISH_CHARS = {
        'ı', // dotless i
        'İ', // dotted I
        'ü',
        'Ü',
        'ö',
        'Ö',
        'ş',
        'Ş',
        'ç',
        'Ç',
        'ğ',
        'Ğ'
    };

    /** Corresponding Latin replacements for {@link #TURKISH_CHARS}. */
    private static final char[] LATIN_CHARS = {
        'i',
        'I',
        'u',
        'U',
        'o',
        'O',
        's',
        'S',
        'c',
        'C',
        'g',
        'G'
    };

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation.
    }

    /**
     * Replaces all Turkish-specific characters in the given string with their
     * closest Latin equivalents.
     *
     * @param input the string that may contain Turkish characters
     * @return a new string with Turkish characters replaced by Latin characters;
     *         returns {@code null} if {@code input} is {@code null}
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String result = input;
        for (int i = 0; i < TURKISH_CHARS.length; i++) {
            result = result.replace(TURKISH_CHARS[i], LATIN_CHARS[i]);
        }

        return result;
    }
}