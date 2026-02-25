package com.thealgorithms.conversions;

/**
 * Utility class for converting Turkish-specific characters to their ASCII equivalents.
 */
public final class TurkishCharacterConverter {

    /**
     * Turkish-specific characters to be normalized.
     */
    private static final char[] TURKISH_CHARS = {
        'ı', // LATIN SMALL LETTER DOTLESS I
        'İ', // LATIN CAPITAL LETTER I WITH DOT ABOVE
        'ü', // LATIN SMALL LETTER U WITH DIAERESIS
        'Ü', // LATIN CAPITAL LETTER U WITH DIAERESIS
        'ö', // LATIN SMALL LETTER O WITH DIAERESIS
        'Ö', // LATIN CAPITAL LETTER O WITH DIAERESIS
        'ş', // LATIN SMALL LETTER S WITH CEDILLA
        'Ş', // LATIN CAPITAL LETTER S WITH CEDILLA
        'ç', // LATIN SMALL LETTER C WITH CEDILLA
        'Ç', // LATIN CAPITAL LETTER C WITH CEDILLA
        'ğ', // LATIN SMALL LETTER G WITH BREVE
        'Ğ'  // LATIN CAPITAL LETTER G WITH BREVE
    };

    /**
     * ASCII replacements corresponding to {@link #TURKISH_CHARS}.
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
        // Utility class; prevent instantiation.
    }

    /**
     * Normalizes a string by replacing Turkish-specific characters with their
     * closest ASCII equivalents.
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