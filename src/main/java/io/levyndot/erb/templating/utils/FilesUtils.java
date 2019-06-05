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
public final class FilesUtils {

    private static FilesUtils instance;
    private static final String ERB_FILE_EXT = ".erb";
    private String projectBaseDir = "";

    private FilesUtils() {}

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FilesUtils getInstance() {
        if(Objects.isNull(instance)) {
            instance = new FilesUtils();
        }
        return instance;
    }

    /**
     * Sets project base dir.
     *
     * @param projectBaseDir the project base dir
     */
    public void setProjectBaseDir(final String projectBaseDir) {
        this.projectBaseDir = projectBaseDir;
    }

    /**
     * Check file existance.
     *
     * @param files the files
     * @throws FileCheckPluginException the file not found plugin exception
     */
    public void checkFileExist(final List<String> files) throws FileCheckPluginException {
        if (Objects.nonNull(files)) {
            for(final String path : files) {
                if(!FileUtils.fileExists(path)) {
                    throw new FileCheckPluginException(String.format("File [%s] does not exist", path));
                }
            }
        }
    }

    /**
     * Create directory if not exists.
     *
     * @param path the files
     * @throws FileCheckPluginException the file check plugin exception
     */
    public void createDirectoryIfNotExists(final String path) throws FileCheckPluginException {
        File dir = new File(path);
        if(!FileUtils.fileExists(path)) {
            try {
                FileUtils.forceMkdir(dir);
            } catch (final IOException e) {
                throw new FileCheckPluginException(String.format("Cannot create directory [%s]", path));
            }
        }
    }

    /**
     * Gets input stream.
     *
     * @param path the path
     * @return the input stream
     * @throws FilePluginException the file plugin exception
     */
    public InputStream getInputStream(final String path) throws FilePluginException {
        try {
            return new FileInputStream(path);
        } catch (final FileNotFoundException e) {
            throw new ReadFilePluginException(e);
        }
    }

    /**
     * Write.
     *
     * @param filename the filename
     * @param content  the content
     * @throws FilePluginException the file plugin exception
     */
    public void write(final String filename, final String content) throws FilePluginException {
        OutputStream outputStream = getOutputStream(filename);
        try {
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (final IOException e) {
            throw new WriteFilePluginException(e);
        }
    }

    /**
     * Gets absolute path.
     *
     * @param relativePath the relative path
     * @return the absolute path
     * @throws FileCheckPluginException the file check plugin exception
     */
    public String getAbsolutePath(final String relativePath) throws FileCheckPluginException {
        File file = new File(relativePath);
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            throw new FileCheckPluginException(String.format("File [%s] does not exists.", relativePath));
        }
    }

    /**
     * Gets output stream.
     *
     * @param path the path
     * @return the output stream
     * @throws FilePluginException the file plugin exception
     */
    private OutputStream getOutputStream(final String path) throws FilePluginException {
        if (path == null || path.isEmpty()) {
            return System.out;
        }

        File outputFile = new File(path);
        if (Objects.nonNull(outputFile.getParentFile()) && !outputFile.getParentFile().exists()) {
            createDirectoryIfNotExists(outputFile.getParentFile().getPath());
        }

        try {
            return new FileOutputStream(outputFile);
        } catch (final FileNotFoundException e) {
            throw new WriteFilePluginException(e);
        }
    }

    public String getTargetFilename(final String targetDir, final String templateFile, final boolean removeExtension) {
        String out = targetDir + templateFile.replace(projectBaseDir, "");
        if (removeExtension && out.endsWith(ERB_FILE_EXT)) {
            out = out.substring(0, out.length() - ERB_FILE_EXT.length());
        }
        return out;
    }
}
