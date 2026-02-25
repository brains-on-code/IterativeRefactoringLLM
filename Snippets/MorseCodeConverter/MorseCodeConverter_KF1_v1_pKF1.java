package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between text and Morse code.
 *
 * Example:
 * "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 * ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 */
public final class Class1 {
    private Class1() {
    }

    private static final Map<Character, String> CHAR_TO_MORSE = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_CHAR = new HashMap<>();

    static {
        CHAR_TO_MORSE.put('A', ".-");
        CHAR_TO_MORSE.put('B', "-...");
        CHAR_TO_MORSE.put('C', "-.-.");
        CHAR_TO_MORSE.put('D', "-..");
        CHAR_TO_MORSE.put('E', ".");
        CHAR_TO_MORSE.put('F', "..-.");
        CHAR_TO_MORSE.put('G', "--.");
        CHAR_TO_MORSE.put('H', "....");
        CHAR_TO_MORSE.put('I', "..");
        CHAR_TO_MORSE.put('J', ".---");
        CHAR_TO_MORSE.put('K', "-.-");
        CHAR_TO_MORSE.put('L', ".-..");
        CHAR_TO_MORSE.put('M', "--");
        CHAR_TO_MORSE.put('N', "-.");
        CHAR_TO_MORSE.put('O', "---");
        CHAR_TO_MORSE.put('P', ".--.");
        CHAR_TO_MORSE.put('Q', "--.-");
        CHAR_TO_MORSE.put('R', ".-.");
        CHAR_TO_MORSE.put('S', "...");
        CHAR_TO_MORSE.put('T', "-");
        CHAR_TO_MORSE.put('U', "..-");
        CHAR_TO_MORSE.put('V', "...-");
        CHAR_TO_MORSE.put('W', ".--");
        CHAR_TO_MORSE.put('X', "-..-");
        CHAR_TO_MORSE.put('Y', "-.--");
        CHAR_TO_MORSE.put('Z', "--..");

        CHAR_TO_MORSE.forEach((character, morse) -> MORSE_TO_CHAR.put(morse, character));
    }

    /**
     * Encodes a plain text string into Morse code.
     * Words are separated by " | " and letters by a single space.
     */
    public static String method1(String text) {
        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (char character : words[wordIndex].toCharArray()) {
                morseBuilder.append(CHAR_TO_MORSE.getOrDefault(character, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                morseBuilder.append("| ");
            }
        }
        return morseBuilder.toString().trim();
    }

    /**
     * Decodes a Morse code string into plain text.
     * Words are expected to be separated by " | " and letters by a single space.
     */
    public static String method2(String morseCode) {
        StringBuilder textBuilder = new StringBuilder();
        String[] morseWords = morseCode.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            for (String morseLetter : morseWords[wordIndex].split(" ")) {
                textBuilder.append(MORSE_TO_CHAR.getOrDefault(morseLetter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(" ");
            }
        }
        return textBuilder.toString();
    }
}