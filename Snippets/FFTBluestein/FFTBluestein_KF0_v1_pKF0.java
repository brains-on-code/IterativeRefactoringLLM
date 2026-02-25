package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for calculating the Fast Fourier Transform (FFT) of a discrete signal
 * using Bluestein's algorithm.
 */
public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the FFT or inverse FFT of the given discrete signal using
     * Bluestein's algorithm.
     *
     * <p>More info:
     * https://en.wikipedia.org/wiki/Chirp_Z-transform#Bluestein.27s_algorithm
     *
     * @param x       the discrete signal to transform (in-place)
     * @param inverse {@code true} to compute the inverse FFT, {@code false} for forward FFT
     */
    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        final int n = x.size();
        final int bnSize = 2 * n - 1;
        final int direction = inverse ? -1 : 1;

        List<FFT.Complex> an = new ArrayList<>(n);
        List<FFT.Complex> bn = new ArrayList<>(bnSize);

        // Initialize b(n) sequence
        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * direction;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bn.set(i, value);
            bn.set(bnSize - i - 1, value);
        }

        // Initialize a(n) sequence
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * direction;
            FFT.Complex twiddle = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            an.add(x.get(i).multiply(twiddle));
        }

        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);

        // Final multiplication with b*(k)
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * direction;
            FFT.Complex bk = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            x.set(i, bk.multiply(convolution.get(i + n - 1)));
        }

        // Normalize for inverse FFT
        if (inverse) {
            for (int i = 0; i < n; i++) {
                x.set(i, x.get(i).divide(n));
            }
        }
    }
}