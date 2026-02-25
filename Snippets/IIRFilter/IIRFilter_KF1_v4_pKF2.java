package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter.
 *
 * Implements:
 *   y[n] = (b0 * x[n] + b1 * x[n-1] + ... + bN * x[n-N]
 *          - a1 * y[n-1] - ... - aN * y[n-N]) / a0
 *
 * Coefficients are configured via {@link #setCoefficients(double[], double[])}.
 */
public class IIRFilter {

    /** Filter order (N in the equation above). */
    private final int order;

    /** Denominator (feedback) coefficients a[0..order]; a[0] must be non-zero. */
    private final double[] denominatorCoefficients;

    /** Numerator (feedforward) coefficients b[0..order]. */
    private final double[] numeratorCoefficients;

    /** Past input samples: x[n-1], x[n-2], ..., x[n-order]. */
    private final double[] inputHistory;

    /** Past output samples: y[n-1], y[n-2], ..., y[n-order]. */
    private final double[] outputHistory;

    /**
     * Creates an IIR filter of the given order.
     *
     * @param order filter order (must be >= 1)
     * @throws IllegalArgumentException if {@code order < 1}
     */
    public IIRFilter(int order) {
        if (order < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.order = order;
        this.denominatorCoefficients = new double[order + 1];
        this.numeratorCoefficients = new double[order + 1];

        // Default to a pass-through filter: a0 = 1, b0 = 1.
        this.denominatorCoefficients[0] = 1.0;
        this.numeratorCoefficients[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * Both arrays must have length {@code order + 1} and contain indices 0..order.
     *
     * @param denominatorCoefficients denominator (feedback) coefficients a[0..order];
     *                                a[0] must be non-zero
     * @param numeratorCoefficients   numerator (feedforward) coefficients b[0..order]
     * @throws IllegalArgumentException if the arrays are not of length {@code order + 1}
     *                                  or if {@code denominatorCoefficients[0] == 0.0}
     */
    public void setCoefficients(double[] denominatorCoefficients, double[] numeratorCoefficients) {
        validateCoefficientArrayLength(denominatorCoefficients, "denominatorCoefficients");
        validateCoefficientArrayLength(numeratorCoefficients, "numeratorCoefficients");

        if (denominatorCoefficients[0] == 0.0) {
            throw new IllegalArgumentException("denominatorCoefficients[0] must not be zero");
        }

        System.arraycopy(denominatorCoefficients, 0, this.denominatorCoefficients, 0, order + 1);
        System.arraycopy(numeratorCoefficients, 0, this.numeratorCoefficients, 0, order + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample current input sample x[n]
     * @return filtered output sample y[n]
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        // Contribution from past inputs and outputs (i = 1..order).
        for (int i = 1; i <= order; i++) {
            outputSample += numeratorCoefficients[i] * inputHistory[i - 1]
                    - denominatorCoefficients[i] * outputHistory[i - 1];
        }

        // Add current input contribution and normalize by a0.
        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        // Shift history: index 0 is most recent, index order-1 is oldest.
        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }

    private void validateCoefficientArrayLength(double[] coefficients, String name) {
        if (coefficients.length != order + 1) {
            throw new IllegalArgumentException(
                    name + " must be of size " + (order + 1) + ", got " + coefficients.length
            );
        }
    }
}