package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for mathematical operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Applies a chirp-z-like transform using FFT-based convolution.
     *
     * @param data   the list of complex input samples (modified in place)
     * @param invert whether to apply the inverse transform
     */
    public static void method1(List<FFT.Complex> data, boolean invert) {
        int n = data.size();
        int convSize = 2 * n - 1;
        int sign = invert ? -1 : 1;

        List<FFT.Complex> chirp = new ArrayList<>(convSize);
        List<FFT.Complex> preMultiplied = new ArrayList<>(n);

        // Initialize chirp list with placeholder values
        for (int i = 0; i < convSize; i++) {
            chirp.add(new FFT.Complex());
        }

        // Build symmetric chirp sequence
        for (int i = 0; i < n; i++) {
            double angle = (i - n + 1.0) * (i - n + 1.0) * Math.PI / n * sign;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            chirp.set(i, value);
            chirp.set(convSize - i - 1, value);
        }

        // Pre-multiply input by quadratic phase
        for (int i = 0; i < n; i++) {
            double angle = -i * (double) i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            preMultiplied.add(data.get(i).multiply(phase));
        }

        // Convolution via FFT
        List<FFT.Complex> convolved = ConvolutionFFT.convolutionFFT(preMultiplied, chirp);

        // Post-multiply and write back to input list
        for (int i = 0; i < n; i++) {
            double angle = -i * (double) i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            data.set(i, phase.multiply(convolved.get(i + n - 1)));
        }

        // Normalize for inverse transform
        if (invert) {
            for (int i = 0; i < n; i++) {
                data.set(i, data.get(i).divide(n));
            }
        }
    }
}