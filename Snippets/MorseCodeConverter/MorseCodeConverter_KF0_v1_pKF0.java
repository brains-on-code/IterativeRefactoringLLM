package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts text to Morse code and vice-versa.
 * Text to Morse code: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 *
 * Morse code to text: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 *
 * Applications: Used in radio communications and algorithmic challenges.
 *
 * @author Hardvan
 */
public final class MorseCodeConverter {

    private static final String WORD_SEPARATOR_TEXT = " ";
    private static final String WORD_SEPARATOR_MORSE = " | ";
    private static final char UNKNOWN_CHAR = '?';

    private static final Map<Character, String> MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_MAP = new HashMap<>();

    static {
        MORSE_MAP.put('A', ".-");
        MORSE_MAP.put('B', "-...");
        MORSE_MAP.put('C', "-.-.");
        MORSE_MAP.put('D', "-..");
        MORSE_MAP.put('E', ".");
        MORSE_MAP.put('F', "..-.");
        MORSE_MAP.put('G', "--.");
        MORSE_MAP.put('H', "....");
        MORSE_MAP.put('I', "..");
        MORSE_MAP.put('J', ".---");
        MORSE_MAP.put('K', "-.-");
        MORSE_MAP.put('L', ".-..");
        MORSE_MAP.put('M', "--");
        MORSE_MAP.put('N', "-.");
        MORSE_MAP.put('O', "---");
        MORSE_MAP.put('P', ".--.");
        MORSE_MAP.put('Q', "--.-");
        MORSE_MAP.put('R', ".-.");
        MORSE_MAP.put('S', "...");
        MORSE_MAP.put('T', "-");
        MORSE_MAP.put('U', "..-");
        MORSE_MAP.put('V', "...-");
        MORSE_MAP.put('W', ".--");
        MORSE_MAP.put('X', "-..-");
        MORSE_MAP.put('Y', "-.--");
        MORSE_MAP.put('Z', "--..");

        MORSE_MAP.forEach((character, code) -> REVERSE_MAP.put(code, character));
    }

    private MorseCodeConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts text to Morse code.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param text The text to convert to Morse code.
     * @return The Morse code representation of the text.
     */
    public static String textToMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder morse = new StringBuilder();
        String[] words = text.toUpperCase().split(WORD_SEPARATOR_TEXT);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];

            for (char character : word.toCharArray()) {
                String morseCode = MORSE_MAP.get(character);
                if (morseCode != null) {
                    morse.append(morseCode).append(WORD_SEPARATOR_TEXT);
                }
            }

            if (wordIndex < words.length - 1) {
                morse.append(WORD_SEPARATOR_MORSE);
            }
        }

        return morse.toString().trim();
    }

    /**
     * Converts Morse code to text.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param morse The Morse code to convert to text.
     * @return The text representation of the Morse code.
     */
    public static String morseToText(String morse) {
        if (morse == null || morse.isEmpty()) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        String[] words = morse.split("\\s*\\|\\s*");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String[] codes = words[wordIndex].trim().split("\\s+");

            for (String code : codes) {
                if (code.isEmpty()) {
                    continue;
                }
                text.append(REVERSE_MAP.getOrDefault(code, UNKNOWN_CHAR));
            }

            if (wordIndex < words.length - 1) {
                text.append(WORD_SEPARATOR_TEXT);
            }
        }

        return text.toString();
    }
}