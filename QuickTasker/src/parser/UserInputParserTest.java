package parser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import parser.UserInputParser.ParserException;

public class UserInputParserTest {
    String userCommandString = "sadfasdf";
    UserInputParser parser= new UserInputParser();
    setAttributes(userCommandString);
    
    String taskName = parser.getTaskName();
    
    
    
    
    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = ParserException.class)
    public void testNullInput() {
       parser.getTaskName(null);
       
    }
    
    @Test
    public void testEmptyString(){
        parser.getTaskName("");
    }
}
