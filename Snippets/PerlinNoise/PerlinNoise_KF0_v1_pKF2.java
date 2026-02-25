package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * For detailed info and implementation see:
 * <a href="http://devmag.org.za/2009/04/25/perlin-noise/">Perlin-Noise</a>
 */
public final class PerlinNoise {

    private PerlinNoise() {
        // Utility class
    }

    /**
     * Generates a 2D Perlin noise array.
     *
     * @param width       width of noise array
     * @param height      height of noise array
     * @param octaveCount number of layers used for blending noise
     * @param persistence impact factor of each layer while blending
     * @param seed        seed for randomizer
     * @return float array containing calculated Perlin noise values
     */
    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] base = new float[width][height];
        final float[][] perlinNoise = new float[width][height];
        final float[][][] noiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        // Initialize base array with random values
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                base[x][y] = random.nextFloat();
            }
        }

        // Generate noise layers (octaves) with different roughness
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(base, width, height, octave);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        // Blend layers together using persistence
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] layer = noiseLayers[octave];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += layer[x][y] * amplitude;
                }
            }
        }

        // Normalize values to range [0, 1]
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    /**
     * Generates a single Perlin noise layer (octave).
     *
     * @param base   base random float array
     * @param width  width of noise array
     * @param height height of noise array
     * @param octave current layer index
     * @return float array containing calculated Perlin noise layer values
     */
    static float[][] generatePerlinNoiseLayer(float[][] base, int width, int height, int octave) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octave;      // 2^octave
        float frequency = 1f / period; // 1 / 2^octave

        for (int x = 0; x < width; x++) {
            int x0 = (x / period) * period;
            int x1 = (x0 + period) % width;
            float horizontalBlend = (x - x0) * frequency;

            for (int y = 0; y < height; y++) {
                int y0 = (y / period) * period;
                int y1 = (y0 + period) % height;
                float verticalBlend = (y - y0) * frequency;

                float top = interpolate(base[x0][y0], base[x1][y0], horizontalBlend);
                float bottom = interpolate(base[x0][y1], base[x1][y1], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    /**
     * Linearly interpolates between two values.
     *
     * @param a     value of point a
     * @param b     value of point b
     * @param alpha weight factor (closer to 0 -> a, closer to 1 -> b)
     * @return interpolated value
     */
    static float interpolate(float a, float b, float alpha) {
        return a * (1 - alpha) + alpha * b;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Width (int): ");
        int width = in.nextInt();

        System.out.println("Height (int): ");
        int height = in.nextInt();

        System.out.println("Octave count (int): ");
        int octaveCount = in.nextInt();

        System.out.println("Persistence (float): ");
        float persistence = in.nextFloat();

        System.out.println("Seed (long): ");
        long seed = in.nextLong();

        System.out.println("Charset (String): ");
        String charset = in.next();

        float[][] perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
        char[] chars = charset.toCharArray();
        int length = chars.length;
        float step = 1f / length;

        // Output noise using characters from charset
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float value = step;
                float noiseValue = perlinNoise[x][y];

                for (char c : chars) {
                    if (noiseValue <= value) {
                        System.out.print(c);
                        break;
                    }
                    value += step;
                }
            }
            System.out.println();
        }

        in.close();
    }
}