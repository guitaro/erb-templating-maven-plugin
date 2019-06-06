package io.levyndot.erb.templating;


import org.apache.maven.model.Build;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class ErbTemplatingPluginMojoTest {

    private ErbTemplatingPluginMojo subject;

    @Before
    public void setup() {
        subject = new ErbTemplatingPluginMojo();
    }

    @Test
    public void testExecuteOK() throws NoSuchFieldException, IllegalAccessException, MojoExecutionException {
        // Given
        List<String> listTemplates = Collections.singletonList(TestHelper.getTemplateTestFile().getAbsolutePath());
        Field templatesField = ErbTemplatingPluginMojo.class.getDeclaredField("templates");
        templatesField.setAccessible(true);
        templatesField.set(subject, listTemplates);

        List<String> listContexts = Collections.singletonList(TestHelper.getContextTestFile().getAbsolutePath());
        Field contextsField = ErbTemplatingPluginMojo.class.getDeclaredField("contexts");
        contextsField.setAccessible(true);
        contextsField.set(subject, listContexts);

        Field targetDirField = ErbTemplatingPluginMojo.class.getDeclaredField("targetDir");
        targetDirField.setAccessible(true);
        targetDirField.set(subject, null);

        Field removeExtensionField = ErbTemplatingPluginMojo.class.getDeclaredField("removeExtension");
        removeExtensionField.setAccessible(true);
        removeExtensionField.set(subject, true);

        Field skipField = ErbTemplatingPluginMojo.class.getDeclaredField("skip");
        skipField.setAccessible(true);
        skipField.set(subject, false);

        MavenProject project = new MavenProject();
        project.setBuild(new Build());
        project.getBuild().setOutputDirectory(TestHelper.getResourcesFolder().getAbsolutePath());
        project.setFile(new File("./target"));
        Field projectField = ErbTemplatingPluginMojo.class.getDeclaredField("project");
        projectField.setAccessible(true);
        projectField.set(subject, project);

        // Then
        subject.execute();

        File out = new File(TestHelper.getResourcesFolder().getAbsoluteFile() + "/target/test-classes/template.txt");

        Assert.assertTrue(out.exists());
        Assert.assertEquals("Hello, world !\n" +
                "\n" +
                "\n" +
                "  value1\n" +
                "\n" +
                "  value2\n" +
                "\n" +
                "  value3\n", TestHelper.readFile(out.getAbsolutePath()));
    }
}
