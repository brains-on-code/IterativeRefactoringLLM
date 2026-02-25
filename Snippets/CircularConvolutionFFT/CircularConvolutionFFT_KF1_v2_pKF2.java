package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for operations on collections of {@link FFT.Complex} numbers.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Pads the given collection with zero-valued {@link FFT.Complex} instances
     * until it reaches at least {@code targetSize} elements.
     *
     * @param collection the collection to pad
     * @param targetSize the minimum required size
     */
    private static void ensureSize(Collection<FFT.Complex> collection, int targetSize) {
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
     * (Bluestein algorithm).
     *
     * <p>Both input lists are padded with zeros up to the maximum of their
     * lengths before the FFT is applied.</p>
     *
     * @param a the first input sequence
     * @param b the second input sequence
     * @return a new list containing the convolution result
     */
    public static ArrayList<FFT.Complex> convolve(ArrayList<FFT.Complex> a, ArrayList<FFT.Complex> b) {
        int n = Math.max(a.size(), b.size());

        ensureSize(a, n);
        ensureSize(b, n);

        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);

        ArrayList<FFT.Complex> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            result.add(a.get(i).multiply(b.get(i)));
        }

        FFTBluestein.fftBluestein(result, true);

        return result;
    }
}