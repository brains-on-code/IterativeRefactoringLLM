package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    // BMP header sizes (in bytes)
    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;

    // BITMAPFILEHEADER constants
    private static final byte[] FILE_TYPE = {'B', 'M'};
    private static final int PIXEL_DATA_OFFSET = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BITMAPINFOHEADER constants
    private static final int INFO_HEADER_SIZE = BITMAP_INFO_HEADER_SIZE;
    private static final int PLANES = 1;
    private static final int BIT_COUNT = 24;          // 24-bit RGB
    private static final int COMPRESSION = 0;         // BI_RGB (no compression)
    private static final int X_PIXELS_PER_METER = 0;
    private static final int Y_PIXELS_PER_METER = 0;
    private static final int COLORS_USED = 0;
    private static final int COLORS_IMPORTANT = 0;

    private int fileSize = 0;
    private int imageSize = 0x030000;
    private int width = 0;
    private int height = 0;

    private int[] pixels;
    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            this.outputStream = out;
            save(bitmap);
        }
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();

        pixels = new int[bmpWidth * bmpHeight];
        bitmap.getPixels(pixels, 0, bmpWidth, 0, 0, bmpWidth, bmpHeight);

        int rowSizeBytes = bmpWidth * 3;
        int paddingPerRow = (4 - (rowSizeBytes % 4)) % 4;
        int totalPadding = paddingPerRow * bmpHeight;

        imageSize = (bmpWidth * bmpHeight * 3) + totalPadding;
        fileSize = imageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        this.width = bmpWidth;
        this.height = bmpHeight;
    }

    private void writePixelData() {
        int totalPixels = (width * height) - 1;
        int rowSizeBytes = width * 3;
        int paddingPerRow = (4 - (rowSizeBytes % 4)) % 4;

        int pixelsWrittenInRow = 1;
        int rowStartIndex = totalPixels - width;
        int lastRowStartIndex = rowStartIndex;

        byte[] rgb = new byte[3];

        for (int j = 0; j < totalPixels; j++) {
            int value = pixels[rowStartIndex];

            // BMP stores pixels in BGR order
            rgb[0] = (byte) (value & 0xFF);         // Blue
            rgb[1] = (byte) ((value >> 8) & 0xFF);  // Green
            rgb[2] = (byte) ((value >> 16) & 0xFF); // Red
            buffer.put(rgb);

            if (pixelsWrittenInRow == width) {
                // Pad each row to a multiple of 4 bytes
                for (int i = 0; i < paddingPerRow; i++) {
                    buffer.put((byte) 0x00);
                }
                pixelsWrittenInRow = 1;
                rowStartIndex = lastRowStartIndex - width;
                lastRowStartIndex = rowStartIndex;
            } else {
                pixelsWrittenInRow++;
            }
            rowStartIndex++;
        }
    }

    private void writeBitmapFileHeader() {
        buffer.put(FILE_TYPE);                     // bfType
        buffer.put(intToDWord(fileSize));          // bfSize
        buffer.put(intToWord(0));                  // bfReserved1
        buffer.put(intToWord(0));                  // bfReserved2
        buffer.put(intToDWord(PIXEL_DATA_OFFSET)); // bfOffBits
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(INFO_HEADER_SIZE));   // biSize
        buffer.put(intToDWord(width));              // biWidth
        buffer.put(intToDWord(height));             // biHeight
        buffer.put(intToWord(PLANES));              // biPlanes
        buffer.put(intToWord(BIT_COUNT));           // biBitCount
        buffer.put(intToDWord(COMPRESSION));        // biCompression
        buffer.put(intToDWord(imageSize));          // biSizeImage
        buffer.put(intToDWord(X_PIXELS_PER_METER)); // biXPelsPerMeter
        buffer.put(intToDWord(Y_PIXELS_PER_METER)); // biYPelsPerMeter
        buffer.put(intToDWord(COLORS_USED));        // biClrUsed
        buffer.put(intToDWord(COLORS_IMPORTANT));   // biClrImportant
    }

    private byte[] intToWord(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF)
        };
    }

    private byte[] intToDWord(int value) {
        return new byte[] {
                (byte) (value & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF)
        };
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            new BMPUtil().saveBitmap(bitmap, out);
        }
    }
}