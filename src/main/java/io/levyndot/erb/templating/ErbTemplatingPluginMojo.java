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

    @Parameter(property = "project.build.directory", name = "destDir", required = false, defaultValue = "${project.build.directory}")
    private String destDir;

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
        if(Objects.isNull(destDir) || destDir.isEmpty()) {
            destDir = project.getBuild().getOutputDirectory();
        }

        // Check template files existances
        try {
            // templates
            FilesUtils.checkFileExist(templates);
            // Contextes
            FilesUtils.checkFileExist(contexts);
        } catch (final FileCheckPluginException e) {
            throw new MojoExecutionException(e.getMessage());
        }

        // render each templates and write them into destDir
        for (final String templateFile : templates) {
            String result;
            try {
                result = JRubyUtils.getInstance().render(FilesUtils.getAbsolutePath(templateFile), contexts);
            } catch (final RubyPluginException | FileCheckPluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }

            // Compute output file name
            String outfile = destDir + templateFile.replace(project.getBasedir().getAbsolutePath(), "");
            if (removeExtension && outfile.endsWith(".erb")) {
                outfile = outfile.substring(0, outfile.length() - ".erb".length());
            }

            // Write file
            try {
                FilesUtils.write(outfile, result);
            } catch (final FilePluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }
        }
    }
}
