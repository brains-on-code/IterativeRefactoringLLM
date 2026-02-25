package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Fast Fourier Transform (FFT) using Bluestein's algorithm.
 *
 * <p>More info:
 * <ul>
 *   <li>https://en.wikipedia.org/wiki/Chirp_Z-transform#Bluestein.27s_algorithm</li>
 *   <li>http://tka4.org/materials/lib/Articles-Books/Numerical%20Algorithms/Hartley_Trasform/Bluestein%27s%20FFT%20algorithm%20-%20Wikipedia,%20the%20free%20encyclopedia.htm</li>
 * </ul>
 */
public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the FFT (or inverse FFT) of the given sequence using Bluestein's algorithm.
     *
     * @param x       the input sequence; on return, contains the transformed values
     * @param inverse {@code true} to compute the inverse FFT, {@code false} for forward FFT
     */
    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        int n = x.size();
        int bnSize = 2 * n - 1;
        int direction = inverse ? -1 : 1;

        List<FFT.Complex> an = new ArrayList<>(n);
        List<FFT.Complex> bn = new ArrayList<>(bnSize);

        // Initialize b(n) sequence (chirp sequence, symmetric around center).
        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            double shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * direction;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bn.set(i, value);
            bn.set(bnSize - i - 1, value);
        }

        // Initialize a(n) sequence: input multiplied by chirp.
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            an.add(x.get(i).multiply(chirp));
        }

        // Convolution via FFT.
        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);

        // Multiply by conjugate chirp b*(k) to obtain final result.
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * direction;
            FFT.Complex bk = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            x.set(i, bk.multiply(convolution.get(i + n - 1)));
        }

        // Normalize for inverse transform.
        if (inverse) {
            for (int i = 0; i < n; i++) {
                x.set(i, x.get(i).divide(n));
            }
        }
    }
}