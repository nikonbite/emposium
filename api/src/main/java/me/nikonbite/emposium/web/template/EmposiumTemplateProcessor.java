package me.nikonbite.emposium.web.template;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class EmposiumTemplateProcessor {
    private final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    public String processTemplate(String templateName, Map<String, String> placeholders) {
        String templateContent = loadTemplate(templateName);
        return replacePlaceholders(templateContent, placeholders);
    }

    private String loadTemplate(String templateName) {
        var templatePath = "resources/" + templateName + ".emposium";

        var inputStream = EmposiumTemplateProcessor.class.getClassLoader().getResourceAsStream(templatePath);

        if (inputStream == null) {
            System.out.println("InputStream is null!");
            return null;
        }

        var reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    private String replacePlaceholders(String templateContent, Map<String, String> placeholders) {
        var matcher = PLACEHOLDER_PATTERN.matcher(templateContent);
        var result = new StringBuilder();

        while (matcher.find()) {
            var placeholder = matcher.group(1);
            var replacement = placeholders.getOrDefault(placeholder, "");

            matcher.appendReplacement(result, replacement);
        }

        matcher.appendTail(result);
        return result.toString();
    }
}
