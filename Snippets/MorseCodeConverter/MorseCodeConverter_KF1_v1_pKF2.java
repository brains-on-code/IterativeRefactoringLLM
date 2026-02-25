package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between plain text and Morse code.
 *
 * <p>Encoding example:
 * <pre>
 *   "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 * </pre>
 *
 * <p>Decoding example:
 * <pre>
 *   ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 * </pre>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /** Mapping from uppercase letters to Morse code. */
    private static final Map<Character, String> CHAR_TO_MORSE = new HashMap<>();

    /** Mapping from Morse code to uppercase letters. */
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

        // Build reverse lookup map
        CHAR_TO_MORSE.forEach((character, morse) -> MORSE_TO_CHAR.put(morse, character));
    }

    /**
     * Encodes a plain text string into Morse code.
     *
     * <p>Words are separated by {@code " | "} in the resulting Morse code.
     * Characters that do not have a Morse representation are skipped.
     *
     * @param text the input text to encode
     * @return the Morse code representation of the input text
     */
    public static String method1(String text) {
        StringBuilder encoded = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (char ch : words[wordIndex].toCharArray()) {
                encoded.append(CHAR_TO_MORSE.getOrDefault(ch, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                encoded.append("| ");
            }
        }
        return encoded.toString().trim();
    }

    /**
     * Decodes a Morse code string into plain text.
     *
     * <p>Words in Morse code must be separated by {@code " | "}.
     * Unknown Morse sequences are decoded as {@code '?'}.
     *
     * @param morse the Morse code string to decode
     * @return the decoded plain text
     */
    public static String method2(String morse) {
        StringBuilder decoded = new StringBuilder();
        String[] morseWords = morse.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            for (String morseChar : morseWords[wordIndex].split(" ")) {
                decoded.append(MORSE_TO_CHAR.getOrDefault(morseChar, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                decoded.append(" ");
            }
        }
        return decoded.toString();
    }
}