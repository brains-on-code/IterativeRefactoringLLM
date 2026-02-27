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

    // BITMAPFILEHEADER fields
    private final byte[] fileType = {'B', 'M'};
    private int fileSize = 0;
    private int reserved1 = 0;
    private int reserved2 = 0;
    private final int pixelDataOffset = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private final int infoHeaderSize = BITMAP_INFO_HEADER_SIZE;
    private int width = 0;
    private int height = 0;
    private final int planes = 1;
    private final int bitCount = 24;
    private final int compression = 0;
    private int imageSize = 0x030000;
    private final int xPixelsPerMeter = 0x0;
    private final int yPixelsPerMeter = 0x0;
    private final int colorsUsed = 0;
    private final int colorsImportant = 0;

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
        convertImage(bitmap);
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmap();
        outputStream.write(buffer.array());
    }

    private void convertImage(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowSize = width * 3;
        int paddingPerRow = (4 - (rowSize % 4)) % 4;
        int totalPadding = paddingPerRow * height;

        imageSize = (width * height * 3) + totalPadding;
        fileSize = imageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        this.width = width;
        this.height = height;
    }

    private void writeBitmap() {
        int totalPixels = (width * height) - 1;
        int rowSize = width * 3;
        int paddingPerRow = (4 - (rowSize % 4)) % 4;

        int rowCount = 1;
        int rowIndex = totalPixels - width;
        int lastRowIndex = rowIndex;
        int padCount = 0;

        byte[] rgb = new byte[3];

        for (int j = 0; j < totalPixels; j++) {
            int value = pixels[rowIndex];

            rgb[0] = (byte) (value & 0xFF);
            rgb[1] = (byte) ((value >> 8) & 0xFF);
            rgb[2] = (byte) ((value >> 16) & 0xFF);
            buffer.put(rgb);

            if (rowCount == width) {
                padCount += paddingPerRow;
                for (int i = 0; i < paddingPerRow; i++) {
                    buffer.put((byte) 0x00);
                }
                rowCount = 1;
                rowIndex = lastRowIndex - width;
                lastRowIndex = rowIndex;
            } else {
                rowCount++;
            }
            rowIndex++;
        }

        fileSize += padCount - paddingPerRow;
        imageSize += padCount - paddingPerRow;
    }

    private void writeBitmapFileHeader() {
        buffer.put(fileType);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(reserved1));
        buffer.put(intToWord(reserved2));
        buffer.put(intToDWord(pixelDataOffset));
    }

    private void writeBitmapInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(width));
        buffer.put(intToDWord(height));
        buffer.put(intToWord(planes));
        buffer.put(intToWord(bitCount));
        buffer.put(intToDWord(compression));
        buffer.put(intToDWord(imageSize));
        buffer.put(intToDWord(xPixelsPerMeter));
        buffer.put(intToDWord(yPixelsPerMeter));
        buffer.put(intToDWord(colorsUsed));
        buffer.put(intToDWord(colorsImportant));
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
        result[1] = (byte) ((value >> 8) & 0x000000FF);
        result[2] = (byte) ((value >> 16) & 0x000000FF);
        result[3] = (byte) ((value >> 24) & 0x000000FF);
        return result;
    }

    static void encodeToBMP(Bitmap bitmap, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        new BMPUtil().saveBitmap(bitmap, out);
        out.close();
    }
}