package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting alphanumeric strings to their NATO phonetic
 * alphabet representation (including digits).
 *
 * <p>Examples:
 * <ul>
 *   <li>"wins" -> "Whiskey India November Sierra"</li>
 *   <li>"removing" -> "Romeo Echo Mike Oscar Victor India November Golf"</li>
 *   <li>"123" -> "One Two Three"</li>
 *   <li>"times1boys2steel3" ->
 *       "Tango India Mike Echo Sierra One Bravo Oscar Yankee Sierra Two Sierra Tango Echo Echo Lima Three"</li>
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /** Mapping from characters to their NATO phonetic (or digit) representation. */
    private static final Map<Character, String> NATO_MAP = new HashMap<>();

    static {
        NATO_MAP.put('A', "Alpha");
        NATO_MAP.put('B', "Bravo");
        NATO_MAP.put('C', "Charlie");
        NATO_MAP.put('D', "Delta");
        NATO_MAP.put('E', "Echo");
        NATO_MAP.put('F', "Foxtrot");
        NATO_MAP.put('G', "Golf");
        NATO_MAP.put('H', "Hotel");
        NATO_MAP.put('I', "India");
        NATO_MAP.put('J', "Juliett");
        NATO_MAP.put('K', "Kilo");
        NATO_MAP.put('L', "Lima");
        NATO_MAP.put('M', "Mike");
        NATO_MAP.put('N', "November");
        NATO_MAP.put('O', "Oscar");
        NATO_MAP.put('P', "Papa");
        NATO_MAP.put('Q', "Quebec");
        NATO_MAP.put('R', "Romeo");
        NATO_MAP.put('S', "Sierra");
        NATO_MAP.put('T', "Tango");
        NATO_MAP.put('U', "Uniform");
        NATO_MAP.put('V', "Victor");
        NATO_MAP.put('W', "Whiskey");
        NATO_MAP.put('X', "X-ray");
        NATO_MAP.put('Y', "Yankee");
        NATO_MAP.put('Z', "Zulu");
        NATO_MAP.put('0', "Zero");
        NATO_MAP.put('1', "One");
        NATO_MAP.put('2', "Two");
        NATO_MAP.put('3', "Three");
        NATO_MAP.put('4', "Four");
        NATO_MAP.put('5', "Five");
        NATO_MAP.put('6', "Six");
        NATO_MAP.put('7', "Seven");
        NATO_MAP.put('8', "Eight");
        NATO_MAP.put('9', "Nine");
    }

    /**
     * Converts the given string to its NATO phonetic alphabet representation.
     * Whitespace characters are ignored. Characters without a mapping are kept
     * as-is.
     *
     * @param input the string to convert
     * @return a space-separated NATO phonetic representation of the input
     */
    public static String method1(String input) {
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