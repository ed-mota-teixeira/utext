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
import control.enums.DestinationType;
import control.enums.SourceType;

public class Executioner extends CommandLineParser {

	private final ArrayList<Action> actions = new ArrayList<Action>();

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
						sb.append(line);
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
					sb.setLength(0);
					sb.append(ms);
				});
			} catch (Exception ex) {
				System.out.println(ex);
				return;
			}

			// output the result to the destination
			if (getDestinationType() == DestinationType.FILE) {
				String ft = "" + System.currentTimeMillis();
				File myFile = new File(ft + ".txt");
				try {
					if (myFile.createNewFile()) {
						FileWriter myWriter = new FileWriter(ft + ".txt");
						try {
							myWriter.write(sb.toString());
						} catch (IOException ee) {
							System.out.println(ee);
						} finally {
							myWriter.close();
						}
					} else {
						System.out.println("Unable to create output the file.");
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(e);
				}
			} else {
				System.out.println(sb);
			}

		}

	}

}
