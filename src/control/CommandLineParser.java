package control;

import java.util.ArrayList;

import control.enums.ActionType;
import control.enums.DestinationType;
import control.enums.SourceType;

public abstract class CommandLineParser {
	private String source;
	private SourceType sourceType;
	private DestinationType destinationType;
	private final ArrayList<ActionType> actionsToExecute = new ArrayList<ActionType>();

	public static String help = "utext options [-f|-t] [source file|text]\n\n" + "options:\n"
			+ "-tu convert text to uppercase\n-tl convert text to lowercase\n" + "-rs remove all white spaces\n"
			+ "-rn removes every numbers\n" + "-cw count number of words\n\n" + "[-f|-t]\n-f source file name\n"
			+ "-t source text\n\n";

	/**
	 * Parse arguments
	 * 
	 * @param args
	 * @return true if parse properly done
	 */
	protected boolean doParse(String[] args) {

		if (args.length > 0) {

			for (int i = 0; i < args.length; i++) {
				String s = args[i].trim();

				if (s.compareTo("-tu") == 0) {
					actionsToExecute.add(ActionType.TO_UPPER_CASE);
				} else if (s.compareTo("-tl") == 0) {
					actionsToExecute.add(ActionType.TO_LOWER_CASE);
				} else if (s.compareTo("-rs") == 0) {
					actionsToExecute.add(ActionType.REMOVE_SPACES);
				} else if (s.compareTo("-rn") == 0) {
					actionsToExecute.add(ActionType.REMOVE_NUMBERS);
				} else if (s.compareTo("-cw") == 0) {
					actionsToExecute.add(ActionType.COUNT_WORDS);
				} else if (s.compareTo("-f") == 0) {
					sourceType = SourceType.FILE;
					if (i >= args.length - 1) {
						System.out.println("Missing source file name\n\n" + help);
						return false;
					}
					source = args[i + 1].trim();
				} else if (s.compareTo("-t") == 0) {
					sourceType = SourceType.TEXT;
					if (i >= args.length - 1) {
						System.out.println("Missing source text\n\n" + help);
						return false;
					}
					source = args[i + 1].trim();
				}
			}

			if (actionsToExecute.isEmpty()) {
				System.out.println("Empty. Select at least one valid option\n\n" + help);
				return false;
			} else if (sourceType == null) {
				System.out.println("Select a valid source\n\n" + help);
				return false;
			}

			if (sourceType == SourceType.FILE) {
				destinationType = DestinationType.FILE;
			} else if (sourceType == SourceType.TEXT) {
				destinationType = DestinationType.CONSOLE;
			} else {
				System.out.println("Select a valid source\n\n" + help);
				return false;
			}

			return true;
		}

		System.out.println(help);
		return false;
	}

	public String getSource() {
		return source;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public DestinationType getDestinationType() {
		return destinationType;
	}

	public ActionType[] getActionsToExecute() {
		ActionType[] at = new ActionType[actionsToExecute.size()];
		at = actionsToExecute.toArray(at);
		return at;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

}
