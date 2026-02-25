package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Prevent instantiation of utility class
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
     * <p>
     * Steps:
     * <ol>
     *   <li>Zero-pad both sequences to the same length (the maximum of their lengths).</li>
     *   <li>Transform both sequences to the frequency domain using FFT.</li>
     *   <li>Multiply the resulting frequency-domain sequences pointwise.</li>
     *   <li>Apply the inverse FFT to obtain the circular convolution result.</li>
     * </ol>
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

        FFTBluestein.fftBluestein(firstSequence, false);  // forward FFT
        FFTBluestein.fftBluestein(secondSequence, false); // forward FFT

        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            FFT.Complex product = firstSequence.get(i).multiply(secondSequence.get(i));
            convolved.add(product);
        }

        FFTBluestein.fftBluestein(convolved, true); // inverse FFT

        return convolved;
    }
}