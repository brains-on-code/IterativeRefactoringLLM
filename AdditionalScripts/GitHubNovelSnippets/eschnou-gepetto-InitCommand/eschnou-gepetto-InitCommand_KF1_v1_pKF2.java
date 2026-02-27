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
 * Initializes a new Gepetto project in the current directory.
 */
@Component
@Command(
    name = "init",
    description = "Initialize a new Gepetto project"
)
public class InitCommand implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(InitCommand.class);

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
            System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
            System.out.println("Use 'configure' command to update existing configuration.");
            return;
        }

        try {
            applyVariablesFromCli();
            createProjectStructure();
            createConfigFile();
            createSampleTaskFile();
            printSuccessMessage();
        } catch (IOException e) {
            logger.error("Error initializing project: {}", e.getMessage(), e);
            System.err.println("Error initializing project: " + e.getMessage());
        }
    }

    /**
     * Apply variables passed via CLI to the application configuration.
     */
    private void applyVariablesFromCli() {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        variables.forEach(applicationConfig::setVariable);
    }

    /**
     * Create the base project directory and subdirectories (tasks, results).
     */
    private void createProjectStructure() throws IOException {
        Path projectDir = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(projectDir)) {
            logger.info("Creating project directory: {}", projectDir);
            Files.createDirectories(projectDir);
        }

        Path tasksDir = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(tasksDir)) {
            logger.info("Creating tasks directory: {}", tasksDir);
            Files.createDirectories(tasksDir);
        }

        Path resultsDir = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(resultsDir)) {
            logger.info("Creating results directory: {}", resultsDir);
            Files.createDirectories(resultsDir);
        }
    }

    /**
     * Create the main configuration file with variables and defaults.
     */
    private void createConfigFile() throws IOException {
        Path configPath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder content = new StringBuilder();
        content.append("# Gepetto Configuration\n\n");

        content.append("variables:\n");
        if (variables != null && !variables.isEmpty()) {
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                content
                    .append("  ")
                    .append(entry.getKey())
                    .append(": \"")
                    .append(entry.getValue())
                    .append("\"\n");
            }
        } else {
            content.append("  # Example variables:\n");
            content.append("  HOSTNAME: \n");
            content.append("  LOCATION: \n");
        }

        content.append("debug: false\n");
        content.append("timeout: 30000\n");

        Files.writeString(configPath, content.toString());
    }

    /**
     * Create a sample task file to demonstrate task structure.
     */
    private void createSampleTaskFile() throws IOException {
        Path sampleTaskPath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String createdDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder content = new StringBuilder();
        content.append("# Sample Task\n");
        content.append("description: \"Check weather for a US city\"\n");
        content.append("tags: [smoketest]\n");
        content.append("author: \"Gepetto\"\n");
        content.append("created: \"").append(createdDate).append("\"\n\n");
        content.append("Task:\n");
        content.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        content.append("  Search the weather for ${LOCATION}.\n");
        content.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskPath, content.toString());
    }

    /**
     * Print a summary of what was created and how to proceed.
     */
    private void printSuccessMessage() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println(
            "\nTo run your task: gepetto run "
                + Constants.PROJECT_DIR
                + "/"
                + Constants.TASKS_DIR
                + "/"
                + Constants.SAMPLE_TASK_FILENAME
        );
    }
}