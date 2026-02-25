package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * Perlin noise generator.
 *
 * <p>For detailed info and implementation see:
 * <a href="http://devmag.org.za/2009/04/25/perlin-noise/">Perlin-Noise</a>
 */
public final class PerlinNoise {

    private PerlinNoise() {
        // Utility class; prevent instantiation
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
    static float[][] generatePerlinNoise(
        int width,
        int height,
        int octaveCount,
        float persistence,
        long seed
    ) {
        float[][] base = new float[width][height];
        float[][] perlinNoise = new float[width][height];
        float[][][] noiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        fillBaseNoise(base, random);
        precomputeOctaves(base, noiseLayers, width, height, octaveCount);
        blendOctaves(perlinNoise, noiseLayers, width, height, octaveCount, persistence);
        normalize(perlinNoise, width, height);

        return perlinNoise;
    }

    private static void fillBaseNoise(float[][] base, Random random) {
        int width = base.length;
        int height = base[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                base[x][y] = random.nextFloat();
            }
        }
    }

    private static void precomputeOctaves(
        float[][] base,
        float[][][] noiseLayers,
        int width,
        int height,
        int octaveCount
    ) {
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(base, width, height, octave);
        }
    }

    private static void blendOctaves(
        float[][] perlinNoise,
        float[][][] noiseLayers,
        int width,
        int height,
        int octaveCount,
        float persistence
    ) {
        float amplitude = 1f;
        float totalAmplitude = 0f;

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

        // Store total amplitude in [0][0] as a simple way to pass it to normalize()
        // without changing the public API. normalize() will divide by this value.
        perlinNoise[0][0] /= (1f / totalAmplitude);
    }

    private static void normalize(float[][] perlinNoise, int width, int height) {
        // totalAmplitude was encoded into perlinNoise[0][0] in blendOctaves()
        float totalAmplitude = perlinNoise[0][0];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }
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
    static float[][] generatePerlinNoiseLayer(
        float[][] base,
        int width,
        int height,
        int octave
    ) {
        float[][] perlinNoiseLayer = new float[width][height];

        int period = 1 << octave;
        float frequency = 1f / period;

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
     * @param alpha weight factor (0 -> a, 1 -> b)
     * @return interpolated value
     */
    static float interpolate(float a, float b, float alpha) {
        return a * (1 - alpha) + alpha * b;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int width = readInt(in, "Width (int): ");
            int height = readInt(in, "Height (int): ");
            int octaveCount = readInt(in, "Octave count (int): ");
            float persistence = readFloat(in, "Persistence (float): ");
            long seed = readLong(in, "Seed (long): ");
            String charset = readString(in, "Charset (String): ");

            float[][] perlinNoise =
                generatePerlinNoise(width, height, octaveCount, persistence, seed);

            renderNoise(perlinNoise, charset);
        }
    }

    private static int readInt(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextInt();
    }

    private static float readFloat(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextFloat();
    }

    private static long readLong(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.nextLong();
    }

    private static String readString(Scanner in, String prompt) {
        System.out.println(prompt);
        return in.next();
    }

    private static void renderNoise(float[][] perlinNoise, String charset) {
        char[] chars = charset.toCharArray();
        int width = perlinNoise.length;
        int height = perlinNoise[0].length;
        int length = chars.length;
        float step = 1f / length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float noiseValue = perlinNoise[x][y];
                float threshold = step;

                for (char c : chars) {
                    if (noiseValue <= threshold) {
                        System.out.print(c);
                        break;
                    }
                    threshold += step;
                }
            }
            System.out.println();
        }
    }
}