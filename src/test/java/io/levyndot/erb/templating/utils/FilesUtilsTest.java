package io.levyndot.erb.templating.utils;

import io.levyndot.erb.templating.TestHelper;
import io.levyndot.erb.templating.exception.FileCheckPluginException;
import io.levyndot.erb.templating.exception.FilePluginException;
import io.levyndot.erb.templating.exception.ReadFilePluginException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Files utils.
 *
 * @author NAGY Levente - 04/06/2019.
 */
public final class FilesUtilsTest {

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field instance = FilesUtils.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @After
    public void tearDown() throws IOException {
        TestHelper.cleanResourceDir();
    }

    @Test
    public void testCheckFileExistWithFiles() throws FileCheckPluginException {
        File contextTestFile = TestHelper.getContextTestFile();
        File templateTestFile = TestHelper.getTemplateTestFile();
        List<String> fileList = Arrays.asList(contextTestFile.getAbsolutePath(), templateTestFile.getAbsolutePath());

        List<String> template = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.ERB_FILE_EXT);
        List<String> context = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.YAML_FILE_EXT);

        Assert.assertEquals(1, template.size());
        Assert.assertEquals(templateTestFile.getAbsolutePath(), template.get(0));
        Assert.assertEquals(1, context.size());
        Assert.assertEquals(contextTestFile.getAbsolutePath(), context.get(0));
    }

    @Test
    public void testCheckFileExistWithFolder() throws FileCheckPluginException {
        File contextTestFile = TestHelper.getContextTestFile();
        File templateTestFile = TestHelper.getTemplateTestFile();
        List<String> fileList = Collections.singletonList(TestHelper.getResourcesFolder().getAbsolutePath());

        List<String> template = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.ERB_FILE_EXT);
        List<String> context = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.YAML_FILE_EXT);

        Assert.assertEquals(1, template.size());
        Assert.assertEquals(templateTestFile.getAbsolutePath(), template.get(0));
        Assert.assertEquals(1, context.size());
        Assert.assertEquals(contextTestFile.getAbsolutePath(), context.get(0));
    }

    @Test
    public void testCheckFileExistWithFolderWithWildcard() throws FileCheckPluginException {
        File contextTestFile = TestHelper.getContextTestFile();
        File templateTestFile = TestHelper.getTemplateTestFile();
        List<String> fileList = Collections.singletonList(TestHelper.getResourcesFolder().getAbsolutePath() + "/**/*");

        List<String> template = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.ERB_FILE_EXT);
        List<String> context = FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.YAML_FILE_EXT);

        Assert.assertEquals(1, template.size());
        Assert.assertEquals(templateTestFile.getAbsolutePath(), template.get(0));
        Assert.assertEquals(1, context.size());
        Assert.assertEquals(contextTestFile.getAbsolutePath(), context.get(0));
    }

    @Test(expected = FileCheckPluginException.class)
    public void testCheckFileExistFileNotFound() throws FileCheckPluginException {
        List<String> fileList = Collections.singletonList(TestHelper.getResourcesFolder().getAbsolutePath() + "/fake.file");
        FilesUtils.getInstance().checkFileExist(fileList, FilesUtils.ERB_FILE_EXT);
    }

    @Test
    public void testCreateDirectoryIfNotExistsOK() throws FileCheckPluginException {
        File resourcesFolder = TestHelper.getResourcesFolder();
        String path = resourcesFolder.getAbsolutePath() + "/" + TestHelper.TEST_DIR;

        FilesUtils.getInstance().createDirectoryIfNotExists(path);

        Assert.assertTrue(Files.exists(Paths.get(path)));
    }

    @Test(expected = FileCheckPluginException.class)
    public void testCreateDirectoryIfNotExistsWithBadName() throws FileCheckPluginException {
        File resourcesFolder = TestHelper.getResourcesFolder();
        String path = resourcesFolder.getAbsolutePath() + "/" + TestHelper.TEST_DIR + ":";

        FilesUtils.getInstance().createDirectoryIfNotExists(path);

        Assert.assertTrue(Files.exists(Paths.get(path)));
    }

    @Test
    public void testGetInputStreamOK() throws FilePluginException {
        InputStream inputStream = FilesUtils.getInstance().getInputStream(TestHelper.getContextTestFile().getAbsolutePath());
        Assert.assertNotNull(inputStream);
    }

    @Test(expected = ReadFilePluginException.class)
    public void testGetInputStreamKO() throws FilePluginException {
        FilesUtils.getInstance().getInputStream("/fake/path/to/fake/file.txt");
    }

    @Test
    public void testWriteOK() throws FilePluginException {
        FilesUtils.getInstance()
                .write(TestHelper.getResourcesFolder().getAbsolutePath() + "/" + TestHelper.TEST_FILE,
                "Hello, world!");
    }

    @Test
    public void testGetAbsolutePathOK() throws FileCheckPluginException {
        String absolutePath = FilesUtils.getInstance().getAbsolutePath("target/test-classes/context.yaml");
        Assert.assertEquals(TestHelper.getContextTestFile().getAbsolutePath() , absolutePath);
    }

    @Test(expected = FileCheckPluginException.class)
    public void testGetAbsolutePathFileNotFound() throws FileCheckPluginException {
        FilesUtils.getInstance().getAbsolutePath("context.yaml");
    }

    @Test
    public void testGetTargetFilename() {
        FilesUtils.getInstance().setProjectBaseDir("basedir");
        String targetFilename = FilesUtils.getInstance().getTargetFilename("target", "basedir/file.txt.erb", true);

        Assert.assertEquals("target/file.txt", targetFilename);
    }
}
