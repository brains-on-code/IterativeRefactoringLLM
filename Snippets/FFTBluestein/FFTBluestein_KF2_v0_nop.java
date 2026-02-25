package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;


public final class FFTBluestein {
    private FFTBluestein() {
    }


    public static void fftBluestein(List<FFT.Complex> x, boolean inverse) {
        int n = x.size();
        int bnSize = 2 * n - 1;
        int direction = inverse ? -1 : 1;
        ArrayList<FFT.Complex> an = new ArrayList<>();
        ArrayList<FFT.Complex> bn = new ArrayList<>();


        for (int i = 0; i < bnSize; i++) {
            bn.add(new FFT.Complex());
        }

        for (int i = 0; i < n; i++) {
            double angle = (i - n + 1) * (i - n + 1) * Math.PI / n * direction;
            bn.set(i, new FFT.Complex(Math.cos(angle), Math.sin(angle)));
            bn.set(bnSize - i - 1, new FFT.Complex(Math.cos(angle), Math.sin(angle)));
        }


        for (int i = 0; i < n; i++) {
            double angle = -i * i * Math.PI / n * direction;
            an.add(x.get(i).multiply(new FFT.Complex(Math.cos(angle), Math.sin(angle))));
        }

        ArrayList<FFT.Complex> convolution = ConvolutionFFT.convolutionFFT(an, bn);


        for (int i = 0; i < n; i++) {
            double angle = -1 * i * i * Math.PI / n * direction;
            FFT.Complex bk = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            x.set(i, bk.multiply(convolution.get(i + n - 1)));
        }


        if (inverse) {
            for (int i = 0; i < n; i++) {
                FFT.Complex z = x.get(i);
                x.set(i, z.divide(n));
            }
        }
    }
}
