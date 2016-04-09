package parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DirectoryParser extends UserInputParser {
	private String filePath;
	private static Logger loggerFilePath = Logger.getLogger("getFilePath");


	private void setAttributesForChange(String userInput) {
		removeWhiteSpaces(userInput);
		determineLengthOfInput();
		setFilePath();
		//convertFilePath();
	}

	private void setFilePath() {
		filePath=userCommand[1];
	}
	/*private void convertFilePath() {
		filePath=filePath.replaceAll("\\", "\\\\");
	}*/

	public String getFilePath(String userInput) {
		loggerFilePath.log(Level.INFO, "Start of getFilePath");
		setAttributesForChange(userInput);
		loggerFilePath.log(Level.INFO, "End of getFilePath");

		return filePath;
	}
}
