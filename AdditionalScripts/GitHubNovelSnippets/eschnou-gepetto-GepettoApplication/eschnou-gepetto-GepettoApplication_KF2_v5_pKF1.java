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

	public static void main(String[] applicationArguments) {
		int applicationExitCode = SpringApplication.exit(
				SpringApplication.run(GepettoApplication.class, applicationArguments)
		);
		System.exit(applicationExitCode);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner commandLineRunner(CommandLine.IFactory commandLineFactory, GepettoCommand gepettoCommand) {
		return commandLineArguments -> {
			try {
				CommandLine commandLine = new CommandLine(gepettoCommand, commandLineFactory);

				commandLine.setExecutionExceptionHandler((exception, command, parseResult) -> {
					System.err.println(exception.getMessage());
					return command.getCommandSpec().exitCodeOnExecutionException();
				});

				commandLine.setParameterExceptionHandler((parameterException, args) -> {
					System.err.println(parameterException.getMessage());
					if (!CommandLine.UnmatchedArgumentException.printSuggestions(parameterException, System.err)) {
						CommandLine errorCommandLine = parameterException.getCommandLine();
						errorCommandLine.usage(System.err, errorCommandLine.getColorScheme());
					}
					return parameterException.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
				});

				int commandExitCode = commandLine.execute(commandLineArguments);
				if (commandExitCode != 0) {
					System.exit(commandExitCode);
				}
			} catch (Exception exception) {
				System.exit(1);
			}
		};
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