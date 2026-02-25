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

        initializeBnSequence(bn, n, bnSize, direction);
        initializeAnSequence(an, x, n, direction);

        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);

        applyFinalMultiplication(x, convolution, n, direction);

        if (inverse) {
            normalizeInverse(x, n);
        }
    }

    private static void initializeBnSequence(List<FFT.Complex> bn, int n, int bnSize, int direction) {
        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = computeAngle(shiftedIndex * shiftedIndex, n, direction);
            FFT.Complex value = complexFromAngle(angle);

            bn.set(i, value);
            bn.set(bnSize - i - 1, value);
        }
    }

    private static void initializeAnSequence(List<FFT.Complex> an, List<FFT.Complex> x, int n, int direction) {
        for (int i = 0; i < n; i++) {
            double angle = computeAngle(-1.0 * i * i, n, direction);
            FFT.Complex twiddle = complexFromAngle(angle);
            an.add(x.get(i).multiply(twiddle));
        }
    }

    private static void applyFinalMultiplication(List<FFT.Complex> x, List<FFT.Complex> convolution, int n, int direction) {
        for (int i = 0; i < n; i++) {
            double angle = computeAngle(-1.0 * i * i, n, direction);
            FFT.Complex bk = complexFromAngle(angle);
            x.set(i, bk.multiply(convolution.get(i + n - 1)));
        }
    }

    private static void normalizeInverse(List<FFT.Complex> x, int n) {
        for (int i = 0; i < n; i++) {
            x.set(i, x.get(i).divide(n));
        }
    }

    private static double computeAngle(double factor, int n, int direction) {
        return factor * Math.PI / n * direction;
    }

    private static FFT.Complex complexFromAngle(double angle) {
        return new FFT.Complex(Math.cos(angle), Math.sin(angle));
    }
}