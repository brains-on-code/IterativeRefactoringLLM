package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for circular convolution of two discrete signals using the convolution
 * theorem.
 *
 * @author Ioannis Karavitsis
 * @version 1.0
 */
public final class CircularConvolutionFFT {

    private CircularConvolutionFFT() {
    }

    /**
     * Pads the given signal with zeros until it reaches the specified size.
     *
     * @param signal the signal to be padded
     * @param targetSize the desired size of the signal
     */
    private static void padWithZeros(Collection<FFT.Complex> signal, int targetSize) {
        if (signal.size() < targetSize) {
            int zerosToAdd = targetSize - signal.size();
            for (int i = 0; i < zerosToAdd; i++) {
                signal.add(new FFT.Complex());
            }
        }
    }

    /**
     * Computes the discrete circular convolution of two signals using the
     * convolution theorem: result = IDFT(DFT(firstSignal) * DFT(secondSignal)).
     * The FFT algorithm (Bluestein) is used for efficient computation.
     *
     * <p>
     * More info: https://en.wikipedia.org/wiki/Convolution_theorem
     *
     * @param firstSignal the first input signal
     * @param secondSignal the second input signal
     * @return the circularly convolved signal
     */
    public static ArrayList<FFT.Complex> fftCircularConvolution(
            ArrayList<FFT.Complex> firstSignal,
            ArrayList<FFT.Complex> secondSignal
    ) {
        int maxSignalLength = Math.max(firstSignal.size(), secondSignal.size());
        padWithZeros(firstSignal, maxSignalLength);
        padWithZeros(secondSignal, maxSignalLength);

        FFTBluestein.fftBluestein(firstSignal, false);
        FFTBluestein.fftBluestein(secondSignal, false);

        ArrayList<FFT.Complex> frequencyDomainProduct = new ArrayList<>();

        for (int i = 0; i < firstSignal.size(); i++) {
            FFT.Complex firstFrequencyComponent = firstSignal.get(i);
            FFT.Complex secondFrequencyComponent = secondSignal.get(i);
            frequencyDomainProduct.add(firstFrequencyComponent.multiply(secondFrequencyComponent));
        }

        FFTBluestein.fftBluestein(frequencyDomainProduct, true);

        return frequencyDomainProduct;
    }
}