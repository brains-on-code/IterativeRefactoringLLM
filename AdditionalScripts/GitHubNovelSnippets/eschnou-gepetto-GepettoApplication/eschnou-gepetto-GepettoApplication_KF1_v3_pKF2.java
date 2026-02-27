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

				commandLine.setExecutionExceptionHandler((ex, cmd, parseResult) -> {
					System.err.println(ex.getMessage());
					return cmd.getCommandSpec().exitCodeOnExecutionException();
				});

				commandLine.setParameterExceptionHandler((ex, args1) -> {
					System.err.println(ex.getMessage());

					boolean suggestionsPrinted =
							CommandLine.UnmatchedArgumentException.printSuggestions(ex, System.err);

					if (!suggestionsPrinted) {
						CommandLine cmd = ex.getCommandLine();
						cmd.usage(System.err, cmd.getColorScheme());
					}

					return ex.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
				});

				int exitCode = commandLine.execute(args);
				if (exitCode != 0) {
					System.exit(exitCode);
				}
			} catch (Exception ex) {
				System.exit(1);
			}
		};
	}

	@Bean
	@Profile("!test")
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 0;
	}

	public static class GepettoCliException extends RuntimeException implements ExitCodeGenerator {
		private final int exitCode;

		public GepettoCliException(String message, int exitCode) {
			super(message);
			this.exitCode = exitCode;
		}

		public GepettoCliException(String message, Throwable cause, int exitCode) {
			super(message, cause);
			this.exitCode = exitCode;
		}

		@Override
		public int getExitCode() {
			return exitCode;
		}
	}
}