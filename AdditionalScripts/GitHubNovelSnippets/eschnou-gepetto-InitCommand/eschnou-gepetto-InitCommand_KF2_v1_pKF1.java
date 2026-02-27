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
    private Map<String, String> cliVariables;

    public InitCommand(ApplicationConfig applicationConfig, ConfigurationService configurationService) {
        this.applicationConfig = applicationConfig;
        this.configurationService = configurationService;
    }

    @Override
    public void run() {
        Path projectDirectory = Paths.get(Constants.PROJECT_DIR);
        if (Files.exists(projectDirectory)) {
            System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
            System.out.println("Use 'configure' command to update existing configuration.");
            return;
        }

        try {
            applyCliVariablesToConfig();
            createProjectStructure();
            createConfigFile();
            createSampleTaskFile();
            printSuccessMessage();
        } catch (IOException exception) {
            LOGGER.error("Error initializing project: {}", exception.getMessage(), exception);
            System.err.println("Error initializing project: " + exception.getMessage());
        }
    }

    private void applyCliVariablesToConfig() {
        if (cliVariables == null || cliVariables.isEmpty()) {
            return;
        }
        cliVariables.forEach(applicationConfig::setVariable);
    }

    private void createProjectStructure() throws IOException {
        Path projectDirectory = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(projectDirectory)) {
            LOGGER.info("Creating project directory: {}", projectDirectory);
            Files.createDirectories(projectDirectory);
        }

        Path tasksDirectory = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(tasksDirectory)) {
            LOGGER.info("Creating tasks directory: {}", tasksDirectory);
            Files.createDirectories(tasksDirectory);
        }

        Path resultsDirectory = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(resultsDirectory)) {
            LOGGER.info("Creating results directory: {}", resultsDirectory);
            Files.createDirectories(resultsDirectory);
        }
    }

    private void createConfigFile() throws IOException {
        Path configFilePath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder configContentBuilder = new StringBuilder();
        configContentBuilder.append("# Gepetto Configuration\n\n");

        configContentBuilder.append("variables:\n");
        if (cliVariables != null && !cliVariables.isEmpty()) {
            for (Map.Entry<String, String> variableEntry : cliVariables.entrySet()) {
                configContentBuilder
                    .append("  ")
                    .append(variableEntry.getKey())
                    .append(": \"")
                    .append(variableEntry.getValue())
                    .append("\"\n");
            }
        } else {
            configContentBuilder.append("  # Example variables:\n");
            configContentBuilder.append("  HOSTNAME: \n");
            configContentBuilder.append("  LOCATION: \n");
        }

        configContentBuilder.append("debug: false\n");
        configContentBuilder.append("timeout: 30000\n");

        Files.writeString(configFilePath, configContentBuilder.toString());
    }

    private void createSampleTaskFile() throws IOException {
        Path sampleTaskFilePath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder taskContentBuilder = new StringBuilder();
        taskContentBuilder.append("# Sample Task\n");
        taskContentBuilder.append("description: \"Check weather for a US city\"\n");
        taskContentBuilder.append("tags: [smoketest]\n");
        taskContentBuilder.append("author: \"Gepetto\"\n");
        taskContentBuilder.append("created: \"").append(currentDate).append("\"\n\n");
        taskContentBuilder.append("Task:\n");
        taskContentBuilder.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        taskContentBuilder.append("  Search the weather for ${LOCATION}.\n");
        taskContentBuilder.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskFilePath, taskContentBuilder.toString());
    }

    private void printSuccessMessage() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
    }
}