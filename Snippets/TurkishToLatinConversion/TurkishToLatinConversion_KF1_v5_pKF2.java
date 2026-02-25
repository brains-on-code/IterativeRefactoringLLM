package com.thealgorithms.conversions;

/**
 * Utility class for converting Turkish-specific characters to their ASCII equivalents.
 */
public final class TurkishCharacterConverter {

    /**
     * Turkish-specific characters to be converted.
     */
    private static final char[] TURKISH_CHARS = {
        'ı',
        'İ',
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

    /**
     * ASCII equivalents for the characters in {@link #TURKISH_CHARS}.
     * The index of each character here matches the corresponding index in {@code TURKISH_CHARS}.
     */
    private static final char[] ASCII_REPLACEMENTS = {
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

    private TurkishCharacterConverter() {
        // Prevent instantiation of utility class.
    }

    /**
     * Replaces Turkish-specific characters in the given string with their ASCII equivalents.
     *
     * @param input the string to normalize
     * @return a new string with Turkish characters replaced by ASCII ones,
     *         or the original string if it is {@code null} or empty
     */
    public static String normalizeTurkishCharacters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String normalized = input;
        for (int i = 0; i < TURKISH_CHARS.length; i++) {
            normalized = normalized.replace(TURKISH_CHARS[i], ASCII_REPLACEMENTS[i]);
        }

        return normalized;
    }
}