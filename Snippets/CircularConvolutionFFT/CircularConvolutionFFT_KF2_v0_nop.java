package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collection;


public final class CircularConvolutionFFT {
    private CircularConvolutionFFT() {
    }


    private static void padding(Collection<FFT.Complex> x, int newSize) {
        if (x.size() < newSize) {
            int diff = newSize - x.size();
            for (int i = 0; i < diff; i++) {
                x.add(new FFT.Complex());
            }
        }
    }


    public static ArrayList<FFT.Complex> fftCircularConvolution(ArrayList<FFT.Complex> a, ArrayList<FFT.Complex> b) {
        int convolvedSize = Math.max(a.size(), b.size());
        padding(a, convolvedSize);
        padding(b, convolvedSize);


        FFTBluestein.fftBluestein(a, false);
        FFTBluestein.fftBluestein(b, false);
        ArrayList<FFT.Complex> convolved = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            convolved.add(a.get(i).multiply(b.get(i)));
        }
        FFTBluestein.fftBluestein(convolved, true);

        return convolved;
    }
}
