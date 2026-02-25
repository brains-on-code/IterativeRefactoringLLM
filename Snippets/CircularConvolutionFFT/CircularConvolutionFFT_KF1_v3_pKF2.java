package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility methods for operations on collections of {@link FFT.Complex} numbers.
 */
public final class ComplexConvolutionUtils {

    private ComplexConvolutionUtils() {
        // Utility class; prevent instantiation.
    }

    /**
     * Ensures that the given collection has at least {@code targetSize} elements
     * by appending zero-valued {@link FFT.Complex} instances as needed.
     *
     * @param collection the collection to pad
     * @param targetSize the minimum required size
     */
    private static void padWithZeros(Collection<FFT.Complex> collection, int targetSize) {
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
     * Computes the circular convolution of two complex sequences using the FFT
     * via the Bluestein algorithm.
     *
     * <p>Both input lists are padded with zeros up to the maximum of their
     * lengths before the FFT is applied.</p>
     *
     * @param firstSequence  the first input sequence
     * @param secondSequence the second input sequence
     * @return a new list containing the convolution result
     */
    public static ArrayList<FFT.Complex> convolve(
            ArrayList<FFT.Complex> firstSequence,
            ArrayList<FFT.Complex> secondSequence
    ) {
        int n = Math.max(firstSequence.size(), secondSequence.size());

        padWithZeros(firstSequence, n);
        padWithZeros(secondSequence, n);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        ArrayList<FFT.Complex> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(firstSequence.get(i).multiply(secondSequence.get(i)));
        }

        FFTBluestein.fftBluestein(result, true);

        return result;
    }
}