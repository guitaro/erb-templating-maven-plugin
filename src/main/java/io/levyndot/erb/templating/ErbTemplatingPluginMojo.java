package io.levyndot.erb.templating;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.script.ScriptEngine;
import java.util.List;

@Mojo(name = "run", requiresProject = false)
public class ErbTemplatingPluginMojo extends AbstractMojo {

    private static final String RUBY_SCRIPT = ""
            + "require 'erb'\n"
            + "require 'ostruct'\n"
            + "require 'java'\n"
            + "\n"
            + "def render(template, variables)\n"
            + "  context = OpenStruct.new(variables).instance_eval do\n"
            + "    variables.each do |k, v|\n"
            + "      instance_variable_set(k, v) if k[0] == '@'\n"
            + "    end\n"
            + "    binding\n"
            + "  end\n"
            + "  ERB.new(template.to_io.read).result(context);\n"
            + "end\n";

    @Parameter(name = "templates", required = true)
    private List<String> templates;

    @Parameter(name = "contexts", required = true)
    private List<String> contexts;

    @Parameter(name = "destDir", required = false)
    private String destDir;

    @Parameter(name = "skip", required = false)
    private boolean skip;

    private ScriptEngine jruby;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Execution skipped");
            return;
        }

        // TODO Check template files existances
        // TODO Check context files existances
        // TODO Load contexts
        // TODO check destDir existance or create it
        // TODO render each templates and write them into destDir
    }
}
