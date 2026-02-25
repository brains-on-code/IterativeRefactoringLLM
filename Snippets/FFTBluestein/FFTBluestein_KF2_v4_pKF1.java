package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class FFTBluestein {

    private FFTBluestein() {
    }

    public static void fftBluestein(List<FFT.Complex> inputSignal, boolean isInverseTransform) {
        int signalLength = inputSignal.size();
        int convolutionLength = 2 * signalLength - 1;
        int transformSign = isInverseTransform ? -1 : 1;

        List<FFT.Complex> modulatedSignal = new ArrayList<>();
        List<FFT.Complex> chirpSequence = new ArrayList<>();

        for (int i = 0; i < convolutionLength; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int index = 0; index < signalLength; index++) {
            int shiftedIndex = index - signalLength + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / signalLength * transformSign;
            FFT.Complex chirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(index, chirpFactor);
            chirpSequence.set(convolutionLength - index - 1, chirpFactor);
        }

        for (int index = 0; index < signalLength; index++) {
            double angle = -1.0 * index * index * Math.PI / signalLength * transformSign;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedSignal.add(inputSignal.get(index).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionOutput = ConvolutionFFT.convolutionFFT(modulatedSignal, chirpSequence);

        for (int index = 0; index < signalLength; index++) {
            double angle = -1.0 * index * index * Math.PI / signalLength * transformSign;
            FFT.Complex demodulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            inputSignal.set(index, demodulationFactor.multiply(convolutionOutput.get(index + signalLength - 1)));
        }

        if (isInverseTransform) {
            for (int index = 0; index < signalLength; index++) {
                FFT.Complex value = inputSignal.get(index);
                inputSignal.set(index, value.divide(signalLength));
            }
        }
    }
}