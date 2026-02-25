package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    private static void padWithZeros(final Collection<FFT.Complex> sequence, final int targetSize) {
        final int currentSize = sequence.size();
        if (currentSize >= targetSize) {
            return;
        }

        final int elementsToAdd = targetSize - currentSize;
        for (int i = 0; i < elementsToAdd; i++) {
            sequence.add(new FFT.Complex());
        }
    }

    public static ArrayList<FFT.Complex> fftCircularConvolution(
            final ArrayList<FFT.Complex> firstSequence,
            final ArrayList<FFT.Complex> secondSequence
    ) {
        final int convolvedSize = Math.max(firstSequence.size(), secondSequence.size());

        padWithZeros(firstSequence, convolvedSize);
        padWithZeros(secondSequence, convolvedSize);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        final ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            final FFT.Complex firstValue = firstSequence.get(i);
            final FFT.Complex secondValue = secondSequence.get(i);
            convolved.add(firstValue.multiply(secondValue));
        }

        FFTBluestein.fftBluestein(convolved, true);
        return convolved;
    }
}