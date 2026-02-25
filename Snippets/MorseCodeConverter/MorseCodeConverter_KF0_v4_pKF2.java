package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between plain text and Morse code.
 *
 * <p>Text → Morse:
 * <ul>
 *   <li>Letters are separated by a single space.</li>
 *   <li>Words are separated by a pipe character: {@code |}.</li>
 *   <li>Example: {@code "HELLO WORLD"} → {@code ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."}</li>
 * </ul>
 *
 * <p>Morse → Text:
 * <ul>
 *   <li>Letters are separated by a single space.</li>
 *   <li>Words are separated by a pipe character: {@code |}.</li>
 *   <li>Example: {@code ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."} → {@code "HELLO WORLD"}</li>
 * </ul>
 *
 * <p>Applications include radio communications and algorithmic challenges.</p>
 */
public final class MorseCodeConverter {

    private static final String LETTER_SEPARATOR = " ";
    private static final String MORSE_WORD_DELIMITER = " | ";
    private static final char UNKNOWN_CHAR = '?';

    private static final Map<Character, String> MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> REVERSE_MAP = new HashMap<>();

    private MorseCodeConverter() {
        // Prevent instantiation of utility class.
    }

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
     *
     * <p>Rules:
     * <ul>
     *   <li>Input is converted to uppercase.</li>
     *   <li>Letters are separated by a single space.</li>
     *   <li>Words are separated by {@code " | "} (space, pipe, space).</li>
     *   <li>Characters without a Morse mapping are skipped.</li>
     * </ul>
     *
     * @param text the text to convert; may contain multiple words separated by spaces
     * @return the Morse code representation of {@code text}
     */
    public static String textToMorse(String text) {
        StringBuilder morse = new StringBuilder();
        String[] words = text.toUpperCase().split(LETTER_SEPARATOR);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendWordAsMorse(morse, words[wordIndex]);

            if (wordIndex < words.length - 1) {
                morse.append(MORSE_WORD_DELIMITER.trim()).append(LETTER_SEPARATOR);
            }
        }

        return morse.toString().trim();
    }

    private static void appendWordAsMorse(StringBuilder morse, String word) {
        for (char letter : word.toCharArray()) {
            String code = MORSE_MAP.get(letter);
            if (code != null) {
                morse.append(code).append(LETTER_SEPARATOR);
            }
        }
    }

    /**
     * Converts Morse code to plain text.
     *
     * <p>Rules:
     * <ul>
     *   <li>Letters are expected to be separated by a single space.</li>
     *   <li>Words are expected to be separated by {@code " | "} (space, pipe, space).</li>
     *   <li>Unknown Morse sequences are converted to {@code '?'}</li>
     * </ul>
     *
     * @param morse the Morse code to convert; letters separated by spaces, words by {@code |}
     * @return the decoded text in uppercase
     */
    public static String morseToText(String morse) {
        StringBuilder text = new StringBuilder();
        String[] words = morse.split(MORSE_WORD_DELIMITER);

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            appendMorseWordAsText(text, words[wordIndex]);

            if (wordIndex < words.length - 1) {
                text.append(LETTER_SEPARATOR);
            }
        }

        return text.toString();
    }

    private static void appendMorseWordAsText(StringBuilder text, String morseWord) {
        String[] codes = morseWord.split(LETTER_SEPARATOR);

        for (String code : codes) {
            if (code.isEmpty()) {
                continue;
            }
            text.append(REVERSE_MAP.getOrDefault(code, UNKNOWN_CHAR));
        }
    }
}