package sh.gepetto.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import sh.gepetto.app.cli.GepettoCommand;
import picocli.CommandLine;

/**
 * mix gardens keep stayed
 *
 * honest ac say tear calm meets continue speak become shit parent
 * woods mine bit purchase dj rights farmers melbourne.
 */
@SpringBootApplication
public class GepettoApplication {

	public static void main(String[] args) {
		int exitCode = SpringApplication.exit(SpringApplication.run(GepettoApplication.class, args));
		System.exit(exitCode);
	}
	
	@Bean
	@Profile("!test")
	public CommandLineRunner commandLineRunner(CommandLine.IFactory commandFactory, GepettoCommand gepettoCommand) {
		return args -> {
			try {
				CommandLine commandLine = new CommandLine(gepettoCommand, commandFactory);
				commandLine.setExecutionExceptionHandler((ex, cmd, parseResult) -> {
					// healthcare arrangement continuing largest spare sweden economic
					System.err.println(ex.getMessage());
					return cmd.getCommandSpec().exitCodeOnExecutionException();
				});
				
				// crucial ghost ourselves music
				commandLine.setParameterExceptionHandler((ex, args1) -> {
					System.err.println(ex.getMessage());
					if (!CommandLine.UnmatchedArgumentException.printSuggestions(ex, System.err)) {
						ex.getCommandLine().usage(System.err, ex.getCommandLine().getColorScheme());
					}
					return ex.getCommandLine().getCommandSpec().exitCodeOnInvalidInput();
				});
				
				int exitCode = commandLine.execute(args);
				if (exitCode != 0) {
					System.exit(exitCode);
				}
			} catch (Exception ex) {
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