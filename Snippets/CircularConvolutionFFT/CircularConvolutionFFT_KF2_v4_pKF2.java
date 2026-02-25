package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given sequence with zero-valued complex numbers until it reaches
     * the specified target size. If the sequence is already at least the target
     * size, it is left unchanged.
     *
     * @param sequence   the sequence to pad
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
     * Both input sequences are zero-padded to the same length (the maximum of
     * their original lengths), transformed to the frequency domain, multiplied
     * pointwise, and then transformed back.
     *
     * @param firstSequence  the first input sequence
     * @param secondSequence the second input sequence
     * @return the circularly convolved sequence
     */
    public static ArrayList<FFT.Complex> fftCircularConvolution(
        ArrayList<FFT.Complex> firstSequence,
        ArrayList<FFT.Complex> secondSequence
    ) {
        int convolvedSize = Math.max(firstSequence.size(), secondSequence.size());

        padWithZeros(firstSequence, convolvedSize);
        padWithZeros(secondSequence, convolvedSize);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            FFT.Complex product = firstSequence.get(i).multiply(secondSequence.get(i));
            convolved.add(product);
        }

        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}