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
        int size = data.size();
        int convolutionSize = 2 * size - 1;
        int transformSign = invert ? -1 : 1;

        List<FFT.Complex> chirpSequence = buildChirpSequence(size, convolutionSize, transformSign);
        List<FFT.Complex> premultipliedData = applyPreMultiplication(data, size, transformSign);

        List<FFT.Complex> convolved = ConvolutionFFT.convolutionFFT(premultipliedData, chirpSequence);

        applyPostMultiplication(data, convolved, size, transformSign);

        if (invert) {
            normalizeInverseTransform(data, size);
        }
    }

    private static List<FFT.Complex> buildChirpSequence(int size, int convolutionSize, int sign) {
        List<FFT.Complex> chirpSequence = new ArrayList<>(convolutionSize);

        for (int i = 0; i < convolutionSize; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int i = 0; i < size; i++) {
            double shiftedIndex = i - size + 1.0;
            double angle = shiftedIndex * shiftedIndex * Math.PI / size * sign;
            FFT.Complex value = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(i, value);
            chirpSequence.set(convolutionSize - i - 1, value);
        }

        return chirpSequence;
    }

    private static List<FFT.Complex> applyPreMultiplication(List<FFT.Complex> data, int size, int sign) {
        List<FFT.Complex> premultipliedData = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            FFT.Complex phase = quadraticPhase(i, size, -sign);
            premultipliedData.add(data.get(i).multiply(phase));
        }

        return premultipliedData;
    }

    private static void applyPostMultiplication(
            List<FFT.Complex> data, List<FFT.Complex> convolved, int size, int sign) {

        for (int i = 0; i < size; i++) {
            FFT.Complex phase = quadraticPhase(i, size, -sign);
            data.set(i, phase.multiply(convolved.get(i + size - 1)));
        }
    }

    private static void normalizeInverseTransform(List<FFT.Complex> data, int size) {
        for (int i = 0; i < size; i++) {
            data.set(i, data.get(i).divide(size));
        }
    }

    private static FFT.Complex quadraticPhase(int index, int size, int sign) {
        double angle = -index * (double) index * Math.PI / size * sign;
        return new FFT.Complex(Math.cos(angle), Math.sin(angle));
    }
}