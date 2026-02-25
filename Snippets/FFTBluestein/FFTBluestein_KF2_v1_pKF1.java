package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
    }

    public static void fftBluestein(List<FFT.Complex> input, boolean inverse) {
        int size = input.size();
        int convolutionSize = 2 * size - 1;
        int direction = inverse ? -1 : 1;

        List<FFT.Complex> aSequence = new ArrayList<>();
        List<FFT.Complex> bSequence = new ArrayList<>();

        for (int i = 0; i < convolutionSize; i++) {
            bSequence.add(new FFT.Complex());
        }

        for (int i = 0; i < size; i++) {
            int shiftedIndex = i - size + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / size * direction;
            FFT.Complex chirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bSequence.set(i, chirp);
            bSequence.set(convolutionSize - i - 1, chirp);
        }

        for (int i = 0; i < size; i++) {
            double angle = -1.0 * i * i * Math.PI / size * direction;
            FFT.Complex modulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            aSequence.add(input.get(i).multiply(modulation));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(aSequence, bSequence);

        for (int i = 0; i < size; i++) {
            double angle = -1.0 * i * i * Math.PI / size * direction;
            FFT.Complex demodulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            input.set(i, demodulation.multiply(convolutionResult.get(i + size - 1)));
        }

        if (inverse) {
            for (int i = 0; i < size; i++) {
                FFT.Complex value = input.get(i);
                input.set(i, value.divide(size));
            }
        }
    }
}