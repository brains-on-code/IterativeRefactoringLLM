package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Discrete Fourier Transform (DFT) of the given sequence using
     * Bluestein's algorithm (chirp-z transform). This reduces the DFT of an
     * arbitrary-length sequence to a convolution.
     *
     * @param x       input sequence (transformed in-place to output)
     * @param inverse if true, computes the inverse DFT; otherwise, the forward DFT
     */
    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        int n = x.size();
        int convolutionSize = 2 * n - 1;
        int direction = inverse ? -1 : 1;

        List<FFT.Complex> modulatedInput = new ArrayList<>(n);
        List<FFT.Complex> chirpKernel = new ArrayList<>(convolutionSize);

        // Pre-fill chirpKernel with zeros
        for (int i = 0; i < convolutionSize; i++) {
            chirpKernel.add(new FFT.Complex());
        }

        // Build symmetric chirp kernel:
        //   chirpKernel[k] = exp(i * direction * π * (k - n + 1)^2 / n),  k ∈ [0, 2n-2]
        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = direction * Math.PI * shiftedIndex * shiftedIndex / n;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpKernel.set(i, chirp);
            chirpKernel.set(convolutionSize - i - 1, chirp);
        }

        // Modulate input:
        //   modulatedInput[k] = x[k] * exp(-i * direction * π * k^2 / n)
        for (int i = 0; i < n; i++) {
            double angle = -direction * Math.PI * i * i / n;
            FFT.Complex modulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedInput.add(x.get(i).multiply(modulation));
        }

        // Convolution via FFT
        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(modulatedInput, chirpKernel);

        // Demodulate and write result back into x:
        //   X[k] = exp(-i * direction * π * k^2 / n) * convolution[k + n - 1]
        for (int i = 0; i < n; i++) {
            double angle = -direction * Math.PI * i * i / n;
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