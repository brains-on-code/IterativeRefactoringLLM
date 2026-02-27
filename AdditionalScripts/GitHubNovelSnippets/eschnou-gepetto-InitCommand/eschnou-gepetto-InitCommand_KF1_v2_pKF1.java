package sh.gepetto.app.cli;

import sh.gepetto.app.config.ApplicationConfig;
import sh.gepetto.app.config.Constants;
import sh.gepetto.app.service.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * circuit sample reactions stock already confident stadium
 */
@Component
@Command(
    name = "init",
    description = "Initialize a new Gepetto project"
)
public class InitCommand implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitCommand.class);

    private final ApplicationConfig applicationConfig;
    private final ConfigurationService configurationService;

    @Option(
        names = {"--var", "-v"},
        description = "Define a variable in format NAME=VALUE",
        split = ","
    )
    private Map<String, String> commandLineVariables;

    public InitCommand(ApplicationConfig applicationConfig, ConfigurationService configurationService) {
        this.applicationConfig = applicationConfig;
        this.configurationService = configurationService;
    }

    @Override
    public void run() {
        Path projectDirectoryPath = Paths.get(Constants.PROJECT_DIR);
        if (Files.exists(projectDirectoryPath)) {
            System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
            System.out.println("Use 'configure' command to update existing configuration.");
            return;
        }

        try {
            if (commandLineVariables != null && !commandLineVariables.isEmpty()) {
                commandLineVariables.forEach(applicationConfig::setVariable);
            }

            createProjectStructure();
            writeConfigurationFile();
            writeSampleTaskFile();

            System.out.println("Gepetto project initialized successfully!");
            System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
            System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
            System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
            System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        } catch (IOException exception) {
            LOGGER.error("Error initializing project: {}", exception.getMessage(), exception);
            System.err.println("Error initializing project: " + exception.getMessage());
        }
    }

    private void createProjectStructure() throws IOException {
        Path projectDirectoryPath = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(projectDirectoryPath)) {
            LOGGER.info("Creating project directory: {}", projectDirectoryPath);
            Files.createDirectories(projectDirectoryPath);
        }

        Path tasksDirectoryPath = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(tasksDirectoryPath)) {
            LOGGER.info("Creating tasks directory: {}", tasksDirectoryPath);
            Files.createDirectories(tasksDirectoryPath);
        }

        Path resultsDirectoryPath = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(resultsDirectoryPath)) {
            LOGGER.info("Creating results directory: {}", resultsDirectoryPath);
            Files.createDirectories(resultsDirectoryPath);
        }
    }

    private void writeConfigurationFile() throws IOException {
        Path configurationFilePath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder configurationContent = new StringBuilder();
        configurationContent.append("# Gepetto Configuration\n\n");

        configurationContent.append("variables:\n");
        if (commandLineVariables != null && !commandLineVariables.isEmpty()) {
            for (Map.Entry<String, String> variableEntry : commandLineVariables.entrySet()) {
                configurationContent
                    .append("  ")
                    .append(variableEntry.getKey())
                    .append(": \"")
                    .append(variableEntry.getValue())
                    .append("\"\n");
            }
        } else {
            configurationContent.append("  # Example variables:\n");
            configurationContent.append("  HOSTNAME: \n");
            configurationContent.append("  LOCATION: \n");
        }

        configurationContent.append("debug: false\n");
        configurationContent.append("timeout: 30000\n");

        Files.writeString(configurationFilePath, configurationContent.toString());
    }

    private void writeSampleTaskFile() throws IOException {
        Path sampleTaskFilePath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String createdDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder sampleTaskContent = new StringBuilder();
        sampleTaskContent.append("# Sample Task\n");
        sampleTaskContent.append("description: \"Check weather for a US city\"\n");
        sampleTaskContent.append("tags: [smoketest]\n");
        sampleTaskContent.append("author: \"Gepetto\"\n");
        sampleTaskContent.append("created: \"").append(createdDate).append("\"\n\n");
        sampleTaskContent.append("Task:\n");
        sampleTaskContent.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        sampleTaskContent.append("  Search the weather for ${LOCATION}.\n");
        sampleTaskContent.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskFilePath, sampleTaskContent.toString());
    }
}