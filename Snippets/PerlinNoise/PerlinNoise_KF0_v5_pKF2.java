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
        float[][] baseNoise = new float[width][height];
        float[][] perlinNoise = new float[width][height];
        float[][][] octaveLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        fillBaseNoise(baseNoise, random);
        precomputeOctaves(baseNoise, octaveLayers, width, height, octaveCount);
        float totalAmplitude =
            blendOctaves(perlinNoise, octaveLayers, width, height, octaveCount, persistence);
        normalize(perlinNoise, width, height, totalAmplitude);

        return perlinNoise;
    }

    /**
     * Fills the base noise array with random values in [0, 1].
     */
    private static void fillBaseNoise(float[][] baseNoise, Random random) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                baseNoise[x][y] = random.nextFloat();
            }
        }
    }

    /**
     * Precomputes all octave layers from the base noise.
     */
    private static void precomputeOctaves(
        float[][] baseNoise,
        float[][][] octaveLayers,
        int width,
        int height,
        int octaveCount
    ) {
        for (int octave = 0; octave < octaveCount; octave++) {
            octaveLayers[octave] = generatePerlinNoiseLayer(baseNoise, width, height, octave);
        }
    }

    /**
     * Blends all octave layers into a single Perlin noise array.
     *
     * @param perlinNoise target array for blended noise
     * @param octaveLayers precomputed octave layers
     * @param width       width of noise array
     * @param height      height of noise array
     * @param octaveCount number of octaves
     * @param persistence amplitude decay factor per octave
     * @return total amplitude used for normalization
     */
    private static float blendOctaves(
        float[][] perlinNoise,
        float[][][] octaveLayers,
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

            float[][] layer = octaveLayers[octave];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += layer[x][y] * amplitude;
                }
            }
        }

        return totalAmplitude;
    }

    /**
     * Normalizes the Perlin noise values to the range [0, 1].
     *
     * @param perlinNoise    noise array to normalize
     * @param width          width of noise array
     * @param height         height of noise array
     * @param totalAmplitude total amplitude used during blending
     */
    private static void normalize(float[][] perlinNoise, int width, int height, float totalAmplitude) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }
    }

    /**
     * Generates a single Perlin noise layer (octave).
     *
     * @param baseNoise base random float array
     * @param width     width of noise array
     * @param height    height of noise array
     * @param octave    current layer index
     * @return float array containing calculated Perlin noise layer values
     */
    static float[][] generatePerlinNoiseLayer(
        float[][] baseNoise,
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

                float top = interpolate(baseNoise[x0][y0], baseNoise[x1][y0], horizontalBlend);
                float bottom = interpolate(baseNoise[x0][y1], baseNoise[x1][y1], horizontalBlend);

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

    /**
     * Renders the noise array to stdout using the provided character set.
     * Lower noise values map to earlier characters in the set.
     */
    private static void renderNoise(float[][] perlinNoise, String charset) {
        char[] chars = charset.toCharArray();
        int width = perlinNoise.length;
        int height = perlinNoise[0].length;
        int charCount = chars.length;
        float step = 1f / charCount;

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