package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given collection of complex numbers with zeros until it reaches
     * the specified size.
     *
     * @param sequence the collection to pad
     * @param targetSize the desired size after padding
     */
    private static void padWithZeros(Collection<FFT.Complex> sequence, int targetSize) {
        int currentSize = sequence.size();
        if (currentSize >= targetSize) {
            return;
        }

        int elementsToAdd = targetSize - currentSize;
        for (int i = 0; i < elementsToAdd; i++) {
            sequence.add(new FFT.Complex());
        }
    }

    /**
     * Computes the circular convolution of two complex sequences using the FFT.
     *
     * @param a first input sequence
     * @param b second input sequence
     * @return the circularly convolved sequence
     */
    public static ArrayList<FFT.Complex> fftCircularConvolution(
            ArrayList<FFT.Complex> a,
            ArrayList<FFT.Complex> b
    ) {
        int convolvedSize = Math.max(a.size(), b.size());

        padWithZeros(a, convolvedSize);
        padWithZeros(b, convolvedSize);

        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);

        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            convolved.add(a.get(i).multiply(b.get(i)));
        }

        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}