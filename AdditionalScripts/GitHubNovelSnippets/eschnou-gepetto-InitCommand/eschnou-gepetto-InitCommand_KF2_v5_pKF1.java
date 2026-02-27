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
        Path projectDirPath = Paths.get(Constants.PROJECT_DIR);
        if (Files.exists(projectDirPath)) {
            System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
            System.out.println("Use 'configure' command to update existing configuration.");
            return;
        }

        try {
            applyCliVariablesToConfig();
            createProjectDirectories();
            createConfigFile();
            createSampleTaskFile();
            printInitializationSummary();
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

    private void createProjectDirectories() throws IOException {
        Path projectDirPath = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(projectDirPath)) {
            LOGGER.info("Creating project directory: {}", projectDirPath);
            Files.createDirectories(projectDirPath);
        }

        Path tasksDirPath = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(tasksDirPath)) {
            LOGGER.info("Creating tasks directory: {}", tasksDirPath);
            Files.createDirectories(tasksDirPath);
        }

        Path resultsDirPath = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(resultsDirPath)) {
            LOGGER.info("Creating results directory: {}", resultsDirPath);
            Files.createDirectories(resultsDirPath);
        }
    }

    private void createConfigFile() throws IOException {
        Path configFilePath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder configContent = new StringBuilder();
        configContent.append("# Gepetto Configuration\n\n");

        configContent.append("variables:\n");
        if (cliVariables != null && !cliVariables.isEmpty()) {
            for (Map.Entry<String, String> variableEntry : cliVariables.entrySet()) {
                configContent
                    .append("  ")
                    .append(variableEntry.getKey())
                    .append(": \"")
                    .append(variableEntry.getValue())
                    .append("\"\n");
            }
        } else {
            configContent.append("  # Example variables:\n");
            configContent.append("  HOSTNAME: \n");
            configContent.append("  LOCATION: \n");
        }

        configContent.append("debug: false\n");
        configContent.append("timeout: 30000\n");

        Files.writeString(configFilePath, configContent.toString());
    }

    private void createSampleTaskFile() throws IOException {
        Path sampleTaskFilePath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String currentIsoDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder sampleTaskContent = new StringBuilder();
        sampleTaskContent.append("# Sample Task\n");
        sampleTaskContent.append("description: \"Check weather for a US city\"\n");
        sampleTaskContent.append("tags: [smoketest]\n");
        sampleTaskContent.append("author: \"Gepetto\"\n");
        sampleTaskContent.append("created: \"").append(currentIsoDate).append("\"\n\n");
        sampleTaskContent.append("Task:\n");
        sampleTaskContent.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        sampleTaskContent.append("  Search the weather for ${LOCATION}.\n");
        sampleTaskContent.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskFilePath, sampleTaskContent.toString());
    }

    private void printInitializationSummary() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
    }
}