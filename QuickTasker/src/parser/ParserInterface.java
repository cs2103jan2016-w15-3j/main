package parser;

import java.time.LocalDate;

public interface ParserInterface {

    /**
     * To retrieve attributes.
     *
     * @throws Exception
     **/

    LocalDate getStartDate(String userInput) throws Exception;

    LocalDate getEndDate(String userInput) throws Exception;

    Commands getCommand(String userInput) throws Exception;

    String getTaskName(String userInput) throws Exception;

    int getTaskIndex(String userInput) throws Exception;

}