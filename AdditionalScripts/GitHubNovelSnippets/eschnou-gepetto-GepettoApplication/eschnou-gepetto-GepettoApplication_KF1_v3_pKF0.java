package sh.gepetto.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import picocli.CommandLine;
import sh.gepetto.app.cli.GepettoCommand;

/**
 * Application entry point for the Gepetto CLI.
 */
@SpringBootApplication
public class GepettoApplication {

    public static void main(String[] args) {
        int exitCode = SpringApplication.exit(SpringApplication.run(GepettoApplication.class, args));
        System.exit(exitCode);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner(CommandLine.IFactory factory, GepettoCommand gepettoCommand) {
        return args -> {
            try {
                CommandLine commandLine = new CommandLine(gepettoCommand, factory);
                configureExceptionHandlers(commandLine);

                int exitCode = commandLine.execute(args);
                if (exitCode != 0) {
                    System.exit(exitCode);
                }
            } catch (Exception exception) {
                System.err.println("Unexpected error: " + exception.getMessage());
                System.exit(1);
            }
        };
    }

    private void configureExceptionHandlers(CommandLine commandLine) {
        commandLine.setExecutionExceptionHandler((exception, cmd, parseResult) -> {
            System.err.println(exception.getMessage());
            return cmd.getCommandSpec().exitCodeOnExecutionException();
        });

        commandLine.setParameterExceptionHandler((exception, args) -> {
            System.err.println(exception.getMessage());
            if (!CommandLine.UnmatchedArgumentException.printSuggestions(exception, System.err)) {
                exception.getCommandLine().usage(System.err, exception.getCommandLine().getColorScheme());
            }
            return exception.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
        });
    }

    @Bean
    @Profile("!test")
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 0;
    }

    public static class GepettoExitException extends RuntimeException implements ExitCodeGenerator {
        private final int exitCode;

        public GepettoExitException(String message, int exitCode) {
            super(message);
            this.exitCode = exitCode;
        }

        public GepettoExitException(String message, Throwable cause, int exitCode) {
            super(message, cause);
            this.exitCode = exitCode;
        }

        @Override
        public int getExitCode() {
            return exitCode;
        }
    }
}