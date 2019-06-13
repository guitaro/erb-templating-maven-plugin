# erb-templating-maven-plugin
Maven plugin which parse ERB file templates with YAML variables contexts.

## Syntax
Let's take for example this context :
``` yaml
name: "world!"
var2:
    attribute: 123
shopping_list:
 - potatoes
 - tomatoes
 - eggs
```
### Variables
``` ruby
<%# This is just a comment %>
Hello, <%= @name %>.
Today is <%= Time.now.strftime('%A') %>.
```
### Structure controle
#### If/else
``` ruby
<%= if @var1 == "someText" %>
    I'm in if
<% else %>
    I'm in else
<% end %>
```
#### For
``` ruby
<% for @item in @shopping_list %>
  <%= @item %>
<% end %>
```
#### Foreach (array)
``` ruby
<%= if @array.each do |value| %>
    <%= @value %>
<% end %>
```
#### Foreach (map)
Iterate through key/value pair :
``` ruby
<%= if @map.each do |key, value| %>
    <%= @key %> => <%= @value %>
<% end %>
```
Iterate through keys :
``` ruby
<%= if @map.each_key do |key| %>
    <%= @key %> => <%= @map[@key] %>
<% end %>
```
Iterate through values :
``` ruby
<%= if @map.each_value do |value| %>
    <%= @value %>
<% end %>
```

## How to use

Add maven plugin dependency :
``` xml
<build>
    <plugins>
        <plugin>
            <groupId>io.levyndot</groupId>
            <artifactId>erb-templating-maven-plugin</artifactId>
            <version>1.0</version>
            ...
        </plugin>
    </plugins>
</build>
```

Configure execution :
```xml
<executions>
    <execution>
        <id>templating</id>
        <goals>
            <goal>run</goal>
        </goals>
        <configuration>
            <templates>
                <template>${project.basedir}/templates/**/*</template>
                <template>${project.basedir}/other_templates/template.txt.erb</template>
            </templates>
            <contexts>
                <context>${project.basedir}/contexts/**/*</context>
                <context>${project.basedir}/other_context/vars.yaml</context>
            </contexts>
            <targetDir>${project.build.directory}</targetDir>
            <removeExtension>true</removeExtension>
            <skip>false</skip>
        </configuration>
    </execution>
</executions>
```

Options :

|name|type|Default|Description|
|---|:---:|:---:|---|
| templates | List | empty list | List of ERB templates files. Can be a wildcard on the end of path. Middle wildcards are not supported. |
| contexts | List | empty list | List of YAML variables files. Can be a wildcard on the end of path. Middle wildcards are not supported. |
|targetDir| String | ${project.build.directory} | Path to output files. |
|removeExtension| Boolean | false | If true, so output file will be renamed without ".erb" extension. |
|skip| Boolean | false | If true, the plugin will not be run. |