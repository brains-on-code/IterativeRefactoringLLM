package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
    }

    public static void fftBluestein(List<FFT.Complex> data, boolean isInverseTransform) {
        int n = data.size();
        int convolutionLength = 2 * n - 1;
        int transformSign = isInverseTransform ? -1 : 1;

        List<FFT.Complex> modulatedData = new ArrayList<>();
        List<FFT.Complex> chirpSequence = new ArrayList<>();

        for (int i = 0; i < convolutionLength; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            int shiftedIndex = i - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * transformSign;
            FFT.Complex chirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(i, chirpFactor);
            chirpSequence.set(convolutionLength - i - 1, chirpFactor);
        }

        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * transformSign;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedData.add(data.get(i).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionOutput = ConvolutionFFT.convolutionFFT(modulatedData, chirpSequence);

        for (int i = 0; i < n; i++) {
            double angle = -1.0 * i * i * Math.PI / n * transformSign;
            FFT.Complex demodulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            data.set(i, demodulationFactor.multiply(convolutionOutput.get(i + n - 1)));
        }

        if (isInverseTransform) {
            for (int i = 0; i < n; i++) {
                FFT.Complex value = data.get(i);
                data.set(i, value.divide(n));
            }
        }
    }
}