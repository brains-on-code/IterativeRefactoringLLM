package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@SuppressWarnings("FieldCanBeLocal")
class BMPUtil {

    private static final int FILE_HEADER_SIZE = 14;
    private static final int INFO_HEADER_SIZE = 40;

    // BITMAPFILEHEADER fields
    private final byte[] fileTypeSignature = {'B', 'M'};
    private int fileSize = 0;
    private int reservedField1 = 0;
    private int reservedField2 = 0;
    private int pixelDataOffset = FILE_HEADER_SIZE + INFO_HEADER_SIZE;

    // BITMAPINFOHEADER fields
    private int infoHeaderSize = INFO_HEADER_SIZE;
    private int bitmapWidth = 0;
    private int bitmapHeight = 0;
    private int colorPlanes = 1;
    private int bitsPerPixel = 24;
    private int compressionMethod = 0;
    private int pixelDataSize = 0x030000;
    private int horizontalResolution = 0x0;
    private int verticalResolution = 0x0;
    private int totalColors = 0;
    private int importantColors = 0;

    private int[] pixels;

    private ByteBuffer buffer = null;
    private OutputStream outputStream;

    private void saveBitmap(Bitmap bitmap, String filePath) throws IOException {
        outputStream = new FileOutputStream(filePath);
        save(bitmap);
        outputStream.close();
    }

    private void saveBitmap(Bitmap bitmap, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        save(bitmap);
    }

    private void save(Bitmap bitmap) throws IOException {
        prepareBitmapData(bitmap);
        writeFileHeader();
        writeInfoHeader();
        writePixelData();
        outputStream.write(buffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        pixelDataSize = (width * height * 3) + rowPaddingBytes;
        fileSize = pixelDataSize + FILE_HEADER_SIZE + INFO_HEADER_SIZE;

        buffer = ByteBuffer.allocate(fileSize);
        bitmapWidth = width;
        bitmapHeight = height;
    }

    private void writePixelData() {
        int lastPixelIndex = (bitmapWidth * bitmapHeight) - 1;
        int paddingBytesPerRow = 4 - ((bitmapWidth * 3) % 4);
        if (paddingBytesPerRow == 4) {
            paddingBytesPerRow = 0;
        }

        int pixelsWrittenInRow = 1;
        int totalPaddingBytes = 0;
        int currentRowStartIndex = lastPixelIndex - bitmapWidth;
        int previousRowStartIndex = currentRowStartIndex;

        byte[] bgr = new byte[3];

        for (int i = 0; i < lastPixelIndex; i++) {
            int pixel = pixels[currentRowStartIndex];

            bgr[0] = (byte) (pixel & 0xFF);         // Blue
            bgr[1] = (byte) ((pixel >> 8) & 0xFF);  // Green
            bgr[2] = (byte) ((pixel >> 16) & 0xFF); // Red

            buffer.put(bgr);

            if (pixelsWrittenInRow == bitmapWidth) {
                totalPaddingBytes += paddingBytesPerRow;
                for (int padIndex = 0; padIndex < paddingBytesPerRow; padIndex++) {
                    buffer.put((byte) 0x00);
                }
                pixelsWrittenInRow = 1;
                currentRowStartIndex = previousRowStartIndex - bitmapWidth;
                previousRowStartIndex = currentRowStartIndex;
            } else {
                pixelsWrittenInRow++;
            }
            currentRowStartIndex++;
        }

        fileSize += totalPaddingBytes - paddingBytesPerRow;
        pixelDataSize += totalPaddingBytes - paddingBytesPerRow;
    }

    private void writeFileHeader() {
        buffer.put(fileTypeSignature);
        buffer.put(intToDWord(fileSize));
        buffer.put(intToWord(reservedField1));
        buffer.put(intToWord(reservedField2));
        buffer.put(intToDWord(pixelDataOffset));
    }

    private void writeInfoHeader() {
        buffer.put(intToDWord(infoHeaderSize));
        buffer.put(intToDWord(bitmapWidth));
        buffer.put(intToDWord(bitmapHeight));
        buffer.put(intToWord(colorPlanes));
        buffer.put(intToWord(bitsPerPixel));
        buffer.put(intToDWord(compressionMethod));
        buffer.put(intToDWord(pixelDataSize));
        buffer.put(intToDWord(horizontalResolution));
        buffer.put(intToDWord(verticalResolution));
        buffer.put(intToDWord(totalColors));
        buffer.put(intToDWord(importantColors));
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
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}