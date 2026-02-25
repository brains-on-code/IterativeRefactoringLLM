package com.thealgorithms.maths;


public final class FastInverseSqrt {
    private FastInverseSqrt() {
    }

    public static boolean inverseSqrt(float number) {
        float x = number;
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x = x * (1.5f - xhalf * x * x);
        return x == ((float) 1 / (float) Math.sqrt(number));
    }


    public static boolean inverseSqrt(double number) {
        double x = number;
        double xhalf = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        for (int it = 0; it < 4; it++) {
            x = x * (1.5d - xhalf * x * x);
        }
        x *= number;
        return x == 1 / Math.sqrt(number);
    }
}
