package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Computes the circular convolution of two discrete signals using the
 * convolution theorem:
 *
 * <pre>
 *   convolved = IDFT(DFT(a) * DFT(b))
 * </pre>
 *
 * Reference: https://en.wikipedia.org/wiki/Convolution_theorem
 */
public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given signal with zeros until its size reaches {@code newSize}.
     * If the signal is already at least {@code newSize} elements long, it is
     * left unchanged.
     *
     * @param signal  the signal to pad
     * @param newSize the desired size after padding
     */
    private static void padWithZeros(Collection<FFT.Complex> signal, int newSize) {
        int currentSize = signal.size();
        if (currentSize >= newSize) {
            return;
        }

        int zerosToAdd = newSize - currentSize;
        for (int i = 0; i < zerosToAdd; i++) {
            signal.add(new FFT.Complex());
        }
    }

    /**
     * Computes the discrete circular convolution of two signals using FFT.
     *
     * Steps:
     * <ol>
     *   <li>Zero-pad both signals to the same length:
     *       {@code max(a.size(), b.size())}.</li>
     *   <li>Compute the FFT of both padded signals.</li>
     *   <li>Multiply the FFT results element-wise.</li>
     *   <li>Apply the inverse FFT to obtain the circular convolution.</li>
     * </ol>
     *
     * Bluestein's FFT algorithm is used so the transform length matches the
     * (padded) signal length.
     *
     * @param a the first input signal
     * @param b the second input signal
     * @return the circularly convolved signal
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
            FFT.Complex product = a.get(i).multiply(b.get(i));
            convolved.add(product);
        }

        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}