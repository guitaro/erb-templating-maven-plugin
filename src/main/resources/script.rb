require 'erb'
require 'ostruct'
require 'java'

def render(template, variables)
  context = OpenStruct.new(variables).instance_eval do
    variables.each do |k, v|
      instance_variable_set(k, v) if k[0] == '@'
    end
    binding
  end
  ERB.new(template.to_io.read).result(context);
end