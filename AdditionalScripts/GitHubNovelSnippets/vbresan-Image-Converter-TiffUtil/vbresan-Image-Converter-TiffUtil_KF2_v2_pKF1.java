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

    private static TiffBitmapFactory.Options createDecodeOptions(File tiffFile) {
        TiffBitmapFactory.Options decodeOptions = new TiffBitmapFactory.Options();
        decodeOptions.inJustDecodeBounds = true;
        TiffBitmapFactory.decodeFile(tiffFile, decodeOptions);
        decodeOptions.inJustDecodeBounds = false;
        return decodeOptions;
    }

    private static String buildOutputFilename(
            String originalFilename,
            int pageNumber,
            int totalPages,
            OutputFormat outputFormat
    ) {
        String pageSuffix = String.format(
                Locale.getDefault(),
                " (%04d of %04d)",
                pageNumber,
                totalPages
        );

        int extensionIndex = originalFilename.lastIndexOf(".");
        String baseName = (extensionIndex == -1)
                ? originalFilename + pageSuffix
                : originalFilename.substring(0, extensionIndex) + pageSuffix;

        return baseName + "." + outputFormat.getFileExtension();
    }

    private static void convertWithTiffConverter(
            OutputFormat outputFormat,
            String inputPath,
            String outputPath,
            int directoryIndex
    ) throws Exception {

        TiffConverter.ConverterOptions converterOptions = new TiffConverter.ConverterOptions();
        converterOptions.readTiffDirectory = directoryIndex;
        converterOptions.throwExceptions = true;

        if (outputFormat == OutputFormat.BMP) {
            TiffConverter.convertTiffBmp(inputPath, outputPath, converterOptions, null);
        } else if (outputFormat == OutputFormat.JPG) {
            TiffConverter.convertTiffJpg(inputPath, outputPath, converterOptions, null);
        } else if (outputFormat == OutputFormat.PNG) {
            TiffConverter.convertTiffPng(inputPath, outputPath, converterOptions, null);
        } else {
            throw new Exception("Unsupported output format for direct conversion.");
        }
    }

    static void encodeToTIF(Bitmap bitmap, File outputFile) throws IOException {
        TiffSaver.SaveOptions saveOptions = new TiffSaver.SaveOptions();
        saveOptions.inThrowException = true;

        try {
            TiffSaver.saveBitmap(outputFile, bitmap, saveOptions);
        } catch (Exception e) {
            IOException ioException = new IOException(e.toString());
            ioException.setStackTrace(e.getStackTrace());
            throw ioException;
        }
    }

    private static void convertSingleTiffPage(
            File inputFile,
            OutputFormat outputFormat,
            TiffBitmapFactory.Options decodeOptions,
            File outputFile
    ) throws EncodeException {

        Bitmap decodedBitmap = TiffBitmapFactory.decodeFile(inputFile, decodeOptions);
        int directoryIndex = decodeOptions.inDirectoryNumber;

        try {
            if (decodedBitmap != null) {
                Converter.encodeBitmap(decodedBitmap, outputFormat, outputFile);
                decodedBitmap.recycle();
            } else {
                String inputPath = inputFile.getAbsolutePath();
                String outputPath = outputFile.getAbsolutePath();
                convertWithTiffConverter(outputFormat, inputPath, outputPath, directoryIndex);
            }
        } catch (Exception e) {
            throw new EncodeException(e);
        }
    }

    public static List<FilenameUriTuple> convertFromTIF(
            File inputFile,
            OutputFormat outputFormat,
            File outputFolder
    ) throws EncodeException {

        List<FilenameUriTuple> convertedFiles = new ArrayList<>();

        TiffBitmapFactory.Options decodeOptions = createDecodeOptions(inputFile);
        decodeOptions.inAvailableMemory = 10_000_000;

        List<Integer> failedDirectoryIndices = new ArrayList<>();
        String inputFilename = inputFile.getName();

        int directoryCount = decodeOptions.outDirectoryCount;
        for (int directoryIndex = 0; directoryIndex < directoryCount; directoryIndex++) {

            String outputFilename = buildOutputFilename(
                    inputFilename,
                    directoryIndex + 1,
                    directoryCount,
                    outputFormat
            );
            File outputFile = new File(outputFolder, outputFilename);

            decodeOptions.inDirectoryNumber = directoryIndex;

            try {
                convertSingleTiffPage(inputFile, outputFormat, decodeOptions, outputFile);
            } catch (EncodeException e) {
                outputFile.delete();
                failedDirectoryIndices.add(directoryIndex);
            }

            convertedFiles.add(new FilenameUriTuple(outputFilename, Uri.fromFile(outputFile)));
        }

        if (!failedDirectoryIndices.isEmpty()) {
            throw new EncodeException(failedDirectoryIndices);
        }

        return convertedFiles;
    }

    public static boolean isTiffFile(File file) {
        TiffBitmapFactory.Options decodeOptions = createDecodeOptions(file);
        return decodeOptions.outWidth != -1 || decodeOptions.outHeight != -1;
    }
}