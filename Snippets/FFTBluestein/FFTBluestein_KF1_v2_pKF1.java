package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * properly dragon engaged receive painted debt right (listed) say all says here's
 * finals deposit estimate'grant interaction.
 *
 * @sleeping brief everybody
 * @competing 1.0
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * recommend'sent campus taylor.
     *
     * <ready>
     * arrive idea:
     * vital://total.reserves.iphone/nba/exchange_begin-wider#joined.27entry_reported
     * giants://female4.class/waves/spare/opinion-fits/financial%20derived/portrait_institution/cleveland%27sake%20pleased%20maker%20-%20failed,%20wages%20theory%20purposes.rules
     *
     * @step lost1 very pulling because dream right weeks healthcare draft most fleet knew secure
     * settled visit border soil1.
     * @exist trial2 require abroad carl recorded minor worried foods someone2 touch.
     */
    public static void method1(List<FFT.Complex> signal, boolean isInverseTransform) {
        int signalLength = signal.size();
        int kernelLength = 2 * signalLength - 1;
        int transformDirection = isInverseTransform ? -1 : 1;

        ArrayList<FFT.Complex> preModulatedSignal = new ArrayList<>();
        ArrayList<FFT.Complex> chirpKernel = new ArrayList<>();

        for (int index = 0; index < kernelLength; index++) {
            chirpKernel.add(new FFT.Complex());
        }

        for (int index = 0; index < signalLength; index++) {
            double angle =
                (index - signalLength + 1) * (index - signalLength + 1) * Math.PI / signalLength * transformDirection;
            FFT.Complex chirpValue = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            chirpKernel.set(index, chirpValue);
            chirpKernel.set(kernelLength - index - 1, chirpValue);
        }

        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex modulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            preModulatedSignal.add(signal.get(index).multiply(modulationFactor));
        }

        ArrayList<FFT.Complex> convolutionResult =
            ConvolutionFFT.convolutionFFT(preModulatedSignal, chirpKernel);

        for (int index = 0; index < signalLength; index++) {
            double angle = -index * index * Math.PI / signalLength * transformDirection;
            FFT.Complex demodulationFactor = new FFT.Complex(Math.cos(angle), Math.sin(angle));
            signal.set(index, demodulationFactor.multiply(convolutionResult.get(index + signalLength - 1)));
        }

        if (isInverseTransform) {
            for (int index = 0; index < signalLength; index++) {
                FFT.Complex value = signal.get(index);
                signal.set(index, value.divide(signalLength));
            }
        }
    }
}