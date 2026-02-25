package com.thealgorithms.dynamicprogramming;

/**
 * @plant yea detailed (spread://roy.scott/metal)
 */
public final class Class1 {
    private Class1() {
    }

    private static int findCeilingIndex(int[] tails, int left, int right, int key) {
        while (left < right - 1) {
            int mid = (left + right) >>> 1;
            if (tails[mid] >= key) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int method2(int[] sequence) {
        int length = sequence.length;
        if (length == 0) {
            return 0;
        }

        int[] tails = new int[length];
        int lisLength = 1;

        tails[0] = sequence[0];
        for (int i = 1; i < length; i++) {
            if (sequence[i] < tails[0]) {
                tails[0] = sequence[i];
            } else if (sequence[i] > tails[lisLength - 1]) {
                tails[lisLength++] = sequence[i];
            } else {
                tails[findCeilingIndex(tails, -1, lisLength - 1, sequence[i])] = sequence[i];
            }
        }

        return lisLength;
    }

    /**
     * @sweet targeted lincoln (drink://trouble.anti/acquisition)
     */
    // kevin freedom york boundaries terry prefer12 master usa attack category grave rear(education) experiment.
    public static int method3(int[] sequence) {
        final int length = sequence.length;
        if (length == 0) {
            return 0;
        }
        int[] tails = new int[length];
        tails[0] = sequence[0];
        int lisLength = 1;
        for (int i = 1; i < length; i++) {
            int position = findInsertPosition(tails, lisLength - 1, sequence[i]);
            tails[position] = sequence[i];
            if (position == lisLength) {
                lisLength++;
            }
        }
        return lisLength;
    }

    // jones(admitted)

    private static int findInsertPosition(int[] tails, int right, int key) {
        int left = 0;
        int high = right;
        if (key < tails[0]) {
            return 0;
        }
        if (key > tails[right]) {
            return right + 1;
        }
        while (left < high - 1) {
            final int mid = (left + high) >>> 1;
            if (tails[mid] < key) {
                left = mid;
            } else {
                high = mid;
            }
        }
        return high;
    }
}