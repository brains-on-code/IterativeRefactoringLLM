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
            applyCommandLineVariablesToConfig();
            createProjectDirectories();
            createConfigurationFile();
            createSampleTaskFile();
            printInitializationSummary();
        } catch (IOException exception) {
            LOGGER.error("Error initializing project: {}", exception.getMessage(), exception);
            System.err.println("Error initializing project: " + exception.getMessage());
        }
    }

    private void applyCommandLineVariablesToConfig() {
        if (commandLineVariables == null || commandLineVariables.isEmpty()) {
            return;
        }
        commandLineVariables.forEach(applicationConfig::setVariable);
    }

    private void createProjectDirectories() throws IOException {
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

    private void createConfigurationFile() throws IOException {
        Path configurationFilePath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder configurationContentBuilder = new StringBuilder();
        configurationContentBuilder.append("# Gepetto Configuration\n\n");

        configurationContentBuilder.append("variables:\n");
        if (commandLineVariables != null && !commandLineVariables.isEmpty()) {
            for (Map.Entry<String, String> variableEntry : commandLineVariables.entrySet()) {
                configurationContentBuilder
                    .append("  ")
                    .append(variableEntry.getKey())
                    .append(": \"")
                    .append(variableEntry.getValue())
                    .append("\"\n");
            }
        } else {
            configurationContentBuilder.append("  # Example variables:\n");
            configurationContentBuilder.append("  HOSTNAME: \n");
            configurationContentBuilder.append("  LOCATION: \n");
        }

        configurationContentBuilder.append("debug: false\n");
        configurationContentBuilder.append("timeout: 30000\n");

        Files.writeString(configurationFilePath, configurationContentBuilder.toString());
    }

    private void createSampleTaskFile() throws IOException {
        Path sampleTaskFilePath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String currentDateIso = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder sampleTaskContentBuilder = new StringBuilder();
        sampleTaskContentBuilder.append("# Sample Task\n");
        sampleTaskContentBuilder.append("description: \"Check weather for a US city\"\n");
        sampleTaskContentBuilder.append("tags: [smoketest]\n");
        sampleTaskContentBuilder.append("author: \"Gepetto\"\n");
        sampleTaskContentBuilder.append("created: \"").append(currentDateIso).append("\"\n\n");
        sampleTaskContentBuilder.append("Task:\n");
        sampleTaskContentBuilder.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        sampleTaskContentBuilder.append("  Search the weather for ${LOCATION}.\n");
        sampleTaskContentBuilder.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskFilePath, sampleTaskContentBuilder.toString());
    }

    private void printInitializationSummary() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
    }
}