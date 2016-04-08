package parser;

public class DirectoryParser extends UserInputParser {
	private String filePath;
	private String[] userCommand;

	private void setAttributesForChange(String userInput) {
		removeWhiteSpaces(userInput);
		determineLengthOfInput();
		setFilePath();
	}

	private void setFilePath() {
		String path = "";

		for (int i = 1; i < lengthOfInput; i++) {
			path += userCommand[i];
		}
		path.trim();
		filePath = path;
	}

	public String getFilePath(String userInput) {
		setAttributesForChange(userInput);
		return filePath;
	}
}
