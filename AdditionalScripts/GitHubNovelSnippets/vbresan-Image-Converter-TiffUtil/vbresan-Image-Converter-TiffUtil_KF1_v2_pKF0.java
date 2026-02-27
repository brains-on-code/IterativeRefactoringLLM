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

    private static final int DEFAULT_AVAILABLE_MEMORY = 10_000_000;

    private TiffImageConverter() {
        // Utility class
    }

    private static TiffBitmapFactory.Options createTiffOptions(File file) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(file, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String buildOutputFileName(
            String baseName,
            int pageIndex,
            int pageCount,
            OutputFormat format
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                pageIndex,
                pageCount
        );

        int dotIndex = baseName.lastIndexOf('.');
        String nameWithoutExtension =
                (dotIndex == -1) ? baseName : baseName.substring(0, dotIndex);

        return nameWithoutExtension + pageSuffix + "." + format.getFileExtension();
    }

    private static void convertTiffDirectly(
            OutputFormat format,
            String inputPath,
            String outputPath,
            int directoryNumber
    ) throws Exception {

        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        options.readTiffDirectory = directoryNumber;
        options.throwExceptions = true;

        switch (format) {
            case BMP:
                TiffConverter.convertTiffBmp(inputPath, outputPath, options, null);
                break;
            case JPG:
                TiffConverter.convertTiffJpg(inputPath, outputPath, options, null);
                break;
            case PNG:
                TiffConverter.convertTiffPng(inputPath, outputPath, options, null);
                break;
            default:
                throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void saveBitmapAsTiff(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions options = new TiffSaver.SaveOptions();
        options.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, options);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    private static void encodeTiffDirectory(
            File inputFile,
            OutputFormat format,
            TiffBitmapFactory.Options options,
            File outputFile
    ) throws EncodeException {

        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputFile, options);
        int directoryNumber = options.inDirectoryNumber;

        try {
            if (bitmap != null) {
                try {
                    Converter.encodeBitmap(bitmap, format, outputFile);
                } finally {
                    bitmap.recycle();
                }
            } else {
                String inputPath = inputFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertTiffDirectly(format, inputPath, outputPath, directoryNumber);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertTiff(
            File inputFile,
            OutputFormat format,
            File outputDirectory
    ) throws EncodeException {

        List<FilenameUriTuple> convertedFiles = new ArrayList<>();

        TiffBitmapFactory.Options options = createTiffOptions(inputFile);
        options.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputName = inputFile.getName();
        int directoryCount = options.outDirectoryCount;

        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {
            String outputFileName = buildOutputFileName(
                    inputName,
                    directoryIndex + 1,
                    directoryCount,
                    format
            );
            File outputFile = new File(outputDirectory, outputFileName);

            options.inDirectoryNumber = directoryIndex;

            try {
                encodeTiffDirectory(inputFile, format, options, outputFile);
            } catch (EncodeException e) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            convertedFiles.add(new FilenameUriTuple(outputFileName, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return convertedFiles;
    }

    public static boolean isTiff(File file) {
        TiffBitmapFactory.Options options = createTiffOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}