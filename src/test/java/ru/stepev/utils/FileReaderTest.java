package ru.stepev.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.stepev.utils.*;

public class FileReaderTest {
	
	 public FileReader reader;

	    @BeforeEach
	    void setUp() throws Exception {
	        reader = new FileReader();
	    }

	    @Test
	    void fileReaderReadTest() throws Exception {   
	        List<String> actual = reader.read("shortTest.sql").collect(Collectors.toList());
	        List<String> expected = new ArrayList<>();
	        expected.add("DROP TABLE IF EXISTS groups CASCADE;");
	        expected.add("DROP TABLE IF EXISTS students CASCADE;");
	        expected.add("DROP TABLE IF EXISTS courses CASCADE;");
	        expected.add("DROP TABLE IF EXISTS students_courses CASCADE;");        
	        assertEquals(expected, actual);
	    }
	    
	    @Test
	    void fileNotFoundTest() { 
	        String actual = null;                                                                                                                                                                
	        try {
	            reader.read("someFile.sql").collect(Collectors.toList());
	            fail("Expected Exception");
	        } catch (NullPointerException e) {
	           actual = "NullPointerException";
	        } catch(IOException e) {
	            
	        }
	        assertEquals("NullPointerException", actual);
	    }

}
