package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for circular convolution of two discrete signals using the convolution
 * theorem.
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

        // Ensure both signals have the same length via zero-padding
        padWithZeros(firstSignal, convolvedSize);
        padWithZeros(secondSignal, convolvedSize);

        // Compute FFT of both signals using Bluestein algorithm
        FFTBluestein.fftBluestein(firstSignal, false);
        FFTBluestein.fftBluestein(secondSignal, false);

        // Point-wise multiplication in frequency domain
        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            FFT.Complex product = firstSignal.get(i).multiply(secondSignal.get(i));
            convolved.add(product);
        }

        // Inverse FFT to obtain circular convolution result
        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}