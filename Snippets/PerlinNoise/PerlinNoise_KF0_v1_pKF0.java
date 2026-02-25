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
    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        float[][] baseNoise = generateBaseNoise(width, height, seed);
        float[][][] noiseLayers = generateNoiseLayers(baseNoise, width, height, octaveCount);
        return blendNoiseLayers(noiseLayers, width, height, octaveCount, persistence);
    }

    private static float[][] generateBaseNoise(int width, int height, long seed) {
        float[][] base = new float[width][height];
        Random random = new Random(seed);

        for (int x = 0; x < width; x++) {
            float[] column = base[x];
            for (int y = 0; y < height; y++) {
                column[y] = random.nextFloat();
            }
        }

        return base;
    }

    private static float[][][] generateNoiseLayers(float[][] base, int width, int height, int octaveCount) {
        float[][][] noiseLayers = new float[octaveCount][][];
        for (int octave = 0; octave < octaveCount; octave++) {
            noiseLayers[octave] = generatePerlinNoiseLayer(base, width, height, octave);
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

        // Blend each layer together with specific persistence
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

        // Normalize values so that they stay between 0..1
        for (int x = 0; x < width; x++) {
            float[] noiseColumn = perlinNoise[x];
            for (int y = 0; y < height; y++) {
                noiseColumn[y] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    /**
     * Generates a single Perlin noise layer.
     *
     * @param base   base random float array
     * @param width  width of noise array
     * @param height height of noise array
     * @param octave current layer
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
            System.out.print("Width (int): ");
            int width = in.nextInt();

            System.out.print("Height (int): ");
            int height = in.nextInt();

            System.out.print("Octave count (int): ");
            int octaveCount = in.nextInt();

            System.out.print("Persistence (float): ");
            float persistence = in.nextFloat();

            System.out.print("Seed (long): ");
            long seed = in.nextLong();

            System.out.print("Charset (String): ");
            String charset = in.next();

            float[][] perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
            printNoise(perlinNoise, charset);
        }
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