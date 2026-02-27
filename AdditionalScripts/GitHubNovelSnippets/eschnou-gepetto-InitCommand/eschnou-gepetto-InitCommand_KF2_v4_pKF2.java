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
            printProjectAlreadyInitializedMessage();
            return;
        }

        try {
            applyVariablesToConfig();
            createProjectStructure();
            createConfigFile();
            createSampleTask();
            printSuccessMessage();
        } catch (IOException e) {
            logger.error("Error initializing project: {}", e.getMessage(), e);
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
        createDirectoryIfMissing(Paths.get(Constants.PROJECT_DIR), "project");
        createDirectoryIfMissing(Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR), "tasks");
        createDirectoryIfMissing(Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR), "results");
    }

    private void createDirectoryIfMissing(Path path, String description) throws IOException {
        if (Files.exists(path)) {
            return;
        }
        logger.info("Creating {} directory: {}", description, path);
        Files.createDirectories(path);
    }

    private void createConfigFile() throws IOException {
        Path configPath = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);
        String content = buildConfigFileContent();
        Files.writeString(configPath, content);
    }

    private String buildConfigFileContent() {
        StringBuilder configContent = new StringBuilder();

        configContent.append("# Gepetto Configuration\n\n");
        configContent.append("variables:\n");

        if (variables != null && !variables.isEmpty()) {
            variables.forEach((key, value) ->
                configContent
                    .append("  ")
                    .append(key)
                    .append(": \"")
                    .append(value)
                    .append("\"\n")
            );
        } else {
            appendExampleVariables(configContent);
        }

        configContent.append("\n");
        configContent.append("debug: false\n");
        configContent.append("timeout: 30000\n");

        return configContent.toString();
    }

    private void appendExampleVariables(StringBuilder configContent) {
        configContent.append("  # Example variables:\n");
        configContent.append("  HOSTNAME: \n");
        configContent.append("  LOCATION: \n");
    }

    private void createSampleTask() throws IOException {
        Path taskPath = Paths.get(
            Constants.PROJECT_DIR,
            Constants.TASKS_DIR,
            Constants.SAMPLE_TASK_FILENAME
        );

        String content = buildSampleTaskContent();
        Files.writeString(taskPath, content);
    }

    private String buildSampleTaskContent() {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        StringBuilder taskContent = new StringBuilder();
        taskContent.append("# Sample Task\n");
        taskContent.append("description: \"Check weather for a US city\"\n");
        taskContent.append("tags: [smoketest]\n");
        taskContent.append("author: \"Gepetto\"\n");
        taskContent.append("created: \"").append(currentDate).append("\"\n\n");
        taskContent.append("Task:\n");
        taskContent.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        taskContent.append("  Search the weather for ${LOCATION}.\n");
        taskContent.append("  Verify that the weather matches the requested location.\n");

        return taskContent.toString();
    }

    private void printProjectAlreadyInitializedMessage() {
        System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
        System.out.println("Use 'configure' command to update existing configuration.");
    }

    private void printSuccessMessage() {
        System.out.println("Gepetto project initialized successfully!");
        System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
        System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
        System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        System.out.println("\nTo run your task: gepetto run " +
            Constants.PROJECT_DIR + "/" +
            Constants.TASKS_DIR + "/" +
            Constants.SAMPLE_TASK_FILENAME
        );
    }
}