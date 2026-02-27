package sh.gepetto.app.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import sh.gepetto.app.config.ApplicationConfig;
import sh.gepetto.app.config.Constants;
import sh.gepetto.app.service.ConfigurationService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Initialize a new Gepetto project.
 */
@Component
@Command(
    name = "init",
    description = "Initialize a new Gepetto project"
)
public class InitCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitCommand.class);

    private static final String NEW_LINE = System.lineSeparator();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    private final ApplicationConfig applicationConfig;
    private final ConfigurationService configurationService;

    @Option(
        names = {"--var", "-v"},
        description = "Define a variable in format NAME=VALUE",
        split = ","
    )
    private Map<String, String> variables;

    public InitCommand(ApplicationConfig applicationConfig, ConfigurationService configurationService) {
        this.applicationConfig = applicationConfig;
        this.configurationService = configurationService;
    }

    @Override
    public void run() {
        Path projectDir = Paths.get(Constants.PROJECT_DIR);

        if (Files.exists(projectDir)) {
            printAlreadyInitializedMessage();
            return;
        }

        try {
            applyVariables();
            createProjectStructure(projectDir);
            writeConfigFile(projectDir);
            writeSampleTaskFile(projectDir);
            printSuccessMessage();
        } catch (IOException e) {
            handleInitializationError(e);
        }
    }

    private void applyVariables() {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        variables.forEach(applicationConfig::setVariable);
    }

    private void createProjectStructure(Path projectDir) throws IOException {
        createDirectoryIfMissing(projectDir, "project");
        createDirectoryIfMissing(projectDir.resolve(Constants.TASKS_DIR), "tasks");
        createDirectoryIfMissing(projectDir.resolve(Constants.RESULTS_DIR), "results");
    }

    private void createDirectoryIfMissing(Path dir, String description) throws IOException {
        if (Files.exists(dir)) {
            return;
        }
        LOGGER.info("Creating {} directory: {}", description, dir);
        Files.createDirectories(dir);
    }

    private void writeConfigFile(Path projectDir) throws IOException {
        Path configPath = projectDir.resolve(Constants.CONFIG_FILENAME);
        Files.writeString(configPath, buildConfigContent());
    }

    private String buildConfigContent() {
        StringBuilder content = new StringBuilder()
            .append("# Gepetto Configuration").append(NEW_LINE).append(NEW_LINE)
            .append("variables:").append(NEW_LINE);

        if (variables != null && !variables.isEmpty()) {
            variables.forEach((key, value) ->
                content.append("  ")
                    .append(key)
                    .append(": \"")
                    .append(value)
                    .append("\"")
                    .append(NEW_LINE)
            );
        } else {
            content.append("  # Example variables:").append(NEW_LINE)
                .append("  HOSTNAME: ").append(NEW_LINE)
                .append("  LOCATION: ").append(NEW_LINE);
        }

        content.append("debug: false").append(NEW_LINE)
            .append("timeout: 30000").append(NEW_LINE);

        return content.toString();
    }

    private void writeSampleTaskFile(Path projectDir) throws IOException {
        Path sampleTaskPath = projectDir
            .resolve(Constants.TASKS_DIR)
            .resolve(Constants.SAMPLE_TASK_FILENAME);

        Files.writeString(sampleTaskPath, buildSampleTaskContent());
    }

    private String buildSampleTaskContent() {
        String createdDate = LocalDate.now().format(DATE_FORMATTER);

        return new StringBuilder()
            .append("# Sample Task").append(NEW_LINE)
            .append("description: \"Check weather for a US city\"").append(NEW_LINE)
            .append("tags: [smoketest]").append(NEW_LINE)
            .append("author: \"Gepetto\"").append(NEW_LINE)
            .append("created: \"").append(createdDate).append("\"").append(NEW_LINE).append(NEW_LINE)
            .append("Task:").append(NEW_LINE)
            .append("  Navigate to ${HOSTNAME} and verify you are at the weather service.").append(NEW_LINE)
            .append("  Search the weather for ${LOCATION}.").append(NEW_LINE)
            .append("  Verify that the weather matches the requested location.").append(NEW_LINE)
            .toString();
    }

    private void printAlreadyInitializedMessage() {
        System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
        System.out.println("Use 'configure' command to update existing configuration.");
    }

    private void printSuccessMessage() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println(
            NEW_LINE + "To run your task: gepetto run "
                + Constants.PROJECT_DIR
                + "/"
                + Constants.TASKS_DIR
                + "/"
                + Constants.SAMPLE_TASK_FILENAME
        );
    }

    private void handleInitializationError(IOException e) {
        LOGGER.error("Error initializing project: {}", e.getMessage(), e);
        System.err.println("Error initializing project: " + e.getMessage());
    }
}