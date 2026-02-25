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

        List<FFT.Complex> aSequence = new ArrayList<>();
        List<FFT.Complex> bSequence = new ArrayList<>();

        // Initialize b(k) sequence (see Wikipedia article for notation)
        for (int i = 0; i < chirpSequenceLength; i++) {
            bSequence.add(new FFT.Complex());
        }

        for (int index = 0; index < signalLength; index++) {
            int shiftedIndex = index - signalLength + 1;
            double angle = shiftedIndex * shiftedIndex * Math.PI / signalLength * transformDirection;
            FFT.Complex chirpValue = new FFT.Complex(Math.cos(angle), Math.sin(angle));

            bSequence.set(index, chirpValue);
            bSequence.set(chirpSequenceLength - index - 1, chirpValue);
        }

        // Initialize a(n) sequence
        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex modulation = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            aSequence.add(signal.get(index).multiply(modulation));
        }

        List<FFT.Complex> convolutionResult = ConvolutionFFT.convolutionFFT(aSequence, bSequence);

        // Final multiplication of the convolution with the conjugate chirp factor b*(k)
        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex conjugateChirp = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            signal.set(index, conjugateChirp.multiply(convolutionResult.get(index + signalLength - 1)));
        }

        // Normalize by n if inverse FFT is requested
        if (inverse) {
            for (int index = 0; index < signalLength; index++) {
                FFT.Complex value = signal.get(index);
                signal.set(index, value.divide(signalLength));
            }
        }
    }
}