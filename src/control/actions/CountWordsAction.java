package control.actions;


public class CountWordsAction implements Action {

	@Override
	public String process(String source) {

		if (source == null || source.isEmpty()) {
			System.out.println("Empty source");
			return "";
		}

		int wordCount = 0;

		boolean isWord = false;
		int endOfLine = source.length() - 1;
		char[] characters = source.toCharArray();

		for (int i = 0; i < characters.length; i++) {
		
			if (Character.isLetter(characters[i]) && i != endOfLine) {
				isWord = true;
			} else if (!Character.isLetter(characters[i]) && isWord) {
				wordCount++;
				isWord = false;
			} else if (Character.isLetter(characters[i]) && i == endOfLine) {
				wordCount++;
			}
		}

		return "Number of words: " + wordCount;

	}

}
