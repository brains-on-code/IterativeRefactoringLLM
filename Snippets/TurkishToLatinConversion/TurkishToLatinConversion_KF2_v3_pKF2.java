package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting Turkish-specific characters to their Latin equivalents.
 */
public final class TurkishToLatinConversion {

    /**
     * Immutable mapping of Turkish-specific characters to their Latin equivalents.
     */
    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP;

    static {
        Map<Character, Character> map = new HashMap<>();
        map.put('ı', 'i');
        map.put('İ', 'I');
        map.put('ü', 'u');
        map.put('Ü', 'U');
        map.put('ö', 'o');
        map.put('Ö', 'O');
        map.put('ş', 's');
        map.put('Ş', 'S');
        map.put('ç', 'c');
        map.put('Ç', 'C');
        map.put('ğ', 'g');
        map.put('Ğ', 'G');
        TURKISH_TO_LATIN_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TurkishToLatinConversion() {
    }

    /**
     * Returns a copy of the input string where all Turkish-specific characters are replaced
     * with their Latin equivalents. Characters not present in the mapping are left unchanged.
     *
     * @param input the string to convert; may be {@code null}
     * @return a new string with Turkish characters converted, or the original input if it is
     *         {@code null} or empty
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder converted = new StringBuilder(input.length());
        for (char ch : input.toCharArray()) {
            converted.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }
        return converted.toString();
    }
}