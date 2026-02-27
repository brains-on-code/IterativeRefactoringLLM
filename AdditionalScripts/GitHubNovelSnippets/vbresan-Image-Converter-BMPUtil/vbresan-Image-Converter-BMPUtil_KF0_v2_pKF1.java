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

    // Bitmap file header
    private final byte[] bitmapSignature = {'B', 'M'};
    private int bitmapFileSize = 0;
    private final int reservedField1 = 0;
    private final int reservedField2 = 0;
    private final int pixelArrayOffset = BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

    // Bitmap info header
    private final int infoHeaderSize = BITMAP_INFO_HEADER_SIZE;
    private int bitmapWidth = 0;
    private int bitmapHeight = 0;
    private final int colorPlaneCount = 1;
    private final int bitsPerPixel = 24;
    private final int compressionMethod = 0;
    private int rawImageSize = 0x030000;
    private final int horizontalPixelsPerMeter = 0x0;
    private final int verticalPixelsPerMeter = 0x0;
    private final int colorPaletteSize = 0;
    private final int importantColorCount = 0;

    // Bitmap raw data
    private int[] argbPixels;

    // File section
    private ByteBuffer byteBuffer = null;
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
        writeBitmapFileHeader();
        writeBitmapInfoHeader();
        writeBitmapPixelArray();
        outputStream.write(byteBuffer.array());
    }

    private void prepareBitmapData(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        argbPixels = new int[width * height];
        bitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);

        int rowPaddingBytes = (4 - ((width * 3) % 4)) * height;
        rawImageSize = (width * height * 3) + rowPaddingBytes;
        bitmapFileSize = rawImageSize + BITMAP_FILE_HEADER_SIZE + BITMAP_INFO_HEADER_SIZE;

        byteBuffer = ByteBuffer.allocate(bitmapFileSize);
        bitmapWidth = width;
        bitmapHeight = height;
    }

    private void writeBitmapPixelArray() {
        int lastPixelIndex = (bitmapWidth * bitmapHeight) - 1;
        int paddingBytesPerRow = 4 - ((bitmapWidth * 3) % 4);
        if (paddingBytesPerRow == 4) {
            paddingBytesPerRow = 0;
        }

        int pixelsWrittenInRow = 1;
        int totalPaddingBytesWritten = 0;
        int currentRowStartIndex = lastPixelIndex - bitmapWidth;
        int previousRowStartIndex = currentRowStartIndex;

        byte[] bgrPixel = new byte[3];

        for (int pixelIndex = 0; pixelIndex < lastPixelIndex; pixelIndex++) {
            int argbPixel = argbPixels[currentRowStartIndex];

            bgrPixel[0] = (byte) (argbPixel & 0xFF);         // Blue
            bgrPixel[1] = (byte) ((argbPixel >> 8) & 0xFF);  // Green
            bgrPixel[2] = (byte) ((argbPixel >> 16) & 0xFF); // Red

            byteBuffer.put(bgrPixel);

            if (pixelsWrittenInRow == bitmapWidth) {
                totalPaddingBytesWritten += paddingBytesPerRow;
                for (int paddingIndex = 0; paddingIndex < paddingBytesPerRow; paddingIndex++) {
                    byteBuffer.put((byte) 0x00);
                }
                pixelsWrittenInRow = 1;
                currentRowStartIndex = previousRowStartIndex - bitmapWidth;
                previousRowStartIndex = currentRowStartIndex;
            } else {
                pixelsWrittenInRow++;
            }
            currentRowStartIndex++;
        }

        bitmapFileSize += totalPaddingBytesWritten - paddingBytesPerRow;
        rawImageSize += totalPaddingBytesWritten - paddingBytesPerRow;
    }

    private void writeBitmapFileHeader() {
        byteBuffer.put(bitmapSignature);
        byteBuffer.put(intToDWord(bitmapFileSize));
        byteBuffer.put(intToWord(reservedField1));
        byteBuffer.put(intToWord(reservedField2));
        byteBuffer.put(intToDWord(pixelArrayOffset));
    }

    private void writeBitmapInfoHeader() {
        byteBuffer.put(intToDWord(infoHeaderSize));
        byteBuffer.put(intToDWord(bitmapWidth));
        byteBuffer.put(intToDWord(bitmapHeight));
        byteBuffer.put(intToWord(colorPlaneCount));
        byteBuffer.put(intToWord(bitsPerPixel));
        byteBuffer.put(intToDWord(compressionMethod));
        byteBuffer.put(intToDWord(rawImageSize));
        byteBuffer.put(intToDWord(horizontalPixelsPerMeter));
        byteBuffer.put(intToDWord(verticalPixelsPerMeter));
        byteBuffer.put(intToDWord(colorPaletteSize));
        byteBuffer.put(intToDWord(importantColorCount));
    }

    private byte[] intToWord(int value) {
        byte[] word = new byte[2];
        word[0] = (byte) (value & 0x00FF);
        word[1] = (byte) ((value >> 8) & 0x00FF);
        return word;
    }

    private byte[] intToDWord(int value) {
        byte[] dword = new byte[4];
        dword[0] = (byte) (value & 0x00FF);
        dword[1] = (byte) ((value >> 8) & 0x000000FF);
        dword[2] = (byte) ((value >> 16) & 0x000000FF);
        dword[3] = (byte) ((value >> 24) & 0x000000FF);
        return dword;
    }

    static void encodeToBMP(Bitmap bitmap, File outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        new BMPUtil().saveBitmap(bitmap, fileOutputStream);
        fileOutputStream.close();
    }
}