package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility class for convolution operations using FFT.
 */
public final class ConvolutionUtils {

    private ConvolutionUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given collection of complex numbers with zeros until it reaches the
     * specified target size.
     *
     * @param complexSequence the collection to pad
     * @param targetSize      the desired size after padding
     */
    private static void padWithZeros(Collection<FFT.Complex> complexSequence, int targetSize) {
        int currentSize = complexSequence.size();
        if (currentSize < targetSize) {
            int zerosToAdd = targetSize - currentSize;
            for (int i = 0; i < zerosToAdd; i++) {
                complexSequence.add(new FFT.Complex());
            }
        }
    }

    /**
     * Computes the convolution of two signals represented as lists of complex
     * numbers using the FFT (Bluestein) algorithm.
     *
     * @param firstSignal  the first input signal
     * @param secondSignal the second input signal
     * @return the convolution result as a list of complex numbers
     */
    public static ArrayList<FFT.Complex> convolve(ArrayList<FFT.Complex> firstSignal,
                                                  ArrayList<FFT.Complex> secondSignal) {
        int maxSignalLength = Math.max(firstSignal.size(), secondSignal.size());
        padWithZeros(firstSignal, maxSignalLength);
        padWithZeros(secondSignal, maxSignalLength);

        FFTBluestein.fftBluestein(firstSignal, false);
        FFTBluestein.fftBluestein(secondSignal, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>(maxSignalLength);
        for (int index = 0; index < maxSignalLength; index++) {
            FFT.Complex pointwiseProduct = firstSignal.get(index).multiply(secondSignal.get(index));
            frequencyDomainProduct.add(pointwiseProduct);
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}