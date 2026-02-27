package sh.gepetto.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import picocli.CommandLine;
import sh.gepetto.app.cli.GepettoCommand;

@SpringBootApplication
public class GepettoApplication {

    public static void main(String[] args) {
        int exitCode = SpringApplication.exit(
                SpringApplication.run(GepettoApplication.class, args)
        );
        System.exit(exitCode);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner commandLineRunner(CommandLine.IFactory factory, GepettoCommand command) {
        return args -> {
            CommandLine commandLine = createConfiguredCommandLine(factory, command);
            int exitCode = executeCommand(commandLine, args);
            exitIfNonZero(exitCode);
        };
    }

    private CommandLine createConfiguredCommandLine(CommandLine.IFactory factory, GepettoCommand command) {
        CommandLine commandLine = new CommandLine(command, factory);
        configureExecutionExceptionHandler(commandLine);
        configureParameterExceptionHandler(commandLine);
        return commandLine;
    }

    private void configureExecutionExceptionHandler(CommandLine commandLine) {
        commandLine.setExecutionExceptionHandler((exception, cmd, parseResult) -> {
            System.err.println(exception.getMessage());
            return cmd.getCommandSpec().exitCodeOnExecutionException();
        });
    }

    private void configureParameterExceptionHandler(CommandLine commandLine) {
        commandLine.setParameterExceptionHandler((exception, args) -> {
            System.err.println(exception.getMessage());

            if (!CommandLine.UnmatchedArgumentException.printSuggestions(exception, System.err)) {
                CommandLine exCommandLine = exception.getCommandLine();
                exCommandLine.usage(System.err, exCommandLine.getColorScheme());
            }

            return exception.getCommandLine()
                    .getCommandSpec()
                    .exitCodeOnInvalidInput();
        });
    }

    private int executeCommand(CommandLine commandLine, String[] args) {
        try {
            return commandLine.execute(args);
        } catch (Exception exception) {
            System.err.println("Unexpected error: " + exception.getMessage());
            return 1;
        }
    }

    private void exitIfNonZero(int exitCode) {
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    @Bean
    @Profile("!test")
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> 0;
    }

    public static class CommandExecutionException extends RuntimeException implements ExitCodeGenerator {

        private final int exitCode;

        public CommandExecutionException(String message, int exitCode) {
            super(message);
            this.exitCode = exitCode;
        }

        public CommandExecutionException(String message, Throwable cause, int exitCode) {
            super(message, cause);
            this.exitCode = exitCode;
        }

        @Override
        public int getExitCode() {
            return exitCode;
        }
    }
}