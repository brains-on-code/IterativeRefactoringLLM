package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    private static void padWithZeros(Collection<FFT.Complex> sequence, int targetLength) {
        int currentLength = sequence.size();
        if (currentLength >= targetLength) {
            return;
        }

        int zerosToAdd = targetLength - currentLength;
        for (int i = 0; i < zerosToAdd; i++) {
            sequence.add(new FFT.Complex());
        }
    }

    public static ArrayList<FFT.Complex> computeCircularConvolution(
            ArrayList<FFT.Complex> firstSequence,
            ArrayList<FFT.Complex> secondSequence
    ) {
        int sequenceLength = Math.max(firstSequence.size(), secondSequence.size());

        padWithZeros(firstSequence, sequenceLength);
        padWithZeros(secondSequence, sequenceLength);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>(sequenceLength);
        for (int i = 0; i < sequenceLength; i++) {
            FFT.Complex product = firstSequence.get(i).multiply(secondSequence.get(i));
            frequencyDomainProduct.add(product);
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}