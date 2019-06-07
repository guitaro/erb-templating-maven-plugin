package io.github.guitaro.erb.templating.utils;

import io.github.guitaro.erb.templating.exception.FilePluginException;
import io.github.guitaro.erb.templating.exception.RubyPluginException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Objects;

/**
 * The type J ruby utils.
 *
 * @author NAGY Levente - 05/06/2019.
 */
public final class JRubyUtils {

    private static ScriptEngine jruby;
    private static final String FUNC_NAME = "render";
    private static final String RUBY_SCRIPT = "" +
            "require 'erb'\n" +
            "require 'ostruct'\n" +
            "require 'yaml'\n" +
            "\n" +
            "def render(template, contexts)\n" +
            "  context = OpenStruct.new.instance_eval do\n" +
            "    contexts.each do |file|\n" +
            "      yml_file = YAML.load_file(file)\n" +
            "      yml_file.each_key { |key|\n" +
            "        var_value = yml_file[key]\n" +
            "        var_name = \"@#{key}\"  # the '@' is required\n" +
            "        self.instance_variable_set(var_name, var_value)\n" +
            "      }\n" +
            "    end\n" +
            "    binding\n" +
            "  end\n" +
            "  ERB.new(template.to_io.read).result(context)\n" +
            "end\n";

    private static JRubyUtils instance;

    private JRubyUtils() {
        jruby = new ScriptEngineManager().getEngineByName("jruby");
        try {
            jruby.eval(RUBY_SCRIPT);
        } catch (final ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized JRubyUtils getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JRubyUtils();
        }
        return instance;
    }

    /**
     * Render string.
     *
     * @param templateFilePath the template file path
     * @param contextFiles     the context files
     * @return the string
     */
    public String render(final String templateFilePath, final List<String> contextFiles) throws RubyPluginException {
        try {
            return ((Invocable) jruby).invokeFunction(FUNC_NAME, FilesUtils.getInstance().getInputStream(templateFilePath), contextFiles).toString();
        } catch (ScriptException | NoSuchMethodException | FilePluginException e) {
            throw new RubyPluginException(e.getMessage());
        }
    }
}
