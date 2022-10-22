package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import control.actions.Action;
import control.actions.CountWordsAction;
import control.actions.LowercaseAction;
import control.actions.RemoveNumbersAction;
import control.actions.RemoveSpacesAction;
import control.actions.UppercaseAction;
import control.enums.ActionType;
import control.enums.SourceType;
import control.out.ConsoleOutput;
import control.out.Output;
import control.out.TextFileOutput;

public class Executioner extends CommandLineParser {

	private final ArrayList<Action> actions = new ArrayList<Action>();

	private void saveIt(String data) {
		Output o = null;

		switch (getDestinationType()) {
		case FILE:
			o = new TextFileOutput();
			break;
		default:
			o = new ConsoleOutput();
		}

		if (!o.send(data)) {
			System.out.println("Error trying to output data");
		} else {
			System.out.println("ok.");
		}
	}

	public void doExecute(String[] args) {

		final StringBuilder sb = new StringBuilder();

		if (doParse(args)) {

			// first add the actions
			final ActionType[] a = getActionsToExecute();
			for (int i = 0; i < a.length; i++) {
				switch (a[i]) {
				case REMOVE_NUMBERS:
					actions.add(new RemoveNumbersAction());
					break;
				case REMOVE_SPACES:
					actions.add(new RemoveSpacesAction());
					break;
				case TO_LOWER_CASE:
					actions.add(new LowercaseAction());
					break;
				case TO_UPPER_CASE:
					actions.add(new UppercaseAction());
					break;
				case COUNT_WORDS:
					actions.add(new CountWordsAction());
				default:
					break;
				}
			}

			// Get the source
			if (getSourceType() == SourceType.FILE) {
				// open file and read text
				Path filePath = Paths.get(getSource());
				Charset charset = StandardCharsets.UTF_8;
				try {
					List<String> lines = Files.readAllLines(filePath, charset);
					for (String line : lines) {
						sb.append(line + "\n");
					}
				} catch (IOException ioEx) {
					System.out.println("I/O Exception " + ioEx.getMessage());
					return;
				} catch (SecurityException secEx) {
					System.out.println("Security Exception " + secEx.getMessage());
					return;
				}

			} else if (getSourceType() == SourceType.TEXT) {

				sb.append(getSource());

			}

			// process the actions on the source
			try {
				actions.forEach((e) -> {
					String ms = e.process(sb.toString());
					saveIt(ms);
				});
			} catch (Exception ex) {
				System.out.println(ex);
				return;
			}

		}

		System.out.println("Done.");

	}

}
