package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class MorseCodeConverter {

    private MorseCodeConverter() {
        // Utility class; prevent instantiation
    }

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

    /**
     * Converts plain text to Morse code.
     * Words are separated by a single space in the input and by " | " in the output.
     *
     * @param text the input text to convert
     * @return the Morse code representation of the input text
     */
    public static String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (char character : words[wordIndex].toCharArray()) {
                morse.append(MORSE_MAP.getOrDefault(character, ""))
                     .append(" ");
            }
            if (wordIndex < words.length - 1) {
                morse.append("| ");
            }
        }

        return morse.toString().trim();
    }

    /**
     * Converts Morse code to plain text.
     * Words are expected to be separated by " | " in the input.
     *
     * @param morse the Morse code string to convert
     * @return the plain text representation of the Morse code
     */
    public static String morseToText(String morse) {
        StringBuilder text = new StringBuilder();
        String[] words = morse.split(" \\| ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            for (String code : words[wordIndex].split(" ")) {
                text.append(REVERSE_MAP.getOrDefault(code, '?'));
            }
            if (wordIndex < words.length - 1) {
                text.append(" ");
            }
        }

        return text.toString();
    }
}