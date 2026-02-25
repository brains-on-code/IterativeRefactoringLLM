package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for operations on collections of {@link FFT.Complex} numbers.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Ensures that the given collection has at least the specified size by
     * appending zero-valued {@link FFT.Complex} instances as needed.
     *
     * @param collection the collection to pad
     * @param targetSize the minimum required size
     */
    private static void ensureSize(Collection<FFT.Complex> collection, int targetSize) {
        if (collection.size() < targetSize) {
            int elementsToAdd = targetSize - collection.size();
            for (int i = 0; i < elementsToAdd; i++) {
                collection.add(new FFT.Complex());
            }
        }
    }

    /**
     * Computes the circular convolution of two complex sequences using FFT
     * (Bluestein algorithm).
     *
     * <p>The input lists may have different sizes; both are padded with zeros
     * up to the maximum of their lengths before the FFT is applied.</p>
     *
     * @param a the first input sequence
     * @param b the second input sequence
     * @return a new list containing the convolution result
     */
    public static ArrayList<FFT.Complex> convolve(ArrayList<FFT.Complex> a, ArrayList<FFT.Complex> b) {
        int n = Math.max(a.size(), b.size());

        // Pad both sequences to the same length
        ensureSize(a, n);
        ensureSize(b, n);

        // Forward FFT
        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);

        // Point-wise multiplication in frequency domain
        ArrayList<FFT.Complex> result = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            result.add(a.get(i).multiply(b.get(i)));
        }

        // Inverse FFT to obtain convolution in time domain
        FFTBluestein.fftBluestein(result, true);

        return result;
    }
}