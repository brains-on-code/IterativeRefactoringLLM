package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {}

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