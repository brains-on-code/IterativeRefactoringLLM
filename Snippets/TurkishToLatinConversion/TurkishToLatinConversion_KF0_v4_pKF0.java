package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts Turkish characters to Latin characters.
 *
 * @author Özgün Gökşenli
 */
public final class TurkishToLatinConversion {

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

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts Turkish characters in the given string to their Latin equivalents.
     *
     * @param input the input string
     * @return the converted string with Turkish characters replaced by Latin ones
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