package io.levyndot.erb.templating.utils;

import io.levyndot.erb.templating.exception.FileCheckPluginException;
import io.levyndot.erb.templating.exception.FilePluginException;
import io.levyndot.erb.templating.exception.ReadFilePluginException;
import io.levyndot.erb.templating.exception.WriteFilePluginException;
import org.apache.maven.shared.utils.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * The type Files utils.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public class FilesUtils {

    /**
     * Check file existance.
     *
     * @param files the files
     * @throws FileCheckPluginException the file not found plugin exception
     */
    public static final void checkFileExist(final List<String> files) throws FileCheckPluginException {
        if (Objects.nonNull(files)) {
            for(final String path : files) {
                if(FileUtils.fileExists(path)) {
                    throw new FileCheckPluginException(String.format("File [%s] does not exist", path));
                }
            }
        }
    }

    /**
     * Create directory if not exists.
     *
     * @param path the files
     * @return the file
     * @throws FileCheckPluginException the file check plugin exception
     */
    public static final File createDirectoryIfNotExists(final String path) throws FileCheckPluginException {
        File dir = new File(path);
        if(!FileUtils.fileExists(path)) {
            try {
                FileUtils.forceMkdir(dir);
            } catch (final IOException e) {
                throw new FileCheckPluginException(String.format("Cannot create directory [%s]", path));
            }
        }
        return dir;
    }

    /**
     * Gets input stream.
     *
     * @param path the path
     * @return the input stream
     * @throws FilePluginException the file plugin exception
     */
    public static InputStream getInputStream(final String path) throws FilePluginException {
        try {
            return new FileInputStream(path);
        } catch (final FileNotFoundException e) {
            throw new ReadFilePluginException(e);
        }
    }

    /**
     * Gets output stream.
     *
     * @param path the path
     * @return the output stream
     * @throws FilePluginException the file plugin exception
     */
    private static OutputStream getOutputStream(final String path) throws FilePluginException {
        if (path == null || path.isEmpty()) {
            return System.out;
        }

        File outputFile = new File(path);
        if (!outputFile.getParentFile().exists()) {
            createDirectoryIfNotExists(outputFile.getParentFile().getPath());
        }

        try {
            return new FileOutputStream(outputFile);
        } catch (final FileNotFoundException e) {
            throw new WriteFilePluginException(e);
        }
    }

    /**
     * Write.
     *
     * @param filename the filename
     * @param content  the content
     * @throws FilePluginException the file plugin exception
     */
    public static void write(final String filename, final String content) throws FilePluginException {
        OutputStream outputStream = getOutputStream(filename);
        try {
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (final IOException e) {
            throw new WriteFilePluginException(e);
        }
    }
}
