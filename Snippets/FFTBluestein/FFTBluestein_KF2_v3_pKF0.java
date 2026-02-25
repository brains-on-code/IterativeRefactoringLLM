package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
        // Utility class; prevent instantiation
    }

    public static void fftBluestein(List<FFT.Complex> input, boolean inverse) {
        int n = input.size();
        int convolutionSize = 2 * n - 1;
        int direction = inverse ? -1 : 1;
        double baseAngle = Math.PI * direction / n;

        List<FFT.Complex> modulatedInput = new ArrayList<>(n);
        List<FFT.Complex> chirpSequence = new ArrayList<>(convolutionSize);

        fillWithZeros(chirpSequence, convolutionSize);
        buildChirpSequence(chirpSequence, n, convolutionSize, baseAngle);
        buildModulatedInput(modulatedInput, input, n, baseAngle);

        List<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(modulatedInput, chirpSequence);
        applyPostProcessing(input, convolution, n, baseAngle);

        if (inverse) {
            normalizeInverse(input, n);
        }
    }

    private static void fillWithZeros(List<FFT.Complex> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new FFT.Complex());
        }
    }

    private static void buildChirpSequence(
            List<FFT.Complex> chirpSequence, int n, int size, double baseAngle) {
        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);

            chirpSequence.set(i, chirp);
            chirpSequence.set(size - i - 1, chirp);
        }
    }

    private static void buildModulatedInput(
            List<FFT.Complex> modulatedInput, List<FFT.Complex> input, int n, double baseAngle) {
        for (int i = 0; i < n; i++) {
            double angle = -i * i * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);
            modulatedInput.add(input.get(i).multiply(chirp));
        }
    }

    private static void applyPostProcessing(
            List<FFT.Complex> output, List<FFT.Complex> convolution, int n, double baseAngle) {
        for (int i = 0; i < n; i++) {
            double angle = -i * i * baseAngle;
            FFT.Complex chirp = complexFromAngle(angle);
            output.set(i, chirp.multiply(convolution.get(i + n - 1)));
        }
    }

    private static void normalizeInverse(List<FFT.Complex> data, int n) {
        for (int i = 0; i < n; i++) {
            data.set(i, data.get(i).divide(n));
        }
    }

    private static FFT.Complex complexFromAngle(double angle) {
        return new FFT.Complex(Math.cos(angle), Math.sin(angle));
    }
}