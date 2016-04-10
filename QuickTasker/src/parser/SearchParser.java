package parser;

public class SearchParser extends UserInputParser {

	private String toSearch;

	private void setAttributesSearch(String input) {
		removeWhiteSpaces(input);
		determineLengthOfInput();
		toSearch = setToSearch();
	}

	private String setToSearch() {
		String output = "";

		for (int i = 1; i < lengthOfInput; i++) {
			output += userCommand[i];
			output += " ";
		}
		output = output.trim();
		return output;
	}

	public String getWordsToSearch(String input) {
		setAttributesSearch(input);
		return toSearch;
	}

}
