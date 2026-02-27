package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    // BMP header sizes
    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;

    // BITMAPFILEHEADER fields
    private static final byte[] FILE_TYPE = {'B', 'M'};
    private static final int RESERVED_1 = 0;
    private static final int RESERVED_2 = 0;
    private static final int PIXEL_DATA_OFFSET =
            BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private static final int INFO_HEADER_SIZE = BITMAP_INFO_HEADER_SIZE;
    private static final int PLANES = 1;
    private static final int BITS_PER_PIXEL = 24; // 24-bit RGB
    private static final int COMPRESSION = 0;     // BI_RGB (no compression)
    private static final int X_PIXELS_PER_METER = 0;
    private static final int Y_PIXELS_PER_METER = 0;
    private static final int COLORS_USED = 0;
    private static final int COLORS_IMPORTANT = 0;

    private int fileSize = 0;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private int imageSize = 0x030000;

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

            // BMP uses BGR byte order
            bgr[0] = (byte) (value & 0xFF);
            bgr[1] = (byte) ((value >> 8) & 0xFF);
            bgr[2] = (byte) ((value >> 16) & 0xFF);
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
        fileSize = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE
                + (imageWidth * imageHeight * 3) + totalPadding;
        imageSize = (imageWidth * imageHeight * 3) + totalPadding;
    }

    private void writeFileHeader() {
        buffer.put(FILE_TYPE);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(RESERVED_1));
        buffer.put(intToWord(RESERVED_2));
        buffer.put(intToDWord(PIXEL_DATA_OFFSET));
    }

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

    private byte[] intToWord(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) (value & 0x00FF);
        result[1] = (byte) ((value >> 8) & 0x00FF);
        return result;
    }

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