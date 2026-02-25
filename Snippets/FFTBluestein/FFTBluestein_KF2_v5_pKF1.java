package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
    }

    public static void fftBluestein(List<FFT.Complex> signal, boolean inverse) {
        int n = signal.size();
        int m = 2 * n - 1;
        int sign = inverse ? -1 : 1;

        List<FFT.Complex> modulatedSignal = new ArrayList<>();
        List<FFT.Complex> chirp = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            chirp.add(new FFT.Complex());
        }

        for (int k = 0; k < n; k++) {
            int shiftedIndex = k - n + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / n * sign;
            FFT.Complex chirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirp.set(k, chirpFactor);
            chirp.set(m - k - 1, chirpFactor);
        }

        for (int k = 0; k < n; k++) {
            double angle = -1.0 * k * k * Math.PI / n * sign;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedSignal.add(signal.get(k).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(modulatedSignal, chirp);

        for (int k = 0; k < n; k++) {
            double angle = -1.0 * k * k * Math.PI / n * sign;
            FFT.Complex demodulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            signal.set(k, demodulationFactor.multiply(convolutionResult.get(k + n - 1)));
        }

        if (inverse) {
            for (int k = 0; k < n; k++) {
                FFT.Complex value = signal.get(k);
                signal.set(k, value.divide(n));
            }
        }
    }
}