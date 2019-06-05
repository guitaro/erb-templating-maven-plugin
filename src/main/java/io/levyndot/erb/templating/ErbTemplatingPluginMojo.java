package io.levyndot.erb.templating;

import io.levyndot.erb.templating.exception.FileCheckPluginException;
import io.levyndot.erb.templating.exception.FilePluginException;
import io.levyndot.erb.templating.exception.RubyPluginException;
import io.levyndot.erb.templating.utils.FilesUtils;
import io.levyndot.erb.templating.utils.JRubyUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Erb templating plugin mojo.
 */
@Mojo(name = "run", requiresProject = true)
public class ErbTemplatingPluginMojo extends AbstractMojo {

    @Parameter(name = "templates", required = true)
    private List<String> templates;

    @Parameter(name = "contexts", required = true)
    private List<String> contexts;

    @Parameter(property = "project.build.directory", name = "targetDir", required = false, defaultValue = "${project.build.directory}")
    private String targetDir;

    @Parameter(name = "removeExtension", required = false, defaultValue = "false")
    private boolean removeExtension;

    @Parameter(name = "skip", required = false, defaultValue = "false")
    private boolean skip;

    @Parameter(property = "project", readonly = true, defaultValue = "${project}")
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Execution skipped");
            return;
        }

        // Check params nullity
        if(Objects.isNull(templates)) {
            templates = new ArrayList<>();
        }
        if(Objects.isNull(contexts)) {
            contexts = new ArrayList<>();
        }
        if(Objects.isNull(targetDir) || targetDir.isEmpty()) {
            targetDir = project.getBuild().getOutputDirectory();
        }

        FilesUtils.getInstance().setProjectBaseDir(project.getBasedir().getAbsolutePath());

        // Check template files existances
        try {
            // templates
            FilesUtils.getInstance().checkFileExist(templates);
            // Contextes
            FilesUtils.getInstance().checkFileExist(contexts);
        } catch (final FileCheckPluginException e) {
            throw new MojoExecutionException(e.getMessage());
        }

        // render each templates and write them into targetDir
        for (final String templateFile : templates) {
            String result;
            try {
                result = JRubyUtils.getInstance().render(FilesUtils.getInstance().getAbsolutePath(templateFile), contexts);
            } catch (final RubyPluginException | FileCheckPluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }

            // Compute output file name
            String outfile = FilesUtils.getInstance().getTargetFilename(targetDir, templateFile, removeExtension);

            // Write file
            try {
                FilesUtils.getInstance().write(outfile, result);
            } catch (final FilePluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }
        }
    }
}
