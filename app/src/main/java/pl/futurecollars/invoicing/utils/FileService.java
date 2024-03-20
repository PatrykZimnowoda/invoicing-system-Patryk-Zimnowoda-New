package pl.futurecollars.invoicing.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {

  public void writeLine(String line, String filePath) {
    try (FileWriter fileWriter = new FileWriter(filePath, true)) {
      fileWriter.write(line + System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<String> readLines(String filePath) {
    List<String> lines = new ArrayList<>();
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return lines;
  }

  public void writeLines(List<String> lines, String filePath) {
    try (FileWriter fileWriter = new FileWriter(filePath, false)) {
      for (String line : lines) {
        fileWriter.write(line + System.lineSeparator());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
