package biz.binarysolutions.imageconverter.image;

import android.graphics.Bitmap;
import android.net.Uri;

import org.beyka.tiffbitmapfactory.TiffBitmapFactory;
import org.beyka.tiffbitmapfactory.TiffConverter;
import org.beyka.tiffbitmapfactory.TiffSaver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import biz.binarysolutions.imageconverter.data.FilenameUriTuple;
import biz.binarysolutions.imageconverter.data.OutputFormat;
import biz.binarysolutions.imageconverter.exceptions.EncodeException;

public class TiffImageConverter {

    private static final int DEFAULT_AVAILABLE_MEMORY_BYTES = 10_000_000;

    private static TiffBitmapFactory.Options createTiffDecodeOptions(File tiffFile) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String buildOutputFilename(
            String baseFilename,
            int pageNumber,
            int totalPageCount,
            OutputFormat outputFormat
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                pageNumber,
                totalPageCount
        );

        String filenameWithoutExtension;
        int extensionIndex = baseFilename.lastIndexOf(".");
        if (extensionIndex == -1) {
            filenameWithoutExtension = baseFilename + pageSuffix;
        } else {
            filenameWithoutExtension =
                    baseFilename.substring(0, extensionIndex) + pageSuffix;
        }

        return filenameWithoutExtension + "." + outputFormat.getFileExtension();
    }

    private static void convertTiffDirectly(
            OutputFormat outputFormat,
            String inputFilePath,
            String outputFilePath,
            int directoryIndex
    ) throws Exception {

        TiffConverter.ConverterOptions converterOptions =
                new TiffConverter.ConverterOptions();

        converterOptions.readTiffDirectory = directoryIndex;
        converterOptions.throwExceptions = true;

        switch (outputFormat) {
            case BMP:
                TiffConverter.convertTiffBmp(inputFilePath, outputFilePath, converterOptions, null);
                break;
            case JPG:
                TiffConverter.convertTiffJpg(inputFilePath, outputFilePath, converterOptions, null);
                break;
            case PNG:
                TiffConverter.convertTiffPng(inputFilePath, outputFilePath, converterOptions, null);
                break;
            default:
                throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void saveBitmapAsTiff(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions saveOptions = new TiffSaver.SaveOptions();
        saveOptions.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, saveOptions);
        } catch (Exception e) {
            IOException wrappedException = new IOException(e.toString());
            wrappedException.setStackTrace(e.getStackTrace());
            throw wrappedException;
        }
    }

    private static void encodeTiffDirectory(
            File inputTiffFile,
            OutputFormat outputFormat,
            TiffBitmapFactory.Options decodeOptions,
            File outputFile
    ) throws EncodeException {

        Bitmap decodedBitmap = TiffBitmapFactory.decodeFile(inputTiffFile, decodeOptions);
        int directoryIndex = decodeOptions.inDirectoryNumber;

        try {
            if (decodedBitmap != null) {
                Converter.encodeBitmap(decodedBitmap, outputFormat, outputFile);
                decodedBitmap.recycle();
            } else {
                String inputPath = inputTiffFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertTiffDirectly(outputFormat, inputPath, outputPath, directoryIndex);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertTiffToImages(
            File inputTiffFile,
            OutputFormat outputFormat,
            File outputDirectory
    ) throws EncodeException {

        List<FilenameUriTuple> convertedImageTuples = new ArrayList<>();

        TiffBitmapFactory.Options decodeOptions = createTiffDecodeOptions(inputTiffFile);
        decodeOptions.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY_BYTES;

        List<Integer> failedDirectoryIndices = new ArrayList<>();
        String inputFilename = inputTiffFile.getName();

        int directoryCount = decodeOptions.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputFilename = buildOutputFilename(
                    inputFilename,
                    directoryIndex + 1,
                    directoryCount,
                    outputFormat
            );
            File outputFile = new File(outputDirectory, outputFilename);

            decodeOptions.inDirectoryNumber = directoryIndex;

            try {
                encodeTiffDirectory(inputTiffFile, outputFormat, decodeOptions, outputFile);
            } catch (EncodeException e) {
                outputFile.delete();
                failedDirectoryIndices.add(directoryIndex);
            }

            convertedImageTuples.add(
                    new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile))
            );
        }

        if (!failedDirectoryIndices.isEmpty()) {
            throw new EncodeException(failedDirectoryIndices);
        }

        return convertedImageTuples;
    }

    public static boolean isTiff(File file) {
        TiffBitmapFactory.Options options = createTiffDecodeOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}