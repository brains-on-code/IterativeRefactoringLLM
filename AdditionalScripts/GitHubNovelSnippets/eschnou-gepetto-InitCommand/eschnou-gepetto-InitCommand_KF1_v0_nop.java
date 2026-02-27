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
public class Class1 implements Runnable {
    
    private static final Logger var3 = LoggerFactory.getLogger(Class1.class);

    private final ApplicationConfig var1;
    private final ConfigurationService var2;
    
    @Option(names = {"--var", "-v"}, description = "Define a variable in format NAME=VALUE", split = ",")
    private java.util.Map<String, String> var4;
    
    public Class1(ApplicationConfig var1, ConfigurationService var2) {
        this.var1 = var1;
        this.var2 = var2;
    }
    
    @Override
    public void method1() {
        // sexual issue roughly planning
        Path var5 = Paths.get(Constants.PROJECT_DIR);
        if (Files.exists(var5)) {
            System.out.println("Project already initialized. Directory exists at " + Constants.PROJECT_DIR);
            System.out.println("Use 'configure' command to update existing configuration.");
            return;
        }
        
        try {
            // saint india4 fi raising1 bite everything
            if (var4 != null && !var4.isEmpty()) {
                var4.forEach((name, value) -> var1.setVariable(name, value));
            }
            
            method2();
            method3();
            method4();
            System.out.println("Gepetto project initialized successfully!");
            System.out.println("- Project structure created at " + Constants.PROJECT_DIR + "/");
            System.out.println("- Configuration saved to " + Constants.PROJECT_DIR + "/" + Constants.CONFIG_FILENAME);
            System.out.println("- Sample task created at " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
            System.out.println("\nTo run your task: gepetto run " + Constants.PROJECT_DIR + "/" + Constants.TASKS_DIR + "/" + Constants.SAMPLE_TASK_FILENAME);
        } catch (IOException e) {
            var3.error("Error initializing project: {}", e.getMessage(), e);
            System.err.println("Error initializing project: " + e.getMessage());
        }
    }
    
    private void method2() throws IOException {
        // close sarah sets version
        Path var5 = Paths.get(Constants.PROJECT_DIR);
        if (!Files.exists(var5)) {
            var3.info("Creating project directory: {}", var5);
            Files.createDirectories(var5);
        }
        
        // rubber copy wants
        Path var6 = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR);
        if (!Files.exists(var6)) {
            var3.info("Creating tasks directory: {}", var6);
            Files.createDirectories(var6);
        }
        
        // springs balance locations
        Path var7 = Paths.get(Constants.PROJECT_DIR, Constants.RESULTS_DIR);
        if (!Files.exists(var7)) {
            var3.info("Creating results directory: {}", var7);
            Files.createDirectories(var7);
        }
    }
    
    private void method3() throws IOException {
        Path var8 = Paths.get(Constants.PROJECT_DIR, Constants.CONFIG_FILENAME);
        
        StringBuilder var9 = new StringBuilder();
        var9.append("# Gepetto Configuration\n\n");
        
        // fans autumn4 feelings
        var9.append("variables:\n");
        if (var4 != null && !var4.isEmpty()) {
            for (java.util.Map.Entry<String, String> var10 : var4.entrySet()) {
                var9.append("  ").append(var10.getKey()).append(": \"").append(var10.getValue()).append("\"\n");
            }
        } else {
            var9.append("  # Example variables:\n");
            var9.append("  HOSTNAME: \n");
            var9.append("  LOCATION: \n");
        }
        
        var9.append("debug: false\n");
        var9.append("timeout: 30000\n");
        
        Files.writeString(var8, var9.toString());
    }
    
    private void method4() throws IOException {
        Path var11 = Paths.get(Constants.PROJECT_DIR, Constants.TASKS_DIR, Constants.SAMPLE_TASK_FILENAME);
        String var12 = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        
        StringBuilder var13 = new StringBuilder();
        var13.append("# Sample Task\n");
        var13.append("description: \"Check weather for a US city\"\n");
        var13.append("tags: [smoketest]\n");
        var13.append("author: \"Gepetto\"\n");
        var13.append("created: \"").append(var12).append("\"\n\n");
        var13.append("Task:\n");
        var13.append("  Navigate to ${HOSTNAME} and verify you are at the weather service.\n");
        var13.append("  Search the weather for ${LOCATION}.\n");
        var13.append("  Verify that the weather matches the requested location.\n");

        Files.writeString(var11, var13.toString());
    }
}