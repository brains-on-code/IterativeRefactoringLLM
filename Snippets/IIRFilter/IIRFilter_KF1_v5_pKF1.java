package com.thealgorithms.audiofilters;

/**
 * Infinite Impulse Response (IIR) filter implementation.
 *
 * The filter is defined by its order and its numerator (b) and denominator (a)
 * coefficients. The output is computed using the standard IIR difference
 * equation.
 */
public class IIRFilter {

    private final int order;
    private final double[] denominator; // a-coefficients
    private final double[] numerator;   // b-coefficients
    private final double[] inputHistory;
    private final double[] outputHistory;

    /**
     * Constructs an IIR filter of the given order.
     *
     * @param order the order of the filter (must be >= 1)
     * @throws IllegalArgumentException if order is less than 1
     */
    public IIRFilter(int order) throws IllegalArgumentException {
        if (order < 1) {
            throw new IllegalArgumentException("Filter order must be greater than zero");
        }

        this.order = order;
        this.denominator = new double[order + 1];
        this.numerator = new double[order + 1];

        denominator[0] = 1.0;
        numerator[0] = 1.0;

        this.inputHistory = new double[order];
        this.outputHistory = new double[order];
    }

    /**
     * Sets the filter coefficients.
     *
     * @param newDenominator the denominator coefficients (a), length must be order + 1
     * @param newNumerator   the numerator coefficients (b), length must be order + 1
     * @throws IllegalArgumentException if the lengths are incorrect or newDenominator[0] is zero
     */
    public void setCoefficients(double[] newDenominator, double[] newNumerator)
        throws IllegalArgumentException {

        if (newDenominator.length != order + 1) {
            throw new IllegalArgumentException(
                "Denominator coefficients must be of size " + (order + 1)
                    + ", got " + newDenominator.length
            );
        }

        if (newDenominator[0] == 0.0) {
            throw new IllegalArgumentException("Denominator coefficient a[0] must not be zero");
        }

        if (newNumerator.length != order + 1) {
            throw new IllegalArgumentException(
                "Numerator coefficients must be of size " + (order + 1)
                    + ", got " + newNumerator.length
            );
        }

        System.arraycopy(newDenominator, 0, this.denominator, 0, order + 1);
        System.arraycopy(newNumerator, 0, this.numerator, 0, order + 1);
    }

    /**
     * Processes a single input sample and returns the filtered output sample.
     *
     * @param inputSample the input sample
     * @return the filtered output sample
     */
    public double processSample(double inputSample) {
        double outputSample = 0.0;

        for (int i = 1; i <= order; i++) {
            outputSample += numerator[i] * inputHistory[i - 1]
                - denominator[i] * outputHistory[i - 1];
        }

        outputSample = (outputSample + numerator[0] * inputSample) / denominator[0];

        for (int i = order - 1; i > 0; i--) {
            inputHistory[i] = inputHistory[i - 1];
            outputHistory[i] = outputHistory[i - 1];
        }

        inputHistory[0] = inputSample;
        outputHistory[0] = outputSample;

        return outputSample;
    }
}