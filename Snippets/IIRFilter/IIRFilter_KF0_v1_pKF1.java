package com.thealgorithms.audiofilters;

/**
 * N-Order IIR Filter Assumes inputs are normalized to [-1, 1]
 *
 * Based on the difference equation from
 * <a href="https://en.wikipedia.org/wiki/Infinite_impulse_response">Wikipedia link</a>
 */
public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients;
    private final double[] numeratorCoefficients;
    private final double[] inputHistory;
    private final double[] outputHistory;

    /**
     * Construct an IIR Filter
     *
     * @param filterOrder the filter's order
     * @throws IllegalArgumentException if filterOrder is zero or less
     */
    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("filterOrder must be greater than zero");
        }

        this.filterOrder = filterOrder;
        denominatorCoefficients = new double[filterOrder + 1];
        numeratorCoefficients = new double[filterOrder + 1];

        // Sane defaults
        denominatorCoefficients[0] = 1.0;
        numeratorCoefficients[0] = 1.0;

        inputHistory = new double[filterOrder];
        outputHistory = new double[filterOrder];
    }

    /**
     * Set coefficients
     *
     * @param denominatorCoeffs Denominator coefficients (a-coefficients)
     * @param numeratorCoeffs   Numerator coefficients (b-coefficients)
     * @throws IllegalArgumentException if {@code denominatorCoeffs} or {@code numeratorCoeffs} is
     *                                  not of size {@code filterOrder}, or if {@code denominatorCoeffs[0]} is 0.0
     */
    public void setCoefficients(double[] denominatorCoeffs, double[] numeratorCoeffs) throws IllegalArgumentException {
        if (denominatorCoeffs.length != filterOrder) {
            throw new IllegalArgumentException(
                "denominatorCoeffs must be of size " + filterOrder + ", got " + denominatorCoeffs.length
            );
        }

        if (denominatorCoeffs[0] == 0.0) {
            throw new IllegalArgumentException("denominatorCoeffs[0] must not be zero");
        }

        if (numeratorCoeffs.length != filterOrder) {
            throw new IllegalArgumentException(
                "numeratorCoeffs must be of size " + filterOrder + ", got " + numeratorCoeffs.length
            );
        }

        for (int i = 0; i < filterOrder; i++) {
            denominatorCoefficients[i] = denominatorCoeffs[i];
            numeratorCoefficients[i] = numeratorCoeffs[i];
        }
    }

    /**
     * Process a single sample
     *
     * @param inputSample the sample to process
     * @return the processed sample
     */
    public double process(double inputSample) {
        double outputSample = 0.0;

        // Process
        for (int i = 1; i <= filterOrder; i++) {
            outputSample += (numeratorCoefficients[i] * inputHistory[i - 1]
                - denominatorCoefficients[i] * outputHistory[i - 1]);
        }
        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        // Feedback (update history)
        for (int i = filterOrder - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}