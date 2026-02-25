package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between plain text and Morse code.
 *
 * <p>Example:
 * <pre>
 * textToMorse("HELLO WORLD") -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 * morseToText(".... . .-.. .-.. --- | .-- --- .-. .-.. -..") -> "HELLO WORLD"
 * </pre>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
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
     * Converts plain text to Morse code.
     * Words are separated by " | " and letters by a single space.
     *
     * @param text the input text
     * @return Morse code representation of the text
     */
    public static String method1(String text) {
        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (char character : words[wordIndex].toCharArray()) {
                morseBuilder.append(CHAR_TO_MORSE.getOrDefault(character, ""))
                            .append(" ");
            }
            if (wordIndex < words.length - 1) {
                morseBuilder.append("| ");
            }
        }

        return morseBuilder.toString().trim();
    }

    /**
     * Converts Morse code to plain text.
     * Words are expected to be separated by " | " and letters by a single space.
     *
     * @param morse the Morse code string
     * @return decoded plain text
     */
    public static String method2(String morse) {
        StringBuilder textBuilder = new StringBuilder();
        String[] words = morse.split(" \\| ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (String morseLetter : words[wordIndex].split(" ")) {
                textBuilder.append(MORSE_TO_CHAR.getOrDefault(morseLetter, '?'));
            }
            if (wordIndex < words.length - 1) {
                textBuilder.append(" ");
            }
        }

        return textBuilder.toString();
    }
}