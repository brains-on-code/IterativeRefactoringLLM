package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for operations on collections of {@link FFT.Complex}.
 */
public final class ComplexCollectionUtils {

    private ComplexCollectionUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Ensures the given collection has at least the specified size by
     * appending new {@link FFT.Complex} instances as needed.
     *
     * @param collection the collection to pad
     * @param targetSize the minimum desired size
     */
    private static void ensureMinimumSize(Collection<FFT.Complex> collection, int targetSize) {
        int currentSize = collection.size();
        if (currentSize >= targetSize) {
            return;
        }

        int elementsToAdd = targetSize - currentSize;
        for (int i = 0; i < elementsToAdd; i++) {
            collection.add(new FFT.Complex());
        }
    }

    /**
     * Performs convolution-like multiplication of two complex sequences using FFT.
     * Both input lists are zero-padded to the same length, transformed, multiplied
     * element-wise, and then inverse-transformed.
     *
     * @param firstSequence  the first complex sequence
     * @param secondSequence the second complex sequence
     * @return a new list containing the result of the element-wise multiplication
     *         in the frequency domain, transformed back to the time domain
     */
    public static ArrayList<FFT.Complex> convolve(
            ArrayList<FFT.Complex> firstSequence,
            ArrayList<FFT.Complex> secondSequence
    ) {
        int targetSize = Math.max(firstSequence.size(), secondSequence.size());

        ensureMinimumSize(firstSequence, targetSize);
        ensureMinimumSize(secondSequence, targetSize);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        ArrayList<FFT.Complex> result = new ArrayList<>(targetSize);
        for (int i = 0; i < targetSize; i++) {
            FFT.Complex product = firstSequence.get(i).multiply(secondSequence.get(i));
            result.add(product);
        }

        FFTBluestein.fftBluestein(result, true);
        return result;
    }
}