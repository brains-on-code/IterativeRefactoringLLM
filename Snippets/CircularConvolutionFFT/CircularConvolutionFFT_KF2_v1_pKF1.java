package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    private static void padWithZeros(Collection<FFT.Complex> sequence, int targetSize) {
        int currentSize = sequence.size();
        if (currentSize < targetSize) {
            int elementsToAdd = targetSize - currentSize;
            for (int i = 0; i < elementsToAdd; i++) {
                sequence.add(new FFT.Complex());
            }
        }
    }

    public static ArrayList<FFT.Complex> computeCircularConvolution(
            ArrayList<FFT.Complex> firstSequence,
            ArrayList<FFT.Complex> secondSequence
    ) {
        int convolutionSize = Math.max(firstSequence.size(), secondSequence.size());

        padWithZeros(firstSequence, convolutionSize);
        padWithZeros(secondSequence, convolutionSize);

        FFTBluestein.fftBluestein(firstSequence, false);
        FFTBluestein.fftBluestein(secondSequence, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>(convolutionSize);
        for (int i = 0; i < convolutionSize; i++) {
            FFT.Complex product = firstSequence.get(i).multiply(secondSequence.get(i));
            frequencyDomainProduct.add(product);
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}