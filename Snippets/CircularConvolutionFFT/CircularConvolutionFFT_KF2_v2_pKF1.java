package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    private static void padSequenceWithZeros(Collection<FFT.Complex> sequence, int desiredLength) {
        int currentLength = sequence.size();
        if (currentLength < desiredLength) {
            int zerosToAdd = desiredLength - currentLength;
            for (int i = 0; i < zerosToAdd; i++) {
                sequence.add(new FFT.Complex());
            }
        }
    }

    public static ArrayList<FFT.Complex> computeCircularConvolution(
            ArrayList<FFT.Complex> inputSequenceA,
            ArrayList<FFT.Complex> inputSequenceB
    ) {
        int convolutionLength = Math.max(inputSequenceA.size(), inputSequenceB.size());

        padSequenceWithZeros(inputSequenceA, convolutionLength);
        padSequenceWithZeros(inputSequenceB, convolutionLength);

        FFTBluestein.fftBluestein(inputSequenceA, false);
        FFTBluestein.fftBluestein(inputSequenceB, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>(convolutionLength);
        for (int index = 0; index < convolutionLength; index++) {
            FFT.Complex productAtIndex =
                    inputSequenceA.get(index).multiply(inputSequenceB.get(index));
            frequencyDomainProduct.add(productAtIndex);
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}