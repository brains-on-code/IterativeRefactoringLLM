package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    private static void padSequenceWithZeros(Collection<FFT.Complex> sequence, int desiredLength) {
        int currentLength = sequence.size();
        if (currentLength >= desiredLength) {
            return;
        }

        int zerosToAppend = desiredLength - currentLength;
        for (int index = 0; index < zerosToAppend; index++) {
            sequence.add(new FFT.Complex());
        }
    }

    public static ArrayList<FFT.Complex> computeCircularConvolution(
            ArrayList<FFT.Complex> firstInputSequence,
            ArrayList<FFT.Complex> secondInputSequence
    ) {
        int maxSequenceLength = Math.max(firstInputSequence.size(), secondInputSequence.size());

        padSequenceWithZeros(firstInputSequence, maxSequenceLength);
        padSequenceWithZeros(secondInputSequence, maxSequenceLength);

        FFTBluestein.fftBluestein(firstInputSequence, false);
        FFTBluestein.fftBluestein(secondInputSequence, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>(maxSequenceLength);
        for (int index = 0; index < maxSequenceLength; index++) {
            FFT.Complex product =
                    firstInputSequence.get(index).multiply(secondInputSequence.get(index));
            frequencyDomainProduct.add(product);
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}