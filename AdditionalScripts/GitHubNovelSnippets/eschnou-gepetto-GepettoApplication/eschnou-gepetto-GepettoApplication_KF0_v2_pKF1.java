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
 * Gepetto Application Entry Point
 *
 * Gepetto is a natural language task execution framework that allows users
 * to write and execute tasks in plain English.
 */
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
	public CommandLineRunner gepettoCommandLineRunner(CommandLine.IFactory commandFactory, GepettoCommand gepettoCommand) {
		return args -> {
			try {
				CommandLine commandLine = new CommandLine(gepettoCommand, commandFactory);

				commandLine.setExecutionExceptionHandler((exception, cmd, parseResult) -> {
					System.err.println(exception.getMessage());
					return cmd.getCommandSpec().exitCodeOnExecutionException();
				});

				commandLine.setParameterExceptionHandler((parameterException, originalArgs) -> {
					System.err.println(parameterException.getMessage());
					if (!CommandLine.UnmatchedArgumentException.printSuggestions(parameterException, System.err)) {
						CommandLine errorCommandLine = parameterException.getCommandLine();
						errorCommandLine.usage(System.err, errorCommandLine.getColorScheme());
					}
					return parameterException.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
				});

				int exitCode = commandLine.execute(args);
				if (exitCode != 0) {
					System.exit(exitCode);
				}
			} catch (Exception unexpectedException) {
				System.exit(1);
			}
		};
	}

	@Bean
	@Profile("!test")
	public ExitCodeGenerator defaultExitCodeGenerator() {
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