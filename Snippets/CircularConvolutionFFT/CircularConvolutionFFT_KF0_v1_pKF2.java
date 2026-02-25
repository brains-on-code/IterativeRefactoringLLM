package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Performs circular convolution of two discrete signals using the convolution theorem.
 *
 * <p>convolved = IDFT(DFT(a) * DFT(b))</p>
 *
 * <p>More info: https://en.wikipedia.org/wiki/Convolution_theorem</p>
 */
public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
        // Utility class; prevent instantiation
    }

    /**
     * Pads the given signal with zeros until it reaches {@code newSize}.
     *
     * @param signal  the signal to be padded
     * @param newSize the desired size after padding
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
     * Computes the discrete circular convolution of two signals using FFT.
     *
     * <p>The two input signals are zero-padded to the same length (the maximum of
     * their original lengths). The convolution is then computed via:
     * <pre>
     *   convolved = IFFT(FFT(a) * FFT(b))
     * </pre>
     * using the Bluestein FFT algorithm so that the transform length matches the
     * (possibly padded) signal length.</p>
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

        // Ensure both signals have the same length via zero-padding
        padWithZeros(a, convolvedSize);
        padWithZeros(b, convolvedSize);

        // Compute FFT of both signals using Bluestein so the length matches the signal length
        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);

        // Point-wise multiplication in the frequency domain
        ArrayList<FFT.Complex> convolved = new ArrayList<>(convolvedSize);
        for (int i = 0; i < convolvedSize; i++) {
            convolved.add(a.get(i).multiply(b.get(i)));
        }

        // Inverse FFT to obtain the circular convolution in the time domain
        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}