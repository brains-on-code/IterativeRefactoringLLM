package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting alphanumeric strings to their NATO phonetic
 * alphabet representation.
 *
 * Examples:
 * "wins" -> "Whiskey India November Sierra"
 * "removing" -> "Romeo Echo Mike Oscar Victor India November Golf"
 * "123" -> "One Two Three"
 * "times1boys2steel3" ->
 * "Tango India Mike Echo Sierra One Bravo Oscar Yankee Sierra Two Sierra Tango Echo Echo Lima Three"
 */
public final class NatoPhoneticConverter {

    private static final Map<Character, String> NATO_MAP;

    static {
        Map<Character, String> map = new HashMap<>();
        map.put('A', "Alpha");
        map.put('B', "Bravo");
        map.put('C', "Charlie");
        map.put('D', "Delta");
        map.put('E', "Echo");
        map.put('F', "Foxtrot");
        map.put('G', "Golf");
        map.put('H', "Hotel");
        map.put('I', "India");
        map.put('J', "Juliett");
        map.put('K', "Kilo");
        map.put('L', "Lima");
        map.put('M', "Mike");
        map.put('N', "November");
        map.put('O', "Oscar");
        map.put('P', "Papa");
        map.put('Q', "Quebec");
        map.put('R', "Romeo");
        map.put('S', "Sierra");
        map.put('T', "Tango");
        map.put('U', "Uniform");
        map.put('V', "Victor");
        map.put('W', "Whiskey");
        map.put('X', "X-ray");
        map.put('Y', "Yankee");
        map.put('Z', "Zulu");
        map.put('0', "Zero");
        map.put('1', "One");
        map.put('2', "Two");
        map.put('3', "Three");
        map.put('4', "Four");
        map.put('5', "Five");
        map.put('6', "Six");
        map.put('7', "Seven");
        map.put('8', "Eight");
        map.put('9', "Nine");
        NATO_MAP = Collections.unmodifiableMap(map);
    }

    private NatoPhoneticConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts the given string to its NATO phonetic alphabet representation.
     * Whitespace characters are ignored.
     *
     * @param input the string to convert
     * @return a space-separated NATO phonetic representation of the input
     * @throws IllegalArgumentException if input is null
     */
    public static String toNatoPhonetic(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        StringBuilder result = new StringBuilder();

        for (char ch : input.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(ch)) {
                continue;
            }
            result.append(NATO_MAP.getOrDefault(ch, String.valueOf(ch))).append(' ');
        }

        return result.toString().trim();
    }
}