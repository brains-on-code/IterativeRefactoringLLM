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

/**
 * circuit sample reactions stock already confident stadium
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
    private java.util.Map<String, String> cliVariables;

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
            if (cliVariables != null && !cliVariables.isEmpty()) {
                cliVariables.forEach(applicationConfig::setVariable);
            }

            createProjectStructure();
            writeConfigFile();
            writeSampleTaskFile();

            System.out.println("Gepetto project initialized successfully!");
            System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
            System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
            System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
            System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        } catch (IOException e) {
            logger.error("Error initializing project: {}", e.getMessage(), e);
            System.err.println("Error initializing project: " + e.getMessage());
        }
    }

    private void createProjectStructure() throws IOException {
        Path projectDirPath = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(projectDirPath)) {
            logger.info("Creating project directory: {}", projectDirPath);
            Files.createDirectories(projectDirPath);
        }

        Path tasksDirPath = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(tasksDirPath)) {
            logger.info("Creating tasks directory: {}", tasksDirPath);
            Files.createDirectories(tasksDirPath);
        }

        Path resultsDirPath = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(resultsDirPath)) {
            logger.info("Creating results directory: {}", resultsDirPath);
            Files.createDirectories(resultsDirPath);
        }
    }

    private void writeConfigFile() throws IOException {
        Path configFilePath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder configContent = new StringBuilder();
        configContent.append("# Gepetto Configuration\n\n");

        configContent.append("variables:\n");
        if (cliVariables != null && !cliVariables.isEmpty()) {
            for (java.util.Map.Entry<String, String> entry : cliVariables.entrySet()) {
                configContent
                    .append("  ")
                    .append(entry.getKey())
                    .append(": \"")
                    .append(entry.getValue())
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

    private void writeSampleTaskFile() throws IOException {
        Path sampleTaskPath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String createdDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder taskContent = new StringBuilder();
        taskContent.append("# Sample Task\n");
        taskContent.append("description: \"Check weather for a US city\"\n");
        taskContent.append("tags: [smoketest]\n");
        taskContent.append("author: \"Gepetto\"\n");
        taskContent.append("created: \"").append(createdDate).append("\"\n\n");
        taskContent.append("Task:\n");
        taskContent.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        taskContent.append("  Search the weather for ${LOCATION}.\n");
        taskContent.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskPath, taskContent.toString());
    }
}