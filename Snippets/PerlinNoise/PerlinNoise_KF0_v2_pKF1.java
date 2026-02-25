package com.thealgorithms.others;

import java.util.Random;
import java.util.Scanner;

/**
 * For detailed info and implementation see: <a
 * href="http://devmag.org.za/2009/04/25/perlin-noise/">Perlin-Noise</a>
 */
public final class PerlinNoise {

    private PerlinNoise() {}

    /**
     * @param width width of noise array
     * @param height height of noise array
     * @param octaveCount numbers of layers used for blending noise
     * @param persistence value of impact each layer get while blending
     * @param seed used for randomizer
     * @return float array containing calculated "Perlin-Noise" values
     */
    static float[][] generatePerlinNoise(int width, int height, int octaveCount, float persistence, long seed) {
        final float[][] baseNoise = new float[width][height];
        final float[][] perlinNoise = new float[width][height];
        final float[][][] octaveNoiseLayers = new float[octaveCount][][];

        Random random = new Random(seed);

        // fill base array with random values as base for noise
        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                baseNoise[xCoordinate][yCoordinate] = random.nextFloat();
            }
        }

        // calculate octaves with different roughness
        for (int octaveIndex = 0; octaveIndex < octaveCount; octaveIndex++) {
            octaveNoiseLayers[octaveIndex] = generatePerlinNoiseLayer(baseNoise, width, height, octaveIndex);
        }

        float amplitude = 1f;
        float totalAmplitude = 0f;

        // calculate perlin noise by blending each layer together with specific persistence
        for (int octaveIndex = octaveCount - 1; octaveIndex >= 0; octaveIndex--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
                for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                    // adding each value of the noise layer to the noise
                    // by increasing amplitude the rougher noises will have more impact
                    perlinNoise[xCoordinate][yCoordinate] += octaveNoiseLayers[octaveIndex][xCoordinate][yCoordinate] * amplitude;
                }
            }
        }

        // normalize values so that they stay between 0..1
        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                perlinNoise[xCoordinate][yCoordinate] /= totalAmplitude;
            }
        }

        return perlinNoise;
    }

    /**
     * @param baseNoise base random float array
     * @param width width of noise array
     * @param height height of noise array
     * @param octaveIndex current layer
     * @return float array containing calculated "Perlin-Noise-Layer" values
     */
    static float[][] generatePerlinNoiseLayer(float[][] baseNoise, int width, int height, int octaveIndex) {
        float[][] perlinNoiseLayer = new float[width][height];

        // calculate period (wavelength) for different shapes
        int period = 1 << octaveIndex; // 2^k
        float frequency = 1f / period; // 1/2^k

        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            // calculates the horizontal sampling indices
            int sampleXStart = (xCoordinate / period) * period;
            int sampleXEnd = (sampleXStart + period) % width;
            float horizontalBlend = (xCoordinate - sampleXStart) * frequency;

            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                // calculates the vertical sampling indices
                int sampleYStart = (yCoordinate / period) * period;
                int sampleYEnd = (sampleYStart + period) % height;
                float verticalBlend = (yCoordinate - sampleYStart) * frequency;

                // blend top corners
                float topInterpolation =
                        interpolate(baseNoise[sampleXStart][sampleYStart], baseNoise[sampleXEnd][sampleYStart], horizontalBlend);

                // blend bottom corners
                float bottomInterpolation =
                        interpolate(baseNoise[sampleXStart][sampleYEnd], baseNoise[sampleXEnd][sampleYEnd], horizontalBlend);

                // blend top and bottom interpolation to get the final blend value for this cell
                perlinNoiseLayer[xCoordinate][yCoordinate] = interpolate(topInterpolation, bottomInterpolation, verticalBlend);
            }
        }

        return perlinNoiseLayer;
    }

    /**
     * @param startValue value of point a
     * @param endValue value of point b
     * @param alpha determine which value has more impact (closer to 0 -> a,
     * closer to 1 -> b)
     * @return interpolated value
     */
    static float interpolate(float startValue, float endValue, float alpha) {
        return startValue * (1 - alpha) + alpha * endValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int width;
        final int height;
        final int octaveCount;
        final float persistence;
        final long seed;
        final String charset;
        final float[][] perlinNoise;

        System.out.println("Width (int): ");
        width = scanner.nextInt();

        System.out.println("Height (int): ");
        height = scanner.nextInt();

        System.out.println("Octave count (int): ");
        octaveCount = scanner.nextInt();

        System.out.println("Persistence (float): ");
        persistence = scanner.nextFloat();

        System.out.println("Seed (long): ");
        seed = scanner.nextLong();

        System.out.println("Charset (String): ");
        charset = scanner.next();

        perlinNoise = generatePerlinNoise(width, height, octaveCount, persistence, seed);
        final char[] charsetCharacters = charset.toCharArray();
        final int charsetLength = charsetCharacters.length;
        final float charsetStep = 1f / charsetLength;

        // output based on charset
        for (int xCoordinate = 0; xCoordinate < width; xCoordinate++) {
            for (int yCoordinate = 0; yCoordinate < height; yCoordinate++) {
                float threshold = charsetStep;
                float noiseValue = perlinNoise[xCoordinate][yCoordinate];

                for (char character : charsetCharacters) {
                    if (noiseValue <= threshold) {
                        System.out.print(character);
                        break;
                    }

                    threshold += charsetStep;
                }
            }

            System.out.println();
        }
        scanner.close();
    }
}