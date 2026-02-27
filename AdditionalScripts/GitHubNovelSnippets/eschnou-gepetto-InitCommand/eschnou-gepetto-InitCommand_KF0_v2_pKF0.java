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
 * Command for initializing a new Gepetto project
 */
@Component
@Command(
    name = "init",
    description = "Initialize a new Gepetto project"
)
public class InitCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitCommand.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    private final ApplicationConfig appConfig;
    private final ConfigurationService configService;

    @Option(
        names = {"--var", "-v"},
        description = "Define a variable in format NAME=VALUE",
        split = ","
    )
    private Map<String, String> variables;

    public InitCommand(ApplicationConfig appConfig, ConfigurationService configService) {
        this.appConfig = appConfig;
        this.configService = configService;
    }

    @Override
    public void run() {
        Path projectDir = Paths.get(Constants.PROJECT_DIR);

        if (Files.exists(projectDir)) {
            printAlreadyInitializedMessage();
            return;
        }

        try {
            applyVariablesToConfig();
            createProjectStructure();
            createConfigFile();
            createSampleTask();
            printSuccessMessage();
        } catch (IOException e) {
            LOGGER.error("Error initializing project: {}", e.getMessage(), e);
            System.err.println("Error initializing project: " + e.getMessage());
        }
    }

    private void applyVariablesToConfig() {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        variables.forEach(appConfig::setVariable);
    }

    private void createProjectStructure() throws IOException {
        createDirectoryIfNotExists(Paths.get(Constants.PROJECT_DIR));
        createDirectoryIfNotExists(Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR));
        createDirectoryIfNotExists(Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR));
    }

    private void createDirectoryIfNotExists(Path directory) throws IOException {
        if (Files.exists(directory)) {
            return;
        }
        LOGGER.info("Creating directory: {}", directory);
        Files.createDirectories(directory);
    }

    private void createConfigFile() throws IOException {
        Path configPath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);
        Files.writeString(configPath, buildConfigContent());
    }

    private String buildConfigContent() {
        StringBuilder configContent = new StringBuilder()
            .append("# Gepetto Configuration\n\n")
            .append("variables:\n");

        if (variables != null && !variables.isEmpty()) {
            variables.forEach((key, value) ->
                configContent.append("  ")
                    .append(key)
                    .append(": \"")
                    .append(value)
                    .append("\"\n")
            );
        } else {
            configContent
                .append("  # Example variables:\n")
                .append("  HOSTNAME: \n")
                .append("  LOCATION: \n");
        }

        configContent
            .append("debug: false\n")
            .append("timeout: 30000\n");

        return configContent.toString();
    }

    private void createSampleTask() throws IOException {
        Path taskPath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );

        Files.writeString(taskPath, buildSampleTaskContent());
    }

    private String buildSampleTaskContent() {
        String currentDate = LocalDate.now().format(DATE_FORMATTER);

        return new StringBuilder()
            .append("# Sample Task\n")
            .append("description: \"Check weather for a US city\"\n")
            .append("tags: [smoketest]\n")
            .append("author: \"Gepetto\"\n")
            .append("created: \"").append(currentDate).append("\"\n\n")
            .append("Task:\n")
            .append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n")
            .append("  Search the weather for ${LOCATION}.\n")
            .append("  Verify that the weather matches the requested location.\n")
            .toString();
    }

    private void printAlreadyInitializedMessage() {
        System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
        System.out.println("Use 'configure' command to update existing configuration.");
    }

    private void printSuccessMessage() {
        String projectDir = Constants.PROJECT_DIR;
        String configFile = Constants.CONFIG_FILENAME;
        String tasksDir = Constants.TASKS_DIR;
        String sampleTaskFile = Constants.SAMPLE_TASK_FILENAME;

        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + projectDir + "/");
        System.out.println("- Configuration saved to " + projectDir + "/" + configFile);
        System.out.println("- Sample task created at " + projectDir + "/" + tasksDir + "/" + sampleTaskFile);
        System.out.println("\nTo run your task: gepetto run " + projectDir + "/" + tasksDir + "/" + sampleTaskFile);
    }
}