package com.thealgorithms.conversions;

import java.util.Map;

public final class TurkishToLatinConversion {

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = Map.ofEntries(
        Map.entry('ı', 'i'),
        Map.entry('İ', 'I'),
        Map.entry('ü', 'u'),
        Map.entry('Ü', 'U'),
        Map.entry('ö', 'o'),
        Map.entry('Ö', 'O'),
        Map.entry('ş', 's'),
        Map.entry('Ş', 'S'),
        Map.entry('ç', 'c'),
        Map.entry('Ç', 'C'),
        Map.entry('ğ', 'g'),
        Map.entry('Ğ', 'G')
    );

    private TurkishToLatinConversion() {
        // Prevent instantiation
    }

    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder converted = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            converted.append(TURKISH_TO_LATIN_MAP.getOrDefault(character, character));
        }
        return converted.toString();
    }
}