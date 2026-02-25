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
        int convolutionSize = 2 * n - 1;
        int sign = invert ? -1 : 1;

        List<FFT.Complex> modulatedInput = new ArrayList<>(n);
        List<FFT.Complex> chirp = new ArrayList<>(convolutionSize);

        initializeChirp(chirp, convolutionSize);
        buildSymmetricChirp(chirp, n, convolutionSize, sign);
        applyQuadraticPhaseModulation(input, modulatedInput, n, sign);

        List<FFT.Complex> convolved = ConvolutionFFT.convolutionFFT(modulatedInput, chirp);

        demodulateAndStore(input, convolved, n, sign);

        if (invert) {
            normalize(input, n);
        }
    }

    private static void initializeChirp(List<FFT.Complex> chirp, int size) {
        for (int i = 0; i < size; i++) {
            chirp.add(new FFT.Complex());
        }
    }

    private static void buildSymmetricChirp(
            List<FFT.Complex> chirp, int n, int convolutionSize, int sign) {

        for (int i = 0; i < n; i++) {
            double shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * sign;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirp.set(i, value);
            chirp.set(convolutionSize - i - 1, value);
        }
    }

    private static void applyQuadraticPhaseModulation(
            List<FFT.Complex> input, List<FFT.Complex> modulatedInput, int n, int sign) {

        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedInput.add(input.get(i).multiply(phase));
        }
    }

    private static void demodulateAndStore(
            List<FFT.Complex> input, List<FFT.Complex> convolved, int n, int sign) {

        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * sign;
            FFT.Complex phase = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            input.set(i, phase.multiply(convolved.get(i + n - 1)));
        }
    }

    private static void normalize(List<FFT.Complex> input, int n) {
        for (int i = 0; i < n; i++) {
            input.set(i, input.get(i).divide(n));
        }
    }
}