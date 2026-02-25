package com.thealgorithms.conversions;

/**
 * Utility class for converting Turkish-specific characters to their ASCII equivalents.
 */
public final class TurkishCharacterConverter {

    /**
     * Turkish-specific characters to be normalized.
     */
    private static final char[] TURKISH_CHARS = {
        0x131, // ı
        0x130, // İ
        0xFC,  // ü
        0xDC,  // Ü
        0xF6,  // ö
        0xD6,  // Ö
        0x15F, // ş
        0x15E, // Ş
        0xE7,  // ç
        0xC7,  // Ç
        0x11F, // ğ
        0x11E  // Ğ
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
        // Prevent instantiation of utility class.
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