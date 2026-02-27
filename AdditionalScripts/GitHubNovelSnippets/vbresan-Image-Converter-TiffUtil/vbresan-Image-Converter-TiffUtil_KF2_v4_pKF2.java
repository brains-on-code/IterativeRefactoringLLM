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

public class TiffUtil {

    private static final int DEFAULT_AVAILABLE_MEMORY = 10_000_000;

    private TiffUtil() {
        // Utility class; prevent instantiation
    }

    private static TiffBitmapFactory.Options createDecodeOptions(File file) {
        TiffBitmapFactory.Options options = new TiffBitmapFactory.Options();

        // First pass: decode bounds only to populate metadata
        options.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(file, options);

        // Second pass: allow full decode when requested
        options.inJustDecodeBounds = false;
        return options;
    }

    private static String buildIndexedFilename(
            String originalFilename,
            int index,
            int totalCount,
            OutputFormat format
    ) {
        String indexSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                index,
                totalCount
        );

        int extensionIndex = originalFilename.lastIndexOf('.');
        String baseName = (extensionIndex == -1)
                ? originalFilename + indexSuffix
                : originalFilename.substring(0, extensionIndex) + indexSuffix;

        return baseName + "." + format.getFileExtension();
    }

    private static void convertDirectly(
            OutputFormat format,
            String inputPath,
            String outputPath,
            int directory
    ) throws Exception {

        TiffConverter.ConverterOptions options = new TiffConverter.ConverterOptions();
        options.readTiffDirectory = directory;
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

    static void encodeToTIF(Bitmap bitmap, File file) throws IOException {
        TiffSaver.SaveOptions options = new TiffSaver.SaveOptions();
        options.inThrowException = true;

        try {
            TiffSaver.saveBitmap(file, bitmap, options);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    private static void convertSinglePageFromTiff(
            File inputFile,
            OutputFormat format,
            TiffBitmapFactory.Options options,
            File outputFile
    ) throws EncodeException {

        Bitmap bitmap = TiffBitmapFactory.decodeFile(inputFile, options);
        int directory = options.inDirectoryNumber;

        try {
            if (bitmap != null) {
                Converter.encodeBitmap(bitmap, format, outputFile);
                bitmap.recycle();
            } else {
                convertDirectly(
                        format,
                        inputFile.getAbsolutePath(),
                        outputFile.getAbsolutePath(),
                        directory
                );
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertFromTIF(
            File inputFile,
            OutputFormat format,
            File outputFolder
    ) throws EncodeException {

        List<FilenameUriTuple> outputFiles = new ArrayList<>();

        TiffBitmapFactory.Options options = createDecodeOptions(inputFile);
        options.inAvailableMemory = DEFAULT_AVAILABLE_MEMORY;

        List<Integer> failedDirectories = new ArrayList<>();
        String inputFilename = inputFile.getName();

        int directoryCount = options.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {
            String outputFilename = buildIndexedFilename(
                    inputFilename,
                    directoryIndex + 1,
                    directoryCount,
                    format
            );
            File outputFile = new File(outputFolder, outputFilename);

            options.inDirectoryNumber = directoryIndex;

            try {
                convertSinglePageFromTiff(inputFile, format, options, outputFile);
            } catch (EncodeException e) {
                // Best-effort cleanup of partially written file
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete();
                failedDirectories.add(directoryIndex);
            }

            outputFiles.add(new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile)));
        }

        if (!failedDirectories.isEmpty()) {
            throw new EncodeException(failedDirectories);
        }

        return outputFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options options = createDecodeOptions(file);
        return options.outWidth != -1 || options.outHeight != -1;
    }
}