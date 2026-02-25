package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Discrete Fourier Transform (DFT) of the given sequence using
     * Bluestein's algorithm (chirp-z transform), which reduces the DFT to a
     * convolution and allows handling arbitrary (non–power-of-two) lengths.
     *
     * @param x       input sequence (in-place transformed to output)
     * @param inverse if true, computes the inverse DFT; otherwise, the forward DFT
     */
    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        int n = x.size();
        int bnSize = 2 * n - 1; // length of convolution kernel
        int direction = inverse ? -1 : 1;

        List<FFT.Complex> an = new ArrayList<>(n);
        List<FFT.Complex> bn = new ArrayList<>(bnSize);

        // Initialize bn with zeros
        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        // Precompute chirp sequence for bn:
        // bn[k] = exp(i * direction * π * (k - n + 1)^2 / n) for k in [0, 2n-2],
        // symmetric around the center.
        for (int i = 0; i < n; i++) {
            int shifted = i - n + 1;
            double angle = shifted * shifted * Math.PI / n * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bn.set(i, chirp);
            bn.set(bnSize - i - 1, chirp);
        }

        // Precompute modulated input sequence an:
        // an[k] = x[k] * exp(-i * direction * π * k^2 / n)
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * direction;
            FFT.Complex modulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            an.add(x.get(i).multiply(modulation));
        }

        // Perform convolution via FFT
        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);

        // Demodulate and extract the DFT result:
        // X[k] = exp(-i * direction * π * k^2 / n) * convolution[k + n - 1]
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * direction;
            FFT.Complex demodulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            x.set(i, demodulation.multiply(convolution.get(i + n - 1)));
        }

        // Normalize for inverse transform
        if (inverse) {
            for (int i = 0; i < n; i++) {
                x.set(i, x.get(i).divide(n));
            }
        }
    }
}