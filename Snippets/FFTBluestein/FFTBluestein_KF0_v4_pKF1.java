package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for calculating the Fast Fourier Transform (FFT) of a discrete signal
 * using Bluestein's algorithm.
 */
public final class FFTBluestein {

    private FFTBluestein() {
    }

    /**
     * Bluestein's FFT Algorithm.
     *
     * <p>
     * More info:
     * https://en.wikipedia.org/wiki/Chirp_Z-transform#Bluestein.27s_algorithm
     * http://tka4.org/materials/lib/Articles-Books/Numerical%20Algorithms/Hartley_Trasform/Bluestein%27s%20FFT%20algorithm%20-%20Wikipedia,%20the%20free%20encyclopedia.htm
     *
     * @param signal The discrete signal which is then converted to the FFT or the
     *               IFFT of signal.
     * @param inverse True if you want to find the inverse FFT.
     */
    public static void fftBluestein(List<FFT.Complex> signal, boolean inverse) {
        int signalLength = signal.size();
        int chirpSequenceLength = 2 * signalLength - 1;
        int transformSign = inverse ? -1 : 1;

        List<FFT.Complex> modulatedSignal = new ArrayList<>();
        List<FFT.Complex> chirpSequence = new ArrayList<>();

        // Initialize chirp sequence b(k)
        for (int i = 0; i < chirpSequenceLength; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int signalIndex = 0; signalIndex < signalLength; signalIndex++) {
            int shiftedIndex = signalIndex - signalLength + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / signalLength * transformSign;
            FFT.Complex chirpValue = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(signalIndex, chirpValue);
            chirpSequence.set(chirpSequenceLength - signalIndex - 1, chirpValue);
        }

        // Initialize modulated input sequence a(n)
        for (int signalIndex = 0; signalIndex < signalLength; signalIndex++) {
            double angle = -signalIndex * signalIndex * Math.PI / signalLength * transformSign;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedSignal.add(signal.get(signalIndex).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(modulatedSignal, chirpSequence);

        // Final multiplication of the convolution with the conjugate chirp factor b*(k)
        for (int signalIndex = 0; signalIndex < signalLength; signalIndex++) {
            double angle = -signalIndex * signalIndex * Math.PI / signalLength * transformSign;
            FFT.Complex conjugateChirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            signal.set(signalIndex, conjugateChirpFactor.multiply(convolutionResult.get(signalIndex + signalLength - 1)));
        }

        // Normalize by n if inverse FFT is requested
        if (inverse) {
            for (int signalIndex = 0; signalIndex < signalLength; signalIndex++) {
                FFT.Complex normalizedValue = signal.get(signalIndex).divide(signalLength);
                signal.set(signalIndex, normalizedValue);
            }
        }
    }
}