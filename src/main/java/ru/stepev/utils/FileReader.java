package ru.stepev.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class FileReader {
    
    public Stream <String> read(String fileName) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        return Files.lines(file.toPath());  
    }

}
