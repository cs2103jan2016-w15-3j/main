package parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryParser extends UserInputParser {
	private String filePath;
	private String[] userCommand;
	private static Logger loggerFilePath = Logger.getLogger("getFilePath");


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
		loggerFilePath.log(Level.INFO, "Start of getFilePath");
		setAttributesForChange(userInput);
		loggerFilePath.log(Level.INFO, "End of getFilePath");

		return filePath;
	}
}
