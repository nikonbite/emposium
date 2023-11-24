package me.nikonbite.emposium.web.template.manager;

import me.nikonbite.emposium.util.Logger;
import me.nikonbite.emposium.web.template.EmposiumTemplate;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EmposiumTemplateManager {
    private final Map<String, EmposiumTemplate> templateRegistry;

    public EmposiumTemplateManager() {
        this.templateRegistry = new HashMap<>();
        load();
    }

    private void load() {
        try {
            Files.walk(Paths.get("src/main/resources"), FileVisitOption.FOLLOW_LINKS)
                .filter(path -> path.toString().endsWith(".emposium"))
                .forEach(this::register);
        } catch (IOException e) {
            throw new RuntimeException("Error loading templates", e);
        }
    }

    private void register(Path filePath) {
        var templateName = filePath.getFileName().toString().replace(".emposium", "");
        var templateContent = read(filePath);

        var emposiumTemplate = new EmposiumTemplate(templateName, templateContent);
        templateRegistry.put(templateName, emposiumTemplate);

        Logger.logf("%sTemplate %s registered successfully.", Logger.PREFIX, templateName + ".emposium");
    }

    private String read(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading template file: " + filePath, e);
        }
    }

    public EmposiumTemplate template(String templateName) {
        return templateRegistry.get(templateName);
    }
}