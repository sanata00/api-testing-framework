package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestDataUtil {
    public static String getDataFromFile(String fileName) {
        File testDataFile = new File("src/test/resources/testData/".concat(fileName));
        try {
            return FileUtils.readFileToString(testDataFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("The file " + fileName + " was not found");
        }
    }
}
