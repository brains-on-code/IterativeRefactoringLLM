package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter implementation.
 *
 * The filter is defined by its order and its numerator (b) and denominator (a)
 * coefficients. The output is computed using the standard IIR difference
 * equation.
 */
public class IIRFilter {

    private final int filterOrder;
    private final double[] denominatorCoefficients; // a-coefficients
    private final double[] numeratorCoefficients;   // b-coefficients
    private final double[] inputHistory;
    private final double[] outputHistory;

    /**
     * Constructs an IIR filter of the given order.
     *
     * @param filterOrder the order of the filter (must be >= 1)
     * @throws IllegalArgumentException if filterOrder is less than 1
     */
    public IIRFilter(int filterOrder) throws IllegalArgumentException {
        if (filterOrder < 1) {
            throw new IllegalArgumentException("order must be greater than zero");
        }

        this.filterOrder = filterOrder;
        this.denominatorCoefficients = new double[filterOrder + 1];
        this.numeratorCoefficients = new double[filterOrder + 1];

        denominatorCoefficients[0] = 1.0;
        numeratorCoefficients[0] = 1.0;

        this.inputHistory = new double[filterOrder];
        this.outputHistory = new double[filterOrder];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param aCoefficients the denominator coefficients (a), length must be filterOrder + 1
     * @param bCoefficients the numerator coefficients (b), length must be filterOrder + 1
     * @throws IllegalArgumentException if the lengths are incorrect or aCoefficients[0] is zero
     */
    public void setCoefficients(double[] aCoefficients, double[] bCoefficients) throws IllegalArgumentException {
        if (aCoefficients.length != filterOrder + 1) {
            throw new IllegalArgumentException(
                "aCoefficients must be of size " + (filterOrder + 1) + ", got " + aCoefficients.length
            );
        }

        if (aCoefficients[0] == 0.0) {
            throw new IllegalArgumentException("aCoefficients[0] must not be zero");
        }

        if (bCoefficients.length != filterOrder + 1) {
            throw new IllegalArgumentException(
                "bCoefficients must be of size " + (filterOrder + 1) + ", got " + bCoefficients.length
            );
        }

        System.arraycopy(aCoefficients, 0, this.denominatorCoefficients, 0, filterOrder + 1);
        System.arraycopy(bCoefficients, 0, this.numeratorCoefficients, 0, filterOrder + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        for (int i = 1; i <= filterOrder; i++) {
            outputSample += numeratorCoefficients[i] * inputHistory[i - 1]
                - denominatorCoefficients[i] * outputHistory[i - 1];
        }
        outputSample = (outputSample + numeratorCoefficients[0] * inputSample) / denominatorCoefficients[0];

        for (int i = filterOrder - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}