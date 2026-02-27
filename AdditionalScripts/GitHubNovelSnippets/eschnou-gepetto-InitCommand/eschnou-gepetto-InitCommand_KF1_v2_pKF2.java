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

    private void applyVariablesFromCli() {
        if (variables == null || variables.isEmpty()) {
            return;
        }
        variables.forEach(applicationConfig::setVariable);
    }

    private void createProjectStructure() throws IOException {
        Path projectDir = Paths.get(Constants.PROJECT_DIR);
        Path tasksDir = projectDir.resolve(Constants.TASKS_DIR);
        Path resultsDir = projectDir.resolve(Constants.RESULTS_DIR);

        createDirectoryIfMissing(projectDir, "project");
        createDirectoryIfMissing(tasksDir, "tasks");
        createDirectoryIfMissing(resultsDir, "results");
    }

    private void createDirectoryIfMissing(Path dir, String label) throws IOException {
        if (Files.exists(dir)) {
            return;
        }
        logger.info("Creating {} directory: {}", label, dir);
        Files.createDirectories(dir);
    }

    private void createConfigFile() throws IOException {
        Path configPath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);

        StringBuilder content = new StringBuilder()
            .append("# Gepetto Configuration\n\n")
            .append("variables:\n");

        if (variables != null && !variables.isEmpty()) {
            variables.forEach((key, value) ->
                content.append("  ")
                    .append(key)
                    .append(": \"")
                    .append(value)
                    .append("\"\n")
            );
        } else {
            content.append("  # Example variables:\n")
                .append("  HOSTNAME: \n")
                .append("  LOCATION: \n");
        }

        content.append("debug: false\n")
            .append("timeout: 30000\n");

        Files.writeString(configPath, content.toString());
    }

    private void createSampleTaskFile() throws IOException {
        Path sampleTaskPath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );
        String createdDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder content = new StringBuilder()
            .append("# Sample Task\n")
            .append("description: \"Check weather for a US city\"\n")
            .append("tags: [smoketest]\n")
            .append("author: \"Gepetto\"\n")
            .append("created: \"").append(createdDate).append("\"\n\n")
            .append("Task:\n")
            .append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n")
            .append("  Search the weather for ${LOCATION}.\n")
            .append("  Verify that the weather matches the requested location.\n");

        Files.writeString(sampleTaskPath, content.toString());
    }

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