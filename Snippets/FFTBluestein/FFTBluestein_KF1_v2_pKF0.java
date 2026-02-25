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
        int convolutionSize = 2 * n - 1;
        int sign = invert ? -1 : 1;

        List<FFT.Complex> chirpSequence = initializeChirpSequence(n, convolutionSize, sign);
        List<FFT.Complex> preMultipliedData = preMultiplyInput(data, n, sign);

        List<FFT.Complex> convolved = ConvolutionFFT.convolutionFFT(preMultipliedData, chirpSequence);

        applyPostMultiplication(data, convolved, n, sign);

        if (invert) {
            normalizeInverseTransform(data, n);
        }
    }

    private static List<FFT.Complex> initializeChirpSequence(int n, int convolutionSize, int sign) {
        List<FFT.Complex> chirpSequence = new ArrayList<>(convolutionSize);

        for (int i = 0; i < convolutionSize; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            double indexShift = i - n + 1.0;
            double angle = indexShift * indexShift * Math.PI / n * sign;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(i, value);
            chirpSequence.set(convolutionSize - i - 1, value);
        }

        return chirpSequence;
    }

    private static List<FFT.Complex> preMultiplyInput(List<FFT.Complex> data, int n, int sign) {
        List<FFT.Complex> preMultipliedData = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            FFT.Complex phase = quadraticPhase(i, n, -sign);
            preMultipliedData.add(data.get(i).multiply(phase));
        }

        return preMultipliedData;
    }

    private static void applyPostMultiplication(
            List<FFT.Complex> data, List<FFT.Complex> convolved, int n, int sign) {

        for (int i = 0; i < n; i++) {
            FFT.Complex phase = quadraticPhase(i, n, -sign);
            data.set(i, phase.multiply(convolved.get(i + n - 1)));
        }
    }

    private static void normalizeInverseTransform(List<FFT.Complex> data, int n) {
        for (int i = 0; i < n; i++) {
            data.set(i, data.get(i).divide(n));
        }
    }

    private static FFT.Complex quadraticPhase(int index, int n, int sign) {
        double angle = -index * (double) index * Math.PI / n * sign;
        return new FFT.Complex(Math.cos(angle), Math.sin(angle));
    }
}