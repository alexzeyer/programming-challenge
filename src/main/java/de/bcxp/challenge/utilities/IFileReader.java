package de.bcxp.challenge.utilities;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface IFileReader {
    List<Class> readFile(String filePath, Class targetClass, char separator) throws IOException, URISyntaxException;
}
