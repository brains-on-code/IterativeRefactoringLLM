package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation
    }

    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        int n = x.size();
        int bnSize = 2 * n - 1;
        int direction = inverse ? -1 : 1;

        List<FFT.Complex> an = new ArrayList<>(n);
        List<FFT.Complex> bn = new ArrayList<>(bnSize);

        // Initialize bn with zeros
        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        // Precompute chirp sequence for bn
        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bn.set(i, chirp);
            bn.set(bnSize - i - 1, chirp);
        }

        // Precompute chirp-modulated input sequence an
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            an.add(x.get(i).multiply(chirp));
        }

        // Perform convolution via FFT
        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);

        // Post-process result and write back to x
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            x.set(i, chirp.multiply(convolution.get(i + n - 1)));
        }

        // Normalize for inverse transform
        if (inverse) {
            for (int i = 0; i < n; i++) {
                x.set(i, x.get(i).divide(n));
            }
        }
    }
}