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
        double baseAngle = Math.PI / n * direction;

        List<FFT.Complex> an = new ArrayList<>(n);
        List<FFT.Complex> bn = new ArrayList<>(bnSize);

        initializeWithZeros(bn, bnSize);
        precomputeChirpSequence(bn, n, bnSize, baseAngle);
        precomputeChirpModulatedInput(an, x, n, baseAngle);

        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);
        postProcessResult(x, convolution, n, baseAngle);

        if (inverse) {
            normalizeInverseTransform(x, n);
        }
    }

    private static void initializeWithZeros(List<FFT.Complex> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new FFT.Complex());
        }
    }

    private static void precomputeChirpSequence(
            List<FFT.Complex> bn, int n, int bnSize, double baseAngle) {
        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);

            bn.set(i, chirp);
            bn.set(bnSize - i - 1, chirp);
        }
    }

    private static void precomputeChirpModulatedInput(
            List<FFT.Complex> an, List<FFT.Complex> x, int n, double baseAngle) {
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);
            an.add(x.get(i).multiply(chirp));
        }
    }

    private static void postProcessResult(
            List<FFT.Complex> x, List<FFT.Complex> convolution, int n, double baseAngle) {
        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);
            x.set(i, chirp.multiply(convolution.get(i + n - 1)));
        }
    }

    private static void normalizeInverseTransform(List<FFT.Complex> x, int n) {
        for (int i = 0; i < n; i++) {
            x.set(i, x.get(i).divide(n));
        }
    }

    private static FFT.Complex complexFromAngle(double angle) {
        return new FFT.Complex(Math.cos(angle), Math.sin(angle));
    }
}