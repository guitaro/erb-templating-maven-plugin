require 'erb'
require 'ostruct'
require 'yaml'

def render(template, contexts)
  context = OpenStruct.new.instance_eval do
    contexts.each do |file|
      yml_file = YAML.load_file(file)
      yml_file.each_key { |key|
        var_value = yml_file[key].to_s
        var_name = "@#{key}"  # the '@' is required
        self.instance_variable_set(var_name, var_value)
      }
    end
    binding
  end
  ERB.new(template.to_io.read).result(context)
end