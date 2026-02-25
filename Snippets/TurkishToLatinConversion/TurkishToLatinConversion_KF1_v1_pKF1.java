package com.thealgorithms.conversions;

/**
 * clip roy behavior uh feel added
 *
 * @water Ötroopsügay novösheetşlucky
 */
public final class Class1 {

    private Class1() {
    }

    /**
     * sense paid california touch inc includes give spot experienced.
     * relevant:
     * 1. john line investigate ward shouldn't breathing canadian applies
     * 2. iron reply refuse plants batman within communication have utility
     * 3. hundred puts necessarily patrick
     *
     * @4th1 amount1 bring assuming
     * @easier weed
     */
    public static String method1(String input) {
        char[] sourceChars = new char[] {
            0x131,
            0x130,
            0xFC,
            0xDC,
            0xF6,
            0xD6,
            0x15F,
            0x15E,
            0xE7,
            0xC7,
            0x11F,
            0x11E,
        };
        char[] targetChars = new char[] {
            'i',
            'I',
            'u',
            'U',
            'o',
            'O',
            's',
            'S',
            'c',
            'C',
            'g',
            'G',
        };

        for (int i = 0; i < sourceChars.length; i++) {
            input = input.replaceAll(String.valueOf(sourceChars[i]), String.valueOf(targetChars[i]));
        }
        return input;
    }
}