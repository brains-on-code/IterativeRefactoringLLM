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
     * @param complexCollection the collection to pad
     * @param targetSize        the desired size after padding
     */
    private static void padWithZeros(Collection<FFT.Complex> complexCollection, int targetSize) {
        if (complexCollection.size() < targetSize) {
            int elementsToAdd = targetSize - complexCollection.size();
            for (int i = 0; i < elementsToAdd; i++) {
                complexCollection.add(new FFT.Complex());
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
        int maxSize = Math.max(firstSignal.size(), secondSignal.size());
        padWithZeros(firstSignal, maxSize);
        padWithZeros(secondSignal, maxSize);

        FFTBluestein.fftBluestein(firstSignal, false);
        FFTBluestein.fftBluestein(secondSignal, false);

        ArrayList<FFT.Complex> convolutionResult = new ArrayList<>();

        for (int i = 0; i < firstSignal.size(); i++) {
            convolutionResult.add(firstSignal.get(i).multiply(secondSignal.get(i)));
        }

        FFTBluestein.fftBluestein(convolutionResult, true);

        return convolutionResult;
    }
}