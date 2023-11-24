package me.nikonbite.emposium.web.template;

import java.util.Map;
import java.util.regex.Pattern;

public record EmposiumTemplate(String name, String content) {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{([^}]+)\\}");

    public String process(Map<String, String> placeholders) {
        return replacePlaceholders(content, placeholders);
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