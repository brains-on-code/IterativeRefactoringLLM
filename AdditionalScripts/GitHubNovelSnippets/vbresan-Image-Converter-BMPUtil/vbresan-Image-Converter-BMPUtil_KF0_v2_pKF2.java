package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;

    // BMP file header (BITMAPFILEHEADER)
    private static final byte[] FILE_TYPE = {'B', 'M'}; // Signature
    private int fileSize = 0;                           // bfSize
    private static final int RESERVED_1 = 0;            // bfReserved1
    private static final int RESERVED_2 = 0;            // bfReserved2
    private static final int PIXEL_DATA_OFFSET =        // bfOffBits
            BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BMP info header (BITMAPINFOHEADER)
    private static final int INFO_HEADER_SIZE = BITMAP_INFO_HEADER_SIZE; // biSize
    private int imageWidth = 0;                                          // biWidth
    private int imageHeight = 0;                                         // biHeight
    private static final int PLANES = 1;                                 // biPlanes
    private static final int BITS_PER_PIXEL = 24;                        // biBitCount (RGB)
    private static final int COMPRESSION = 0;                            // biCompression (BI_RGB)
    private int imageSize = 0x030000;                                    // biSizeImage
    private static final int X_PIXELS_PER_METER = 0x0;                   // biXPelsPerMeter
    private static final int Y_PIXELS_PER_METER = 0x0;                   // biYPelsPerMeter
    private static final int COLORS_USED = 0;                            // biClrUsed
    private static final int COLORS_IMPORTANT = 0;                       // biClrImportant

    private int[] pixels;

    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        outputStream = new FileOutputStream(filename);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareImageData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    /**
     * Extracts pixel data from the bitmap and prepares the backing buffer and header fields.
     */
    private void prepareImageData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSizeWithoutPadding = width * 3; // 3 bytes per pixel (RGB)
        int paddingPerRow = (4 - (rowSizeWithoutPadding % 4)) % 4;
        int totalPadding = paddingPerRow * height;

        imageSize = (width * height * 3) + totalPadding;
        fileSize = imageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        imageWidth = width;
        imageHeight = height;
    }

    /**
     * Writes pixel data in BGR format, bottom-up, with row padding to 4-byte boundaries.
     */
    private void writePixelData() {
        int totalPixels = imageWidth * imageHeight;
        int lastIndex = totalPixels - 1;

        int paddingPerRow = (4 - ((imageWidth * 3) % 4)) % 4;

        int rowCount = 1;
        int rowIndex = lastIndex - imageWidth;
        int lastRowIndex = rowIndex;

        byte[] bgr = new byte[3];

        for (int j = 0; j < lastIndex; j++) {
            int value = pixels[rowIndex];

            // BMP stores pixels as BGR
            bgr[0] = (byte) (value & 0xFF);         // Blue
            bgr[1] = (byte) ((value >> 8) & 0xFF);  // Green
            bgr[2] = (byte) ((value >> 16) & 0xFF); // Red
            buffer.put(bgr);

            if (rowCount == imageWidth) {
                for (int i = 0; i < paddingPerRow; i++) {
                    buffer.put((byte) 0x00);
                }
                rowCount = 1;
                rowIndex = lastRowIndex - imageWidth;
                lastRowIndex = rowIndex;
            } else {
                rowCount++;
            }
            rowIndex++;
        }

        int totalPadding = paddingPerRow * imageHeight;
        fileSize = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE + (imageWidth * imageHeight * 3) + totalPadding;
        imageSize = (imageWidth * imageHeight * 3) + totalPadding;
    }

    /**
     * Writes the BITMAPFILEHEADER to the buffer.
     */
    private void writeFileHeader() {
        buffer.put(FILE_TYPE);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(RESERVED_1));
        buffer.put(intToWord(RESERVED_2));
        buffer.put(intToDWord(PIXEL_DATA_OFFSET));
    }

    /**
     * Writes the BITMAPINFOHEADER to the buffer.
     */
    private void writeInfoHeader() {
        buffer.put(intToDWord(INFO_HEADER_SIZE));
        buffer.put(intToDWord(imageWidth));
        buffer.put(intToDWord(imageHeight));
        buffer.put(intToWord(PLANES));
        buffer.put(intToWord(BITS_PER_PIXEL));
        buffer.put(intToDWord(COMPRESSION));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(X_PIXELS_PER_METER));
        buffer.put(intToDWord(Y_PIXELS_PER_METER));
        buffer.put(intToDWord(COLORS_USED));
        buffer.put(intToDWord(COLORS_IMPORTANT));
    }

    /**
     * Converts an int to a 2-byte little-endian array.
     */
    private byte[] intToWord(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x00FF);
        return result;
    }

    /**
     * Converts an int to a 4-byte little-endian array.
     */
    private byte[] intToDWord(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0xFF);
        result[2] = (byte) ((value >> 16) & 0xFF);
        result[3] = (byte) ((value >> 24) & 0xFF);
        return result;
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        try {
            new BMPUtil().saveBitmap(bitmap, out);
        } finally {
            out.close();
        }
    }
}