package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Performs circular convolution of two discrete signals using the convolution theorem.
 *
 * <p>More info: https://en.wikipedia.org/wiki/Convolution_theorem
 */
public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given signal with zeros until it reaches the specified size.
     *
     * @param signal  the signal to be padded
     * @param newSize the desired size of the signal
     */
    private static void padWithZeros(Collection<FFT.Complex> signal, int newSize) {
        int currentSize = signal.size();
        if (currentSize >= newSize) {
            return;
        }

        int elementsToAdd = newSize - currentSize;
        for (int i = 0; i < elementsToAdd; i++) {
            signal.add(new FFT.Complex());
        }
    }

    /**
     * Computes the discrete circular convolution of two signals using the
     * convolution theorem:
     *
     * <pre>
     * convolved = IDFT(DFT(a) * DFT(b))
     * </pre>
     *
     * <p>FFT (Bluestein algorithm) is used so that the transform length matches
     * the signal length.
     *
     * @param firstSignal  the first input signal
     * @param secondSignal the second input signal
     * @return the circularly convolved signal
     */
    public static ArrayList<FFT.Complex> fftCircularConvolution(
            ArrayList<FFT.Complex> firstSignal,
            ArrayList<FFT.Complex> secondSignal) {

        int convolvedSize = Math.max(firstSignal.size(), secondSignal.size());

        padWithZeros(firstSignal, convolvedSize);
        padWithZeros(secondSignal, convolvedSize);

        FFTBluestein.fftBluestein(firstSignal, false);
        FFTBluestein.fftBluestein(secondSignal, false);

        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            FFT.Complex product = firstSignal.get(i).multiply(secondSignal.get(i));
            convolved.add(product);
        }

        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}