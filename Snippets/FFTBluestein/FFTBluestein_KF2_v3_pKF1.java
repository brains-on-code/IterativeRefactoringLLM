package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
    }

    public static void fftBluestein(List<FFT.Complex> input, boolean inverse) {
        int size = input.size();
        int convolutionSize = 2 * size - 1;
        int sign = inverse ? -1 : 1;

        List<FFT.Complex> modulatedInput = new ArrayList<>();
        List<FFT.Complex> chirp = new ArrayList<>();

        for (int i = 0; i < convolutionSize; i++) {
            chirp.add(new FFT.Complex());
        }

        for (int i = 0; i < size; i++) {
            int shiftedIndex = i - size + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / size * sign;
            FFT.Complex chirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirp.set(i, chirpFactor);
            chirp.set(convolutionSize - i - 1, chirpFactor);
        }

        for (int i = 0; i < size; i++) {
            double angle = -1.0 * i * i * Math.PI / size * sign;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedInput.add(input.get(i).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(modulatedInput, chirp);

        for (int i = 0; i < size; i++) {
            double angle = -1.0 * i * i * Math.PI / size * sign;
            FFT.Complex demodulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            input.set(i, demodulationFactor.multiply(convolutionResult.get(i + size - 1)));
        }

        if (inverse) {
            for (int i = 0; i < size; i++) {
                FFT.Complex value = input.get(i);
                input.set(i, value.divide(size));
            }
        }
    }
}