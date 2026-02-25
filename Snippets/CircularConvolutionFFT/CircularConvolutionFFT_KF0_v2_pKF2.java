package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Circular convolution of two discrete signals using the convolution theorem:
 *
 * <pre>
 *   convolved = IDFT(DFT(a) * DFT(b))
 * </pre>
 *
 * More info: https://en.wikipedia.org/wiki/Convolution_theorem
 */
public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Prevent instantiation
    }

    /**
     * Pads {@code signal} with zeros until its size is {@code newSize}.
     *
     * @param signal  signal to pad
     * @param newSize target size
     */
    private static void padWithZeros(Collection<FFT.Complex> signal, int newSize) {
        if (signal.size() >= newSize) {
            return;
        }
        int zerosToAdd = newSize - signal.size();
        for (int i = 0; i < zerosToAdd; i++) {
            signal.add(new FFT.Complex());
        }
    }

    /**
     * Computes the discrete circular convolution of two signals using FFT:
     *
     * <pre>
     *   1. Zero-pad both signals to the same length (max(a.size, b.size)).
     *   2. Compute FFT of both padded signals.
     *   3. Multiply the FFT results point-wise.
     *   4. Apply inverse FFT to obtain the circular convolution.
     * </pre>
     *
     * Bluestein's FFT algorithm is used so the transform length matches the (padded) signal length.
     *
     * @param a first input signal
     * @param b second input signal
     * @return circularly convolved signal
     */
    public static ArrayList<FFT.Complex> fftCircularConvolution(
            ArrayList<FFT.Complex> a,
            ArrayList<FFT.Complex> b
    ) {
        int convolvedSize = Math.max(a.size(), b.size());

        padWithZeros(a, convolvedSize);
        padWithZeros(b, convolvedSize);

        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);

        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            convolved.add(a.get(i).multiply(b.get(i)));
        }

        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}