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
        int transformDirection = inverse ? -1 : 1;

        List<FFT.Complex> modulatedInput = new ArrayList<>();
        List<FFT.Complex> chirpSequence = new ArrayList<>();

        // Initialize chirp sequence b(k)
        for (int i = 0; i < chirpSequenceLength; i++) {
            chirpSequence.add(new FFT.Complex());
        }

        for (int index = 0; index < signalLength; index++) {
            int shiftedIndex = index - signalLength + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / signalLength * transformDirection;
            FFT.Complex chirpValue = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            chirpSequence.set(index, chirpValue);
            chirpSequence.set(chirpSequenceLength - index - 1, chirpValue);
        }

        // Initialize modulated input sequence a(n)
        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            modulatedInput.add(signal.get(index).multiply(modulationFactor));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(modulatedInput, chirpSequence);

        // Final multiplication of the convolution with the conjugate chirp factor b*(k)
        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex conjugateChirpFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            signal.set(index, conjugateChirpFactor.multiply(convolutionResult.get(index + signalLength - 1)));
        }

        // Normalize by n if inverse FFT is requested
        if (inverse) {
            for (int index = 0; index < signalLength; index++) {
                FFT.Complex normalizedValue = signal.get(index).divide(signalLength);
                signal.set(index, normalizedValue);
            }
        }
    }
}