package com.thealgorithms.audiofilters;

public class IIRFilter {

    private final int order;
    private final double[] denominatorCoeffs; // a[0..order]
    private final double[] numeratorCoeffs;   // b[0..order]
    private final double[] inputHistory;      // x[n-1], x[n-2], ...
    private final double[] outputHistory;     // y[n-1], y[n-2], ...

    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.denominatorCoeffs = new double[order + 1];
        this.numeratorCoeffs = new double[order + 1];

        // Default to identity filter: y[n] = x[n]
        this.denominatorCoeffs[0] = 1.0;
        this.numeratorCoeffs[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the IIR filter coefficients.
     *
     * @param aCoeffs Denominator coefficients a[0..order]; a[0] must be non-zero.
     * @param bCoeffs Numerator coefficients b[0..order].
     */
    public void setCoeffs(double[] aCoeffs, double[] bCoeffs) {
        validateCoeffsLength(aCoeffs, "aCoeffs");
        validateCoeffsLength(bCoeffs, "bCoeffs");

        if (aCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("aCoeffs[0] must not be zero");
        }

        System.arraycopy(aCoeffs, 0, denominatorCoeffs, 0, order + 1);
        System.arraycopy(bCoeffs, 0, numeratorCoeffs, 0, order + 1);
    }

    private void validateCoeffsLength(double[] coeffs, String name) {
        if (coeffs.length != order + 1) {
            throw new IllegalArgumentException(
                name + " must be of size " + (order + 1) + ", got " + coeffs.length
            );
        }
    }

    /**
     * Processes a single input sample and returns the filtered output.
     *
     * Direct-form I IIR filter:
     * y[n] = (b0*x[n] + Σ_{i=1..order}(b_i*x[n-i] - a_i*y[n-i])) / a0
     *
     * @param sample current input sample x[n]
     * @return filtered output sample y[n]
     */
    public double process(double sample) {
        double accumulator = 0.0;

        for (int i = 1; i <= order; i++) {
            accumulator += numeratorCoeffs[i] * inputHistory[i - 1]
                         - denominatorCoeffs[i] * outputHistory[i - 1];
        }

        double result = (accumulator + numeratorCoeffs[0] * sample) / denominatorCoeffs[0];

        shiftHistoryBuffers();

        inputHistory[0] = sample;
        outputHistory[0] = result;

        return result;
    }

    private void shiftHistoryBuffers() {
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }
    }
}