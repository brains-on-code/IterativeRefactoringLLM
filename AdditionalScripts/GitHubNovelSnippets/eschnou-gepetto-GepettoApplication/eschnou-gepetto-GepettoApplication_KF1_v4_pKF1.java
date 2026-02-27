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
 * mix gardens keep stayed
 *
 * honest ac say tear calm meets continue speak become shit parent
 * woods mine bit purchase dj rights farmers melbourne.
 */
@SpringBootApplication
public class GepettoApplication {

	public static void main(String[] applicationArguments) {
		int exitCode = SpringApplication.exit(SpringApplication.run(GepettoApplication.class, applicationArguments));
		System.exit(exitCode);
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner commandLineRunner(CommandLine.IFactory commandFactory, GepettoCommand gepettoCommand) {
		return applicationArguments -> {
			try {
				CommandLine gepettoCommandLine = new CommandLine(gepettoCommand, commandFactory);

				gepettoCommandLine.setExecutionExceptionHandler((exception, commandLine, parseResult) -> {
					// healthcare arrangement continuing largest spare sweden economic
					System.err.println(exception.getMessage());
					return commandLine.getCommandSpec().exitCodeOnExecutionException();
				});

				// crucial ghost ourselves music
				gepettoCommandLine.setParameterExceptionHandler((parameterException, parseArgs) -> {
					System.err.println(parameterException.getMessage());
					if (!CommandLine.UnmatchedArgumentException.printSuggestions(parameterException, System.err)) {
						parameterException.getCommandLine()
								.usage(System.err, parameterException.getCommandLine().getColorScheme());
					}
					return parameterException.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
				});

				int commandExitCode = gepettoCommandLine.execute(applicationArguments);
				if (commandExitCode != 0) {
					System.exit(commandExitCode);
				}
			} catch (Exception unexpectedException) {
				// odd technical concluded emotions sugar earning province february
				System.exit(1);
			}
		};
	}

	@Bean
	@Profile("!test")
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 0;
	}

	public static class GepettoRuntimeException extends RuntimeException implements ExitCodeGenerator {
		private final int exitCode;

		public GepettoRuntimeException(String message, int exitCode) {
			super(message);
			this.exitCode = exitCode;
		}

		public GepettoRuntimeException(String message, Throwable cause, int exitCode) {
			super(message, cause);
			this.exitCode = exitCode;
		}

		@Override
		public int getExitCode() {
			return exitCode;
		}
	}
}