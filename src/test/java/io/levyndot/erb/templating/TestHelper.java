package io.levyndot.erb.templating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * The type Test helper.
 *
 * @author NAGY Levente - 06/06/2019.
 */
public final class TestHelper {

    /**
     * The constant CONTEXT_YAML.
     */
    public static final String CONTEXT_YAML = "context.yaml";
    /**
     * The constant TEMPLATE_TXT_ERB.
     */
    public static final String TEMPLATE_TXT_ERB = "template.txt.erb";
    /**
     * The constant ROOT_RESOURCES_DIR.
     */
    public static final String ROOT_RESOURCES_DIR = ".";
    /**
     * The constant TEST_DIR.
     */
    public static final String TEST_DIR = "testdir";
    /**
     * The constant TEST_FILE.
     */
    public static final String TEST_FILE = "test.file";

    private static final List<String> FILE_TO_CLEAN = Arrays.asList(TEST_DIR, TEST_FILE);

    /**
     * Gets context test file.
     *
     * @return the context test file
     */
    public static final File getContextTestFile() {
        return new File(TestHelper.class.getClassLoader().getResource(CONTEXT_YAML).getFile());
    }

    /**
     * Gets template test file.
     *
     * @return the template test file
     */
    public static final File getTemplateTestFile() {
        return new File(TestHelper.class.getClassLoader().getResource(TEMPLATE_TXT_ERB).getFile());
    }

    /**
     * Gets resources folder.
     *
     * @return the resources folder
     */
    public static final File getResourcesFolder() {
        return new File(TestHelper.class.getClassLoader().getResource(ROOT_RESOURCES_DIR).getFile());
    }

    /**
     * Clean resource dir.
     *
     * @throws IOException the io exception
     */
    public static final void cleanResourceDir() throws IOException {
        Files.walk(getResourcesFolder().toPath())
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(file -> {
                    if(FILE_TO_CLEAN.contains(file.getName())) {
                        file.delete();
                    }
                });
    }

    /**
     * Read file string.
     *
     * @param path the path
     * @return the string
     */
    public static final String readFile(String path) {
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine(); StringBuilder sb = new StringBuilder();
            while(line != null){
                sb.append(line).append("\n"); line = buf.readLine();
            }
            return sb.toString();
        } catch (final IOException e) {
            // Do nothing
        }
        return null;
    }
}
