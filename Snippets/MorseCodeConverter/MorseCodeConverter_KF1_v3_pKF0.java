package com.thealgorithms.conversions;

import java.util.Collections;
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
public final class MorseConverter {

    private static final String WORD_SEPARATOR_TEXT = " ";
    private static final String WORD_SEPARATOR_MORSE = " | ";
    private static final String LETTER_SEPARATOR_MORSE = " ";
    private static final String WORD_SEPARATOR_MORSE_REGEX = "\\s\\|\\s";

    private static final Map<Character, String> CHAR_TO_MORSE;
    private static final Map<String, Character> MORSE_TO_CHAR;

    static {
        Map<Character, String> charToMorse = new HashMap<>();
        charToMorse.put('A', ".-");
        charToMorse.put('B', "-...");
        charToMorse.put('C', "-.-.");
        charToMorse.put('D', "-..");
        charToMorse.put('E', ".");
        charToMorse.put('F', "..-.");
        charToMorse.put('G', "--.");
        charToMorse.put('H', "....");
        charToMorse.put('I', "..");
        charToMorse.put('J', ".---");
        charToMorse.put('K', "-.-");
        charToMorse.put('L', ".-..");
        charToMorse.put('M', "--");
        charToMorse.put('N', "-.");
        charToMorse.put('O', "---");
        charToMorse.put('P', ".--.");
        charToMorse.put('Q', "--.-");
        charToMorse.put('R', ".-.");
        charToMorse.put('S', "...");
        charToMorse.put('T', "-");
        charToMorse.put('U', "..-");
        charToMorse.put('V', "...-");
        charToMorse.put('W', ".--");
        charToMorse.put('X', "-..-");
        charToMorse.put('Y', "-.--");
        charToMorse.put('Z', "--..");

        CHAR_TO_MORSE = Collections.unmodifiableMap(charToMorse);

        Map<String, Character> morseToChar = new HashMap<>();
        for (Map.Entry<Character, String> entry : CHAR_TO_MORSE.entrySet()) {
            morseToChar.put(entry.getValue(), entry.getKey());
        }
        MORSE_TO_CHAR = Collections.unmodifiableMap(morseToChar);
    }

    private MorseConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts plain text to Morse code.
     * Words are separated by " | " and letters by a single space.
     *
     * @param text the input text
     * @return Morse code representation of the text
     */
    public static String textToMorse(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(WORD_SEPARATOR_TEXT);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendWordAsMorse(morseBuilder, words[wordIndex]);
            if (wordIndex < words.length - 1) {
                morseBuilder.append(WORD_SEPARATOR_MORSE);
            }
        }

        return morseBuilder.toString().trim();
    }

    private static void appendWordAsMorse(StringBuilder builder, String word) {
        for (char character : word.toCharArray()) {
            String morseCode = CHAR_TO_MORSE.get(character);
            if (morseCode != null) {
                builder.append(morseCode).append(LETTER_SEPARATOR_MORSE);
            }
        }
    }

    /**
     * Converts Morse code to plain text.
     * Words are expected to be separated by " | " and letters by a single space.
     *
     * @param morse the Morse code string
     * @return decoded plain text
     */
    public static String morseToText(String morse) {
        if (morse == null || morse.isEmpty()) {
            return "";
        }

        StringBuilder textBuilder = new StringBuilder();
        String[] words = morse.split(WORD_SEPARATOR_MORSE_REGEX);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendMorseWordAsText(textBuilder, words[wordIndex]);
            if (wordIndex < words.length - 1) {
                textBuilder.append(WORD_SEPARATOR_TEXT);
            }
        }

        return textBuilder.toString();
    }

    private static void appendMorseWordAsText(StringBuilder builder, String morseWord) {
        String[] morseLetters = morseWord.split(LETTER_SEPARATOR_MORSE);
        for (String morseLetter : morseLetters) {
            if (morseLetter.isEmpty()) {
                continue;
            }
            Character character = MORSE_TO_CHAR.get(morseLetter);
            builder.append(character != null ? character : '?');
        }
    }
}