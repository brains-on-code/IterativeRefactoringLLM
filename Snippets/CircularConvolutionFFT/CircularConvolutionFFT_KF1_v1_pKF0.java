package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for operations on collections of {@link FFT.Complex}.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Ensures the given collection has at least the specified size by
     * appending new {@link FFT.Complex} instances as needed.
     *
     * @param collection the collection to pad
     * @param targetSize the minimum desired size
     */
    private static void ensureSize(Collection<FFT.Complex> collection, int targetSize) {
        int currentSize = collection.size();
        if (currentSize < targetSize) {
            int elementsToAdd = targetSize - currentSize;
            for (int i = 0; i < elementsToAdd; i++) {
                collection.add(new FFT.Complex());
            }
        }
    }

    /**
     * Performs convolution-like multiplication of two complex sequences using FFT.
     * Both input lists are zero-padded to the same length, transformed, multiplied
     * element-wise, and then inverse-transformed.
     *
     * @param first  the first complex sequence
     * @param second the second complex sequence
     * @return a new list containing the result of the element-wise multiplication
     *         in the frequency domain, transformed back to the time domain
     */
    public static ArrayList<FFT.Complex> method2(
            ArrayList<FFT.Complex> first,
            ArrayList<FFT.Complex> second
    ) {
        int targetSize = Math.max(first.size(), second.size());

        ensureSize(first, targetSize);
        ensureSize(second, targetSize);

        FFTBluestein.fftBluestein(first, false);
        FFTBluestein.fftBluestein(second, false);

        ArrayList<FFT.Complex> result = new ArrayList<>(targetSize);
        for (int i = 0; i < targetSize; i++) {
            result.add(first.get(i).multiply(second.get(i)));
        }

        FFTBluestein.fftBluestein(result, true);
        return result;
    }
}