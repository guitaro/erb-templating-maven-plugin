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

    @Parameter(property = "templates", name = "templates", required = true)
    private List<String> templates;

    @Parameter(property = "contexts", name = "contexts", required = true)
    private List<String> contexts;

    @Parameter(property = "destDir", name = "destDir", required = true, defaultValue = "${project.build.directory}")
    private String destDir;

    @Parameter(property = "removeExtension", name = "removeExt", required = false, defaultValue = "false")
    private boolean removeExtension;

    @Parameter(property = "skip", name = "skip", required = false, defaultValue = "false")
    private boolean skip;

    @Parameter(property = "project", readonly = true, required = true, defaultValue = "${project}")
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
                result = JRubyUtils.getInstance().render(templateFile, contexts);
            } catch (final RubyPluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }

            // TODO Write file
            try {
                FilesUtils.write("filename", result);
            } catch (final FilePluginException e) {
                throw new MojoExecutionException(e.getMessage());
            }
        }
    }
}
