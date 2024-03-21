package pl.futurecollars.invoicing.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileService {

  public void writeLine(String line, String filePath) {
    try {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      try (FileWriter fileWriter = new FileWriter(filePath, true)) {
        fileWriter.write(line + System.lineSeparator());
      }
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

  public void appendLines(List<String> lines, String filePath) {
    try {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      Files.write(path, lines, StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void appendLine(String line, String filePath) {
    try {
      Path path = Paths.get(filePath);
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      Files.write(path, (line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException("Failed to append line to file " + filePath, e);
    }
  }
}
