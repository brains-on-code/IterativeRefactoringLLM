package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for performing chirp z-transform–style preprocessing
 * using FFT-based convolution.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Applies a quadratic-phase modulation, performs convolution via FFT,
     * and optionally normalizes the result in-place on the provided list.
     *
     * @param input  list of complex values to be transformed (modified in-place)
     * @param invert if true, performs the inverse-style operation (including normalization)
     */
    public static void method1(List<FFT.Complex> input, boolean invert) {
        int n = input.size();
        int convSize = 2 * n - 1;
        int sign = invert ? -1 : 1;

        List<FFT.Complex> modulatedInput = new ArrayList<>();
        List<FFT.Complex> chirp = new ArrayList<>();

        // Initialize chirp list with placeholder values
        for (int i = 0; i < convSize; i++) {
            chirp.add(new FFT.Complex());
        }

        // Build symmetric chirp sequence
        for (int i = 0; i < n; i++) {
            double angle = (i - n + 1) * (i - n + 1) * Math.PI / n * sign;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            chirp.set(i, value);
            chirp.set(convSize - i - 1, value);
        }

        // Apply quadratic-phase modulation to input
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedInput.add(input.get(i).multiply(phase));
        }

        // Convolution via FFT
        List<FFT.Complex> convolved = ConvolutionFFT.convolutionFFT(modulatedInput, chirp);

        // Demodulate and write back to input
        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            input.set(i, phase.multiply(convolved.get(i + n - 1)));
        }

        // Normalize for inverse operation
        if (invert) {
            for (int i = 0; i < n; i++) {
                input.set(i, input.get(i).divide(n));
            }
        }
    }
}