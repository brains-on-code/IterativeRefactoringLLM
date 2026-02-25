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
     * Generates a Perlin noise map.
     *
     * @param width       width of noise array
     * @param height      height of noise array
     * @param octaveCount number of layers used for blending noise
     * @param persistence impact factor of each layer while blending
     * @param seed        seed used for randomizer
     * @return float array containing calculated Perlin noise values
     */
    static float[][] generatePerlinNoise(
            int width,
            int height,
            int octaveCount,
            float persistence,
            long seed
    ) {
        float[][] baseNoise = generateBaseNoise(width, height, seed);
        float[][][] noiseLayers = generateNoiseLayers(baseNoise, width, height, octaveCount);
        return blendNoiseLayers(noiseLayers, width, height, octaveCount, persistence);
    }

    private static float[][] generateBaseNoise(int width, int height, long seed) {
        float[][] baseNoise = new float[width][height];
        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            float[] column = baseNoise[x];
            for (int y = 0; y < height; y++) {
                column[y] = random.nextFloat();
            }
        }

        return baseNoise;
    }

    private static float[][][] generateNoiseLayers(
            float[][] baseNoise,
            int width,
            int height,
            int octaveCount
    ) {
        float[][][] noiseLayers = new float[octaveCount][][];
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(baseNoise, width, height, octave);
        }
        return noiseLayers;
    }

    private static float[][] blendNoiseLayers(
            float[][][] noiseLayers,
            int width,
            int height,
            int octaveCount,
            float persistence
    ) {
        float[][] perlinNoise = new float[width][height];

        float amplitude = 1f;
        float totalAmplitude = 0f;

        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            float[][] layer = noiseLayers[octave];
            for (int x = 0; x < width; x++) {
                float[] noiseColumn = perlinNoise[x];
                float[] layerColumn = layer[x];
                for (int y = 0; y < height; y++) {
                    noiseColumn[y] += layerColumn[y] * amplitude;
                }
            }
        }

        normalize(perlinNoise, width, height, totalAmplitude);
        return perlinNoise;
    }

    private static void normalize(float[][] noise, int width, int height, float totalAmplitude) {
        for (int x = 0; x < width; x++) {
            float[] column = noise[x];
            for (int y = 0; y < height; y++) {
                column[y] /= totalAmplitude;
            }
        }
    }

    /**
     * Generates a single Perlin noise layer.
     *
     * @param baseNoise base random float array
     * @param width     width of noise array
     * @param height    height of noise array
     * @param octave    current layer
     * @return float array containing calculated Perlin noise layer values
     */
    static float[][] generatePerlinNoiseLayer(
            float[][] baseNoise,
            int width,
            int height,
            int octave
    ) {
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

                float top = interpolate(baseNoise[x0][y0], baseNoise[x1][y0], horizontalBlend);
                float bottom = interpolate(baseNoise[x0][y1], baseNoise[x1][y1], horizontalBlend);

                perlinNoiseLayer[x][y] = interpolate(top, bottom, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    /**
     * Linear interpolation between two values.
     *
     * @param a     value of point a
     * @param b     value of point b
     * @param alpha determines which value has more impact (closer to 0 -> a,
     *              closer to 1 -> b)
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

            float[][] perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
            printNoise(perlinNoise, charset);
        }
    }

    private static int readInt(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.nextInt();
    }

    private static float readFloat(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.nextFloat();
    }

    private static long readLong(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.nextLong();
    }

    private static String readString(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.next();
    }

    private static void printNoise(float[][] perlinNoise, String charset) {
        char[] chars = charset.toCharArray();
        int width = perlinNoise.length;
        int height = perlinNoise[0].length;

        int charsetLength = chars.length;
        float step = 1f / charsetLength;

        for (int x = 0; x < width; x++) {
            float[] column = perlinNoise[x];
            for (int y = 0; y < height; y++) {
                float noiseValue = column[y];
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