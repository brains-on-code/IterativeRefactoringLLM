package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Writes a {@link Bitmap} as a 24-bit BMP image.
 */
class BmpWriter {

    // --- BMP format constants ---

    /** Size of the BMP file header in bytes. */
    private static final int FILE_HEADER_SIZE = 14;

    /** Size of the BMP info header in bytes. */
    private static final int INFO_HEADER_SIZE = 40;

    /** BMP file signature ("BM"). */
    private static final byte[] BMP_SIGNATURE = {'B', 'M'};

    /** Offset from the beginning of the file to the pixel data. */
    private static final int PIXEL_DATA_OFFSET = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    /** Size of the info header field (must be 40 for BITMAPINFOHEADER). */
    private static final int INFO_HEADER_SIZE_FIELD = INFO_HEADER_SIZE;

    /** Number of color planes (must be 1). */
    private static final int PLANES = 1;

    /** Bits per pixel (24-bit RGB). */
    private static final int BITS_PER_PIXEL = 24;

    /** Compression method (0 = BI_RGB, no compression). */
    private static final int COMPRESSION = 0;

    /** Horizontal resolution (pixels per meter). */
    private static final int X_PIXELS_PER_METER = 0;

    /** Vertical resolution (pixels per meter). */
    private static final int Y_PIXELS_PER_METER = 0;

    /** Number of colors in the palette (0 = default). */
    private static final int COLORS_USED = 0;

    /** Number of important colors (0 = all). */
    private static final int IMPORTANT_COLORS = 0;

    // --- Header fields that depend on the bitmap ---

    /** Total file size in bytes. */
    private int fileSize;

    /** Reserved fields in the file header (unused, must be 0). */
    private int reserved1;
    private int reserved2;

    /** Bitmap width in pixels. */
    private int width;

    /** Bitmap height in pixels. */
    private int height;

    /** Size of the raw bitmap data (including padding), in bytes. */
    private int imageSize = 0x030000;

    // --- Working data ---

    /** ARGB pixels read from the source bitmap. */
    private int[] pixels;

    /** Buffer used to build the BMP file in memory. */
    private ByteBuffer buffer;

    /** Output stream to which the BMP data is written. */
    private OutputStream outputStream;

    private void write(Bitmap bitmap, String path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            write(bitmap, fos);
        }
    }

    private void write(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        write(bitmap);
    }

    private void write(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    /**
     * Extracts pixel data from the bitmap and initializes header-related fields and buffer.
     */
    private void prepareBitmapData(Bitmap bitmap) {
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        pixels = new int[bmpWidth * bmpHeight];
        bitmap.getPixels(pixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        // Each pixel is 3 bytes (B, G, R). Rows are padded to a multiple of 4 bytes.
        int rowSize = bmpWidth * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;

        imageSize = (rowSize + rowPadding) * bmpHeight;
        fileSize = imageSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        width = bmpWidth;
        height = bmpHeight;
    }

    /**
     * Writes pixel data in bottom-up BGR format with row padding.
     */
    private void writePixelData() {
        int lastPixelIndex = (width * height) - 1;
        int rowSize = width * 3;
        int rowPadding = (4 - (rowSize % 4)) % 4;

        int pixelsInRow = 1;
        int totalPaddingWritten = 0;
        int rowStartIndex = lastPixelIndex - width;
        int currentIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int color = pixels[currentIndex];

            // BMP stores pixels in BGR order.
            rgb[0] = (byte) (color & 0xFF);         // Blue
            rgb[1] = (byte) ((color >> 8) & 0xFF);  // Green
            rgb[2] = (byte) ((color >> 16) & 0xFF); // Red
            buffer.put(rgb);

            if (pixelsInRow == width) {
                totalPaddingWritten += rowPadding;
                for (int p = 0; p < rowPadding; p++) {
                    buffer.put((byte) 0x00);
                }
                pixelsInRow = 1;
                rowStartIndex -= width;
                currentIndex = rowStartIndex;
            } else {
                pixelsInRow++;
                currentIndex++;
            }
        }

        fileSize += totalPaddingWritten - rowPadding;
        imageSize += totalPaddingWritten - rowPadding;
    }

    /**
     * Writes the 14-byte BMP file header.
     */
    private void writeFileHeader() {
        buffer.put(BMP_SIGNATURE);
        buffer.put(intTo4Bytes(fileSize));
        buffer.put(intTo2Bytes(reserved1));
        buffer.put(intTo2Bytes(reserved2));
        buffer.put(intTo4Bytes(PIXEL_DATA_OFFSET));
    }

    /**
     * Writes the 40-byte BMP info header (BITMAPINFOHEADER).
     */
    private void writeInfoHeader() {
        buffer.put(intTo4Bytes(INFO_HEADER_SIZE_FIELD));
        buffer.put(intTo4Bytes(width));
        buffer.put(intTo4Bytes(height));
        buffer.put(intTo2Bytes(PLANES));
        buffer.put(intTo2Bytes(BITS_PER_PIXEL));
        buffer.put(intTo4Bytes(COMPRESSION));
        buffer.put(intTo4Bytes(imageSize));
        buffer.put(intTo4Bytes(X_PIXELS_PER_METER));
        buffer.put(intTo4Bytes(Y_PIXELS_PER_METER));
        buffer.put(intTo4Bytes(COLORS_USED));
        buffer.put(intTo4Bytes(IMPORTANT_COLORS));
    }

    /**
     * Converts an int to a 2-byte little-endian array.
     */
    private byte[] intTo2Bytes(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF)
        };
    }

    /**
     * Converts an int to a 4-byte little-endian array.
     */
    private byte[] intTo4Bytes(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    static void write(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            new BmpWriter().write(bitmap, fos);
        }
    }
}